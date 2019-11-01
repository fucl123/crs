package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportInventoryDetailMapper;
import com.kzkj.mapper.ImportInventoryMapper;
import com.kzkj.pojo.po.ImportInventory;
import com.kzkj.pojo.po.ImportInventoryDetail;
import com.kzkj.pojo.vo.request.invt.Inventory;
import com.kzkj.service.ImportInventoryService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImportInventoryServiceImpl extends ServiceImpl<ImportInventoryMapper, ImportInventory> implements ImportInventoryService {

    @Autowired
    ImportInventoryMapper inventoryMapper;

    @Autowired
    ImportInventoryDetailMapper inventoryDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertInventorys(List<com.kzkj.pojo.vo.request.invt.ImportInventory> inventorys)
    {
        if (inventorys == null || inventorys.size() <= 0) return false;
        for (com.kzkj.pojo.vo.request.invt.ImportInventory i : inventorys)
        {
            ImportInventory inventory = new ImportInventory();
            BeanMapper.map(i.getInventoryHead(),inventory);
            if (i.getInventoryList() == null || i.getInventoryList().size() <= 0)
                continue;
            inventoryMapper.insert(inventory);
            for (ImportInventoryDetail inventoryDetail: BeanMapper.mapList(i.getInventoryList(), ImportInventoryDetail.class))
            {
                inventoryDetail.setInventoryId(inventory.getId());
                inventoryDetailMapper.insert(inventoryDetail);
            }
        }
        return true;
    }
}
