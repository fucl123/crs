package com.kzkj.listener;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.DepatureDetail;
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

        List<com.kzkj.pojo.po.Departure> departureList= new ArrayList<com.kzkj.pojo.po.Departure>();
        List<com.kzkj.pojo.po.Departure> updateDepartureList= new ArrayList<com.kzkj.pojo.po.Departure>();
        for(Departure departure:event.getDeparture())
        {
            com.kzkj.pojo.po.Departure dep = new com.kzkj.pojo.po.Departure();
            DepartureReturn departureReturn =new DepartureReturn();
            BeanMapper.map(departure.getDepartureHead(),departureReturn);
            BeanMapper.map(departure.getDepartureHead(),dep);
            dep.setDepatureDetailList(BeanMapper.mapList(departure.getDepartureList(), DepatureDetail.class));
            departureReturn.setPreNo("123456789");
            dep.setPreNo(departureReturn.getPreNo());
            String now = sdf.format(new Date());
            departureReturn.setReturnTime(now);
            dep.setReturnTime(now);
            //数据查重
            com.kzkj.pojo.po.Departure oldDeparture =
                    departureService.getByLogisticsCodeAndCopNo(
                            departure.getDepartureHead().getLogisticsCode(),departure.getDepartureHead().getCopNo());
            if(oldDeparture == null)
            {
                departureReturn = departureService.checkDeparture(departureReturn,dep);
                departureReturn.setReturnInfo("新增申报成功["+departure.getDepartureHead().getGuid()+"]");
                departureReturn.setReturnStatus("2");
                dep.setReturnInfo(departureReturn.getReturnInfo());
                dep.setReturnStatus(departureReturn.getReturnStatus());
                departureList.add(dep);
            }else {

                if (!oldDeparture.getReturnStatus().equals("2")
                        &&!oldDeparture.getReturnStatus().equals("399"))
                {
                    departureReturn = departureService.checkDeparture(departureReturn,dep);
                    if (departureReturn.getReturnStatus().equals("2"))
                    {
                        dep.setId(oldDeparture.getId());
                        dep.setReturnInfo(departureReturn.getReturnInfo());
                        dep.setReturnStatus(departureReturn.getReturnStatus());
                        updateDepartureList.add(dep);
                    }
                }else{
                    departureReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + departure.getDepartureHead().getCopNo()
                            + "]，原离境单报送时间对应状态为["
                            + now + " : 2-申报;]");
                    departureReturn.setReturnStatus("-304001");
                }
            }
            departureReturnList.add(departureReturn);
        }
        ceb510Message.setDepartureReturn(departureReturnList);
        ceb510Message.setGuid(departureReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb510Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB510Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        logger.info("回执报文："+resultXml);
        mqSender.sendMsg(queue, resultXml,"CEB510Message");
        //插入数据库
        departureService.insertDepartures(departureList);
        //更新退单数据
        departureService.batchUpdateDeparture(updateDepartureList);
        //判断是否是拆分的最后一个报文
        if(msgCount == msgSeqNo)
        {
            //离境单399回执报文
            returnDeparture399(departureHead.getBillNo(),baseTransfer.getDxpId());
            //清单899回执报文
            returnInvt899(departureHead.getBillNo(),baseTransfer.getDxpId());
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
        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Departure d:departureList)
        {
            DepartureReturn departureReturn =new DepartureReturn();
            d.setReturnTime(now);
            d.setReturnInfo("审核成功["+d.getLogisticsCode()+"+"+d.getCopNo()+"]");
            d.setReturnStatus("399");
            BeanMapper.map(d,departureReturn);
            departureReturnList.add(departureReturn);
        }
        ceb510Message.setDepartureReturn(departureReturnList);
        ceb510Message.setGuid(departureReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb510Message);
        String resultXml=customData(xml, dxpId, "CEB510Message");
        String queue=dxpId+"_HZ";
        if(departureService.updateBatchById(departureList))
        mqSender.sendMsg(queue, resultXml,"CEB510Message");
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
        String now = sdf.format(new Date());
        for(Inventory inventory: logisticsList)
        {
            InventoryReturn inventoryReturn = new InventoryReturn();
            inventory.setReturnTime(now);
            inventory.setReturnInfo("结关["+inventory.getEbcCode()+"+"+inventory.getOrderNo()+"]");
            inventory.setReturnStatus("899");
            BeanMapper.map(inventory,inventoryReturn);
            inventoryReturnList.add(inventoryReturn);
        }
        ceb604Message.setInventoryReturn(inventoryReturnList);
        ceb604Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb604Message);
        String resultXml=customData(xml, dxpId, "CEB604Message");
        String queue = dxpId+"_HZ";
        if(inventoryService.updateBatchById(logisticsList))
        mqSender.sendMsg(queue, resultXml,"CEB604Message");
    }
}
