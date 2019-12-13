package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.receipts.CEB403Message;
import com.kzkj.pojo.vo.request.receipts.Receipts;
import com.kzkj.pojo.vo.response.invt.CEB604Message;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.pojo.vo.response.logistics.CEB506Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.pojo.vo.response.order.CEB304Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.pojo.vo.response.receipts.CEB404Message;
import com.kzkj.pojo.vo.response.receipts.ReceiptsReturn;
import com.kzkj.service.*;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReceiptsEventListener extends BaseListener{

    @Autowired
    ReceiptsService receiptsService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    OrderService orderService;

    @Autowired
    InventoryService inventoryService;

    @Subscribe
    public void listener(CEB403Message event){
        CEB404Message ceb404Message=new CEB404Message();
        List<ReceiptsReturn> receiptsReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.Receipts> receiptsList= new ArrayList<com.kzkj.pojo.po.Receipts>();
        List<com.kzkj.pojo.po.Receipts> updateReceiptsList= new ArrayList<com.kzkj.pojo.po.Receipts>();

        for(Receipts receipts:event.getReceipts())
        {
            com.kzkj.pojo.po.Receipts rece = new com.kzkj.pojo.po.Receipts();
            ReceiptsReturn receiptsReturn =new ReceiptsReturn();
            receiptsReturn.setGuid(receipts.getGuid());
            receiptsReturn.setEbcCode(receipts.getEbcCode());
            receiptsReturn.setOrderNo(receipts.getOrderNo());
            receiptsReturn.setPayNo(receipts.getPayNo());
            String now = sdf.format(new Date());
            receiptsReturn.setReturnTime(now);
            rece.setReturnTime(now);
            BeanMapper.map(receipts,rece);
            com.kzkj.pojo.po.Receipts oldReceipts =
                    receiptsService.getByEbcCodeAndOrderNo(rece.getEbcCode(),rece.getOrderNo());

            //数据查重
            if(oldReceipts == null)
            {
                //数据校验
                receiptsReturn = receiptsService.checkReceipts(receiptsReturn,rece);
                rece.setReturnStatus(receiptsReturn.getReturnStatus());
                rece.setReturnInfo(receiptsReturn.getReturnInfo());
                receiptsList.add(rece);
            }else {
                if(!oldReceipts.getReturnStatus().equals("2")
                        &&!oldReceipts.getReturnStatus().equals("120"))//如果是退单
                {
                    //数据校验
                    receiptsReturn = receiptsService.checkReceipts(receiptsReturn,rece);
                    if (receiptsReturn.getReturnStatus().equals("2"))
                    {
                        rece.setId(oldReceipts.getId());
                        rece.setReturnStatus(receiptsReturn.getReturnStatus());
                        rece.setReturnInfo(receiptsReturn.getReturnInfo());
                        updateReceiptsList.add(rece);
                    }
                }else{
                    receiptsReturn.setReturnInfo("重复申报，业务主键["
                            + rece.getEbcCode() + "+" + rece.getOrderNo()
                            + "]");
                    receiptsReturn.setReturnStatus("-304001");
                    rece.setReturnStatus(receiptsReturn.getReturnStatus());
                    rece.setReturnInfo(receiptsReturn.getReturnInfo());
                }
            }
            receiptsReturnList.add(receiptsReturn);
        }

        ceb404Message.setReceiptsReturn(receiptsReturnList);
        ceb404Message.setGuid(receiptsReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb404Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB404Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB404Message");
        //插入数据库
        receiptsService.insertBatch(receiptsList);
        //更新退单记录
        if (updateReceiptsList.size() > 0)
            receiptsService.updateAllColumnBatchById(updateReceiptsList);
        //订运清三单对碰成功后发送收款单120回执
        receiptsList.addAll(updateReceiptsList);
        send120Receipts(receiptsList,baseTransfer.getDxpId());
    }

    /**
     * 订运清三单对碰成功后发送收款单120回执
     * @param receiptsList
     * @param dxpid
     */
    private void send120Receipts(List<com.kzkj.pojo.po.Receipts> receiptsList,String dxpid)
    {
        if (receiptsList == null || receiptsList.size() <= 0) return;
        ///收款单120回执
        List<ReceiptsReturn> receipts120ReturnList= new ArrayList<ReceiptsReturn>();
        //收款单120更新
        List<com.kzkj.pojo.po.Receipts> receiptsUpdateList= new ArrayList<com.kzkj.pojo.po.Receipts>();
        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Receipts receipts: receiptsList)
        {
            if (!receipts.getReturnStatus().equals("2")) continue;
            Inventory inventory = inventoryService.getByOrderNoAndEbcCode(receipts.getOrderNo(),receipts.getEbcCode());
            if(inventory == null || !inventory.getReturnStatus().equals("399")) continue;
            //收款单120回执
            ReceiptsReturn receiptsReturn =new ReceiptsReturn();
            BeanMapper.map(receipts,receiptsReturn);
            receiptsReturn.setReturnInfo("逻辑校验通过["+receipts.getOrderNo()+"+"+receipts.getEbcCode()+"]");
            receiptsReturn.setReturnStatus("120");
            receiptsReturn.setReturnTime(now);
            receipts.setReturnInfo(receiptsReturn.getReturnInfo());
            receipts.setReturnStatus(receiptsReturn.getReturnStatus());
            receipts.setReturnTime(receiptsReturn.getReturnTime());
            receipts120ReturnList.add(receiptsReturn);
            receiptsUpdateList.add(receipts);
        }
        //更新数据库
        if (receiptsUpdateList.size() > 0 && receiptsService.updateBatchById(receiptsUpdateList))
        {
            String queue = dxpid + "_HZ";
            //发送收款单120回执报文
            CEB404Message ceb404Message=new CEB404Message();
            ceb404Message.setReceiptsReturn(receipts120ReturnList);
            ceb404Message.setGuid(receipts120ReturnList.get(0).getGuid());
            String xml = XMLUtil.convertToXml(ceb404Message);
            String resultXml = customData(xml, dxpid, "CEB404Message");
            mqSender.sendMsg(queue, resultXml,"CEB404Message");
        }
    }
}
