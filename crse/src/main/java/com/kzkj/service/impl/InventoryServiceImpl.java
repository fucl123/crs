package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.InventoryDetailMapper;
import com.kzkj.mapper.InventoryMapper;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.po.InventoryDetail;
import com.kzkj.service.InventoryService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    InventoryDetailMapper inventoryDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertInventorys(List<com.kzkj.pojo.vo.request.invt.Inventory> inventorys)
    {
        if (inventorys == null || inventorys.size() <= 0) return false;
        for (com.kzkj.pojo.vo.request.invt.Inventory i : inventorys)
        {
            Inventory inventory = new Inventory();
            BeanMapper.map(i.getInventoryHead(),inventory);
            if (i.getInventoryList() == null || i.getInventoryList().size() <= 0)
                continue;
            inventoryMapper.insert(inventory);
            for (InventoryDetail inventoryDetail: BeanMapper.mapList(i.getInventoryList(),InventoryDetail.class))
            {
                inventoryDetail.setInventoryId(inventory.getId());
                inventoryDetailMapper.insert(inventoryDetail);
            }
        }
        return true;
    }
}
