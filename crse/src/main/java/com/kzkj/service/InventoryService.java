package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.po.Order;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;

import java.util.List;

public interface InventoryService extends IService<Inventory> {

    boolean insertInventorys(List<Inventory> inventorys);

    Inventory getByOrderNoAndEbcCode(String orderNo,String ebcCode);

    boolean inventory120Update(List<Order> orderList, List<Logistics> logisticsList,List<Inventory> inventoryList);

    Inventory getByLogisticsCodeAndNo(String logisticsCode,String logisticsNo);

    InventoryReturn checkInventory(InventoryReturn inventoryReturn,Inventory inventory);

    boolean batchUpdateInventory(List<Inventory> inventoryList);
}
