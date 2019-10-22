package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.invt.CEB603Message;
import com.kzkj.pojo.vo.request.invt.Inventory;
import com.kzkj.pojo.vo.response.invt.CEB604Message;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.utils.XMLUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InventoryEventListener extends BaseListener{
    @Subscribe
    public void listener(CEB603Message event){
        CEB604Message ceb604Message=new CEB604Message();
        List<InventoryReturn> inventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Inventory inventory:event.getInventory())
        {
            InventoryReturn inventoryReturn =new InventoryReturn();
            inventoryReturn.setGuid(inventory.getInventoryHead().getGuid());
            inventoryReturn.setCopNo(inventory.getInventoryHead().getCopNo());
            inventoryReturn.setAgentCode(inventory.getInventoryHead().getAgentCode());
            inventoryReturn.setPreNo("");
            inventoryReturn.setEbcCode(inventory.getInventoryHead().getEbcCode());
            inventoryReturn.setInvtNo("");
            inventoryReturn.setOrderNo(inventory.getInventoryHead().getOrderNo());
            inventoryReturn.setEbpCode(inventory.getInventoryHead().getEbpCode());
            inventoryReturn.setEbpCode(inventory.getInventoryHead().getEbcCode());

            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                inventoryReturn.setReturnInfo("新增申报成功["+inventory.getInventoryHead().getGuid()+"]");
                inventoryReturn.setReturnStatus("2");
            }else {
                inventoryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + inventory.getInventoryHead().getCopNo()
                        + "]，原清单报送时间对应状态为["
                        + now + " : 2-申报;]");
                inventoryReturn.setReturnStatus("-304001");
            }

            inventoryReturnList.add(inventoryReturn);
        }

        ceb604Message.setInventoryReturn(inventoryReturnList);
        ceb604Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb604Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB604Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB604Message");
    }
}
