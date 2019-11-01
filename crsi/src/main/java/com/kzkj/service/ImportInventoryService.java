package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportInventory;
import com.kzkj.pojo.vo.request.invt.Inventory;

import java.util.List;

public interface ImportInventoryService extends IService<ImportInventory> {
    boolean insertInventorys(List<com.kzkj.pojo.vo.request.invt.ImportInventory> inventorys);
}
