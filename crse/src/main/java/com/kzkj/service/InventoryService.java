package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Inventory;

import java.util.List;

public interface InventoryService extends IService<Inventory> {

    boolean insertInventorys(List<com.kzkj.pojo.vo.request.invt.Inventory> inventorys);
}
