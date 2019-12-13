package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportInventoryDetailMapper;
import com.kzkj.mapper.ImportInventoryMapper;
import com.kzkj.mapper.ImportLogisticsMapper;
import com.kzkj.mapper.ImportOrderMapper;
import com.kzkj.pojo.po.ImportInventory;
import com.kzkj.pojo.po.ImportInventoryDetail;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.vo.response.invt.ImportInventoryReturn;
import com.kzkj.service.ImportInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImportInventoryServiceImpl extends ServiceImpl<ImportInventoryMapper, ImportInventory> implements ImportInventoryService {

    @Autowired
    ImportInventoryMapper importInventoryMapper;

    @Autowired
    ImportInventoryDetailMapper importInventoryDetailMapper;

    @Autowired
    ImportOrderMapper importOrderMapper;

    @Autowired
    ImportLogisticsMapper importLogisticsMapper;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertInventorys(List<ImportInventory> inventorys)
    {
        if (inventorys == null || inventorys.size() <= 0) return false;
        for (ImportInventory i : inventorys)
        {
            importInventoryMapper.insert(i);
            if(i.getImportInventoryDetailList() == null || i.getImportInventoryDetailList().size() <= 0)
                continue;
            for (ImportInventoryDetail inventoryDetail: i.getImportInventoryDetailList())
            {
                inventoryDetail.setInventoryId(i.getId());
                importInventoryDetailMapper.insert(inventoryDetail);
            }
        }
        return true;
    }

    @Override
    public ImportInventory getByOrderNoAndEbcCode(String orderNo, String ebcCode) {
        return importInventoryMapper.getByOrderNoAndEbcCode(orderNo,ebcCode);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean inventory120Update(
            List<ImportOrder> orderList, List<ImportLogistics> logisticsList, List<ImportInventory> inventoryList) {
        if(orderList == null || logisticsList == null || inventoryList ==null
                || orderList.size() <= 0 || logisticsList.size() <= 0 || inventoryList.size() <= 0)
            return false;
        for(ImportOrder o: orderList)
        {
            importOrderMapper.updateReturnStatus(o);
        }
        for(ImportLogistics l: logisticsList)
        {
            importLogisticsMapper.updateReturnStatus(l);
        }
        for(ImportInventory i: inventoryList)
        {
            importInventoryMapper.updateReturnStatus(i);
        }
        return true;
    }

    @Override
    public ImportInventory getByLogisticsCodeAndNo(String logisticsCode, String logisticsNo) {
        return importInventoryMapper.getByLogisticsCodeAndNo(logisticsCode,logisticsNo);
    }

    @Override
    public ImportInventoryReturn checkInventory(ImportInventoryReturn inventoryReturn, ImportInventory inventory) {

        String msg = "";
        String status = "";
        inventoryReturn.setReturnInfo("新增申报成功！");
        inventoryReturn.setReturnStatus("2");
        /*if(inventory.getGrossWeight().compareTo(BigDecimal.ZERO) <= 0)
        {
            msg = "毛重[grossWeight]应大于0";
            status = "100";
        }else if (inventory.getNetWeight().compareTo(BigDecimal.ZERO) <= 0){
            msg = "毛重[netWeight]应大于0";
            status = "100";
        }else if (inventory.getNetWeight().compareTo(inventory.getGrossWeight()) > 0){
            msg = "净重[netWeight]要等于小于毛重[grossWeight]";
            status = "100";
        }else if (){

        }else if (){

        }else if (){

        }else if(inventory.getInventoryDetailList() == null
                || inventory.getInventoryDetailList().size() == 0)
        {
            msg = "清单表体不能为空";
            status = "100";
        }
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
           }else if()
           {

           }
        }*/
        return inventoryReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateInventory(List<ImportInventory> inventoryList)
    {
        if (inventoryList == null || inventoryList.size() <= 0) return false;
        for(ImportInventory inventory: inventoryList)
        {
            importInventoryMapper.updateById(inventory);
            if (inventory.getImportInventoryDetailList() == null || inventory.getImportInventoryDetailList().size() <= 0)
                continue;
            EntityWrapper<ImportInventoryDetail> wrapper = new EntityWrapper<>();
            wrapper.eq("inventory_id",inventory.getId());
            importInventoryDetailMapper.delete(wrapper);
            for (ImportInventoryDetail inventoryDetail : inventory.getImportInventoryDetailList())
            {
                inventoryDetail.setInventoryId(inventory.getId());
                importInventoryDetailMapper.insert(inventoryDetail);
            }
        }
        return true;
    }
}
