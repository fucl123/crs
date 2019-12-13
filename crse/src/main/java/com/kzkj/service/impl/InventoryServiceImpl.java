package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.InventoryDetailMapper;
import com.kzkj.mapper.InventoryMapper;
import com.kzkj.mapper.LogisticsMapper;
import com.kzkj.mapper.OrderMapper;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.po.InventoryDetail;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.po.Order;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.service.InventoryService;
import com.kzkj.utils.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryDetailMapper inventoryDetailMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    LogisticsMapper logisticsMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertInventorys(List<Inventory> inventorys)
    {
        if (inventorys == null || inventorys.size() <= 0) return false;
        for (Inventory i : inventorys)
        {
            inventoryMapper.insert(i);
            if(i.getInventoryDetailList() == null || i.getInventoryDetailList().size() <= 0)
            continue;
            for (InventoryDetail inventoryDetail: i.getInventoryDetailList())
            {
                inventoryDetail.setInventoryId(i.getId());
                inventoryDetailMapper.insert(inventoryDetail);
            }
        }
        return true;
    }

    @Override
    public Inventory getByOrderNoAndEbcCode(String orderNo, String ebcCode) {
        return inventoryMapper.getByOrderNoAndEbcCode(orderNo,ebcCode);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean inventory120Update(
            List<Order> orderList, List<Logistics> logisticsList, List<Inventory> inventoryList) {
        if(orderList == null || logisticsList == null || inventoryList ==null
           || orderList.size() <= 0 || logisticsList.size() <= 0 || inventoryList.size() <= 0)
        return false;
        for(Order o: orderList)
        {
            orderMapper.updateReturnStatus(o);
        }
        for(Logistics l: logisticsList)
        {
            logisticsMapper.updateReturnStatus(l);
        }
        for(Inventory i: inventoryList)
        {
            inventoryMapper.updateReturnStatus(i);
        }
        return true;
    }

    @Override
    public Inventory getByLogisticsCodeAndNo(String logisticsCode, String logisticsNo) {
        return inventoryMapper.getByLogisticsCodeAndNo(logisticsCode,logisticsNo);
    }

    @Override
    public InventoryReturn checkInventory(InventoryReturn inventoryReturn, Inventory inventory) {

        String msg = "新增申报成功[\"+inventory.getGuid()+\"]";
        String status = "2";
        if(inventory.getGrossWeight().compareTo(BigDecimal.ZERO) <= 0)
        {
            msg = "毛重[grossWeight]应大于0";
            status = "100";
        }else if (inventory.getNetWeight().compareTo(BigDecimal.ZERO) <= 0){
            msg = "毛重[netWeight]应大于0";
            status = "100";
        }else if (inventory.getNetWeight().compareTo(inventory.getGrossWeight()) > 0){
            msg = "净重[netWeight]要等于小于毛重[grossWeight]";
            status = "100";
        }else if(inventory.getInventoryDetailList() == null
                || inventory.getInventoryDetailList().size() == 0)
        {
            msg = "清单表体不能为空";
            status = "100";
        }else {
            for(InventoryDetail inventoryDetail : inventory.getInventoryDetailList())
            {
                if(inventoryDetail.getPrice().compareTo(BigDecimal.ZERO) <= 0)
                {
                    msg = "单价[price]应大于0";
                    status = "100";
                }else if(inventoryDetail.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    msg = "总价[totalPrice]应大于0";
                    status = "100";
                }else if(inventoryDetail.getUnit().equals(inventoryDetail.getUnit1())) {
                    if(inventoryDetail.getQty().compareTo(inventoryDetail.getQty1()) !=0 )
                    {
                        msg = "当法定单位[unit]和申报单位[unit1]一致时，申报数量[qty]应该等于法定数量[qty2]";
                        status = "100";
                    }
                }else if(StringUtils.isNotBlank(inventoryDetail.getUnit2()))
                {
                    if(inventoryDetail.getQty2() == null)
                    {
                        msg = "第二计量单位[unit2]不为空，则第二数量[qty2]也不能为空且第二数量[qty2]应大于0";
                        status = "100";
                    }else if(inventoryDetail.getQty2().compareTo(BigDecimal.ZERO) <= 0){
                        msg = "第二计量单位[unit2]不为空，则第二数量[qty2]也不能为空且第二数量[qty2]应大于0";
                        status = "100";
                    }
                }else if(StringUtils.isBlank(inventoryDetail.getUnit2()))
                {
                    if(inventoryDetail.getQty2() != null)
                    {
                        msg = "第二计量单位[unit2]为空，则第二数量[qty2]也不能为空";
                        status = "100";
                    }
                }
            }
        }
        inventoryReturn.setReturnInfo(msg);
        inventoryReturn.setReturnStatus(status);
        return inventoryReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateInventory(List<Inventory> inventoryList)
    {
        if (inventoryList == null || inventoryList.size() <= 0) return false;
        for(Inventory inventory: inventoryList)
        {
           inventoryMapper.updateById(inventory);
           if (inventory.getInventoryDetailList() == null || inventory.getInventoryDetailList().size() <= 0)
               continue;
           EntityWrapper<InventoryDetail> wrapper = new EntityWrapper<>();
           wrapper.eq("inventory_id",inventory.getId());
           inventoryDetailMapper.delete(wrapper);
           for (InventoryDetail inventoryDetail : inventory.getInventoryDetailList())
           {
               inventoryDetail.setInventoryId(inventory.getId());
               inventoryDetailMapper.insert(inventoryDetail);
           }
        }
        return true;
    }
}
