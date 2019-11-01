package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.invt.CEB621Message;
import com.kzkj.pojo.vo.request.invt.ImportInventory;
import com.kzkj.pojo.vo.response.invt.CEB622Message;
import com.kzkj.pojo.vo.response.invt.ImportInventoryReturn;
import com.kzkj.service.ImportInventoryService;
import com.kzkj.utils.XMLUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ImportInventoryListener extends BaseListener{

    @Autowired
    ImportInventoryService importInventoryService;

    @Subscribe
    public void listener(CEB621Message event){
        CEB622Message ceb622Message=new CEB622Message();
        List<ImportInventoryReturn> importInventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(ImportInventory importInventory : event.getInventory())
        {
            ImportInventoryReturn importInventoryReturn =new ImportInventoryReturn();
            importInventoryReturn.setGuid(importInventory.getInventoryHead().getGuid());
            importInventoryReturn.setCopNo(importInventory.getInventoryHead().getCopNo());
            importInventoryReturn.setCustomsCode(importInventory.getInventoryHead().getCustomsCode());
            importInventoryReturn.setPreNo("123456789");
            importInventoryReturn.setEbcCode(importInventory.getInventoryHead().getEbcCode());
            importInventoryReturn.setEbpCode(importInventory.getInventoryHead().getEbpCode());
            if (StringUtils.isNotBlank(importInventory.getInventoryHead().getInvtNo())){
                importInventoryReturn.setInvtNo(importInventory.getInventoryHead().getInvtNo());
            }else{
                importInventoryReturn.setInvtNo("123");
            }
            importInventoryReturn.setAgentCode(importInventory.getInventoryHead().getAgentCode());
            String now = sdf.format(new Date());
            importInventoryReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                importInventoryReturn.setReturnInfo("新增申报成功["+importInventory.getInventoryHead().getGuid()+"]");
                importInventoryReturn.setReturnStatus("2");
            }else {
                importInventoryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + importInventory.getInventoryHead().getCopNo()
                        + "]，原清单报送时间对应状态为["
                        + now + " : 2-申报;]");
                importInventoryReturn.setReturnStatus("-304001");
            }

            importInventoryReturnList.add(importInventoryReturn);
        }

        ceb622Message.setInventoryReturn(importInventoryReturnList);
        ceb622Message.setGuid(importInventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb622Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB622Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB622Message");

        //插入数据库
        //importInventoryService.insertInventorys(event.getInventory());
    }
}
