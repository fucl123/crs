package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.ImportDelivery;
import com.kzkj.pojo.po.ImportDeliveryDetail;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.delivery.CEB711Message;
import com.kzkj.pojo.vo.request.delivery.Delivery;
import com.kzkj.pojo.vo.response.delivery.CEB712Message;
import com.kzkj.pojo.vo.response.delivery.DeliveryReturn;
import com.kzkj.service.ImportDeliveryService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DeliveryEventListener extends BaseListener{

    @Autowired
    ImportDeliveryService importDeliveryService;

    @Subscribe
    public void listener(CEB711Message event){
        CEB712Message ceb712Message=new CEB712Message();
        List<DeliveryReturn> deliveryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<ImportDelivery> importDeliveryList =new ArrayList<>();
        List<ImportDelivery> updateImportDeliveryList= new ArrayList<>();
        Map<String,ImportDelivery> map = new HashMap<String,ImportDelivery>();
        for(Delivery delivery:event.getDelivery())
        {
            ImportDelivery importDelivery = new ImportDelivery();
            DeliveryReturn deliveryReturn =new DeliveryReturn();
            BeanMapper.map(delivery.getDeliveryHead(),deliveryReturn);
            deliveryReturn.setPreNo("123456789");
            String now = sdf.format(new Date());
            deliveryReturn.setReturnTime(now);
            BeanMapper.map(delivery.getDeliveryHead(),importDelivery);
            importDelivery.setReturnTime(now);
            importDelivery.setImportDeliveryDetailList(BeanMapper.mapList(delivery.getDeliveryList(), ImportDeliveryDetail.class));

            if(map.containsKey(importDelivery.getOperatorCode()+"_"+importDelivery.getCopNo()))
            {
                deliveryReturn.setReturnInfo("重复申报，业务主键["+importDelivery.getOperatorCode()+"_"+importDelivery.getCopNo()+"]");
                deliveryReturn.setReturnStatus("100");
                deliveryReturnList.add(deliveryReturn);
                continue;
            }
            map.put(importDelivery.getOperatorCode()+"_"+importDelivery.getCopNo(),importDelivery);
            //数据查重
            ImportDelivery oldImportDelivery = importDeliveryService.getByOperatorCodeAndCopNo(
                    delivery.getDeliveryHead().getOperatorCode(),delivery.getDeliveryHead().getCopNo());
            if(oldImportDelivery == null)
            {
                //数据校验
                deliveryReturn = importDeliveryService.checkImportDelivery(deliveryReturn,importDelivery);
                importDelivery.setReturnInfo(deliveryReturn.getReturnInfo());
                importDelivery.setReturnStatus(deliveryReturn.getReturnStatus());
                importDeliveryList.add(importDelivery);
            }else {
                if (!oldImportDelivery.getReturnStatus().equals("2")
                     &&!oldImportDelivery.getReturnStatus().equals("399")){
                    //数据校验
                    deliveryReturn = importDeliveryService.checkImportDelivery(deliveryReturn,importDelivery);
                    if (deliveryReturn.getReturnStatus().equals("2"))
                    {
                        importDelivery.setId(oldImportDelivery.getId());
                        importDelivery.setReturnInfo(deliveryReturn.getReturnInfo());
                        importDelivery.setReturnStatus(deliveryReturn.getReturnStatus());
                        updateImportDeliveryList.add(importDelivery);
                    }
                }else{
                    deliveryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + delivery.getDeliveryHead().getOperatorCode() + "+" + delivery.getDeliveryHead().getLogisticsCode()
                            + "]，原清单总分单报送时间对应状态为["
                            + now + " : 2-申报;]");
                    deliveryReturn.setReturnStatus("-304001");
                    importDelivery.setReturnStatus(deliveryReturn.getReturnStatus());
                    importDelivery.setReturnInfo(deliveryReturn.getReturnInfo());
                }
            }
            deliveryReturnList.add(deliveryReturn);
        }
        ceb712Message.setDeliveryReturn(deliveryReturnList);
        ceb712Message.setGuid(deliveryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb712Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB712Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        logger.info("resultXml:{}"+resultXml);
        mqSender.sendMsg(queue, resultXml,"CEB712Message");
        //插入数据库
        importDeliveryService.insertImportDelivery(importDeliveryList);
        //更新退单记录
        importDeliveryService.batchUpdateImportDelivery(updateImportDeliveryList);
        //入库明细399回执报文
        importDeliveryList.addAll(updateImportDeliveryList);
        returnDelivery399(importDeliveryList,baseTransfer.getDxpId());
    }
    /**
     * 入库明细单399回执报文
     * @param importDeliveryList
     */
    private void returnDelivery399(List<ImportDelivery> importDeliveryList,String dxpid)
    {
        if(importDeliveryList == null || importDeliveryList.size() <= 0)
        {
          return;
        }
        List<DeliveryReturn> deliveryReturnList =new ArrayList<>();
        List<ImportDelivery> updateImportDeliveryList =new ArrayList<>();
        String now = sdf.format(new Date());
        for(ImportDelivery importDelivery: importDeliveryList)
        {
            ImportDelivery oldImportDelivery =
                    importDeliveryService.getByOperatorCodeAndCopNo(importDelivery.getOperatorCode(),importDelivery.getCopNo());
            if(oldImportDelivery !=null )
            {
                if(oldImportDelivery.getReturnStatus().equals("2")){
                    DeliveryReturn deliveryReturn =new DeliveryReturn();
                    BeanMapper.map(importDelivery,deliveryReturn);
                    deliveryReturn.setReturnTime(now);
                    deliveryReturn.setReturnStatus("399");
                    deliveryReturn.setReturnInfo("海关审结["+importDelivery.getOperatorCode()+"+"+importDelivery.getCopNo()+"]");
                    deliveryReturnList.add(deliveryReturn);
                    oldImportDelivery.setReturnInfo(deliveryReturn.getReturnInfo());
                    oldImportDelivery.setReturnStatus(deliveryReturn.getReturnStatus());
                    oldImportDelivery.setReturnTime(deliveryReturn.getReturnTime());
                    updateImportDeliveryList.add(oldImportDelivery);
                }else{
                    DeliveryReturn deliveryReturn =new DeliveryReturn();
                    BeanMapper.map(importDelivery,deliveryReturn);
                    deliveryReturn.setReturnTime(now);
                    deliveryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + importDelivery.getOperatorCode() + "+" + importDelivery.getLogisticsCode()
                            + "]，原清单总分单报送时间对应状态为["
                            + now + " : "+oldImportDelivery.getReturnStatus());
                    deliveryReturn.setReturnStatus("-304001");
                    deliveryReturnList.add(deliveryReturn);
                }
            }else{
                continue;
            }
        }
        CEB712Message ceb712Message=new CEB712Message();
        ceb712Message.setDeliveryReturn(deliveryReturnList);
        if(deliveryReturnList.size() <= 0)
        {
            return;
        }
        ceb712Message.setGuid(deliveryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb712Message);
        String resultXml=customData(xml, dxpid, "CEB712Message");
        String queue = dxpid+"_HZ";
        if(importDeliveryService.updateBatchById(updateImportDeliveryList)){
            mqSender.sendMsg(queue, resultXml,"CEB712Message");
        }

    }
}
