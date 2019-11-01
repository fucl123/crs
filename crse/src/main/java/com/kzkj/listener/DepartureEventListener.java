package com.kzkj.listener;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.departure.CEB509Message;
import com.kzkj.pojo.vo.request.departure.Departure;
import com.kzkj.pojo.vo.request.departure.DepartureList;
import com.kzkj.pojo.vo.response.departure.CEB510Message;
import com.kzkj.pojo.vo.response.departure.DepartureReturn;
import com.kzkj.pojo.vo.response.invt.CEB604Message;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.service.DepartureService;
import com.kzkj.service.InventoryService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DepartureEventListener extends BaseListener{

    @Autowired
    DepartureService departureService;

    @Autowired
    InventoryService inventoryService;

    @Subscribe
    public void listener(CEB509Message event){
        CEB510Message ceb510Message=new CEB510Message();
        List<DepartureReturn> departureReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        if (event.getDeparture() == null ||event.getDeparture().size() <= 0) return;
        com.kzkj.pojo.vo.request.departure.DepartureHead departureHead = event.getDeparture().get(0).getDepartureHead();
        int msgCount = departureHead.getMsgCount();
        int msgSeqNo = Integer.parseInt(departureHead.getMsgSeqNo());

        for(Departure departure:event.getDeparture())
        {
            DepartureReturn departureReturn =new DepartureReturn();
            departureReturn.setGuid(departure.getDepartureHead().getGuid());
            departureReturn.setCopNo(departure.getDepartureHead().getCopNo());
            departureReturn.setPreNo("123456789");
            departureReturn.setLogisticsCode(departure.getDepartureHead().getLogisticsCode());
            departureReturn.setMsgSeqNo(departure.getDepartureHead().getMsgSeqNo());
            String now = sdf.format(new Date());
            departureReturn.setReturnTime(now);
            //数据查重
            boolean flag=true;
            if(flag)
            {
                departureReturn.setReturnInfo("新增申报成功["+departure.getDepartureHead().getGuid()+"]");
                departureReturn.setReturnStatus("2");
            }else {
                departureReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + departure.getDepartureHead().getCopNo()
                        + "]，原离境单报送时间对应状态为["
                        + now + " : 2-申报;]");
                departureReturn.setReturnStatus("-304001");
            }
            departureReturnList.add(departureReturn);
        }
        ceb510Message.setDepartureReturn(departureReturnList);
        ceb510Message.setGuid(departureReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb510Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB712Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB712Message");
        //插入数据库
        //departureService.insertDepartures(event.getDeparture());
        //判断是否是拆分的最后一个报文
        if(msgCount == msgSeqNo)
        {
            //离境单399回执报文
            //returnDeparture399(departureHead.getBillNo(),baseTransfer.getDxpId());
            //清单899回执报文
            //returnInvt899(departureHead.getBillNo(),baseTransfer.getDxpId());
        }
    }

    /**
     * 离境单399回执报文
     * @param billNo
     * @param dxpId
     */
    private void returnDeparture399(String billNo ,String dxpId){
        CEB510Message ceb510Message=new CEB510Message();
        List<DepartureReturn> departureReturnList =new ArrayList<>();

        EntityWrapper<com.kzkj.pojo.po.Departure> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("bill_no",billNo);
        List<com.kzkj.pojo.po.Departure> departureList= departureService.selectList(entityWrapper);
        for(com.kzkj.pojo.po.Departure d:departureList)
        {
            DepartureReturn departureReturn =new DepartureReturn();
            BeanMapper.map(d,departureReturn);
            departureReturn.setLogisticsCode(d.getLogisticsCode());
            String now = sdf.format(new Date());
            departureReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                departureReturn.setReturnInfo("审核成功["+d.getGuid()+"]");
                departureReturn.setReturnStatus("399");
            }else {
                departureReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + d.getCopNo()
                        + "]，原离境单报送时间对应状态为["
                        + now + " : 2-申报;]");
                departureReturn.setReturnStatus("-304001");
            }
            departureReturnList.add(departureReturn);
        }
        ceb510Message.setDepartureReturn(departureReturnList);
        ceb510Message.setGuid(departureReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb510Message);
        String resultXml=customData(xml, dxpId, "CEB712Message");
        String queue=dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB712Message");
    }


    /**
     * 清单899回执报文
     * @param billNo
     * @param dxpId
     */
    private void returnInvt899(String billNo ,String dxpId)
    {
        EntityWrapper<Inventory> entityWrapper =  new EntityWrapper<>();
        entityWrapper.eq("bill_no",billNo);
        List<Inventory> logisticsList = inventoryService.selectList(entityWrapper);
        if (logisticsList == null || logisticsList.size() <= 0) return;
        CEB604Message ceb604Message=new CEB604Message();
        List<InventoryReturn> inventoryReturnList= new ArrayList<InventoryReturn>();
        for(Inventory inventory: logisticsList)
        {
            InventoryReturn inventoryReturn = new InventoryReturn();
            BeanMapper.map(inventory,inventoryReturn);
            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);
            inventoryReturn.setReturnInfo("结关["+inventory.getGuid()+"]");
            inventoryReturn.setReturnStatus("899");
            inventoryReturnList.add(inventoryReturn);
        }
        ceb604Message.setInventoryReturn(inventoryReturnList);
        ceb604Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb604Message);
        String resultXml=customData(xml, dxpId, "CEB604Message");
        String queue = dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB604Message");
    }
}
