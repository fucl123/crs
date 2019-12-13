package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportInventory;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.vo.response.invt.ImportInventoryReturn;

import java.util.List;

public interface ImportInventoryService extends IService<ImportInventory> {
    boolean insertInventorys(List<ImportInventory> inventorys);

    ImportInventory getByOrderNoAndEbcCode(String orderNo,String ebcCode);

    boolean inventory120Update(List<ImportOrder> orderList, List<ImportLogistics> logisticsList, List<ImportInventory> inventoryList);

    ImportInventory getByLogisticsCodeAndNo(String logisticsCode,String logisticsNo);

    ImportInventoryReturn checkInventory(ImportInventoryReturn inventoryReturn, ImportInventory inventory);

    boolean batchUpdateInventory(List<ImportInventory> inventoryList);
}
