package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportDeliveryDetailMapper;
import com.kzkj.mapper.ImportDeliveryMapper;
import com.kzkj.pojo.po.ImportDelivery;
import com.kzkj.pojo.po.ImportDeliveryDetail;
import com.kzkj.pojo.vo.request.delivery.Delivery;
import com.kzkj.service.ImportDeliveryService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImportDeliveryServiceImpl extends ServiceImpl<ImportDeliveryMapper, ImportDelivery> implements ImportDeliveryService {

    @Autowired
    ImportDeliveryMapper importDeliveryMapper;

    @Autowired
    ImportDeliveryDetailMapper importDeliveryDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertImportDelivery(List<Delivery> deliverys) {
        if (deliverys == null || deliverys.size() <= 0) return false;
        for (Delivery delivery : deliverys)
        {
            ImportDelivery importDelivery = new ImportDelivery();
            BeanMapper.map(delivery.getDeliveryHead(),importDelivery);
            if (delivery.getDeliveryList() == null || delivery.getDeliveryList().size() <= 0)
                continue;
            importDeliveryMapper.insert(importDelivery);
            for (ImportDeliveryDetail importDeliveryDetail: BeanMapper.mapList(delivery.getDeliveryList(), ImportDeliveryDetail.class))
            {
                importDeliveryDetail.setDeliveryId(importDelivery.getId());
                importDeliveryDetailMapper.insert(importDeliveryDetail);
            }
        }
        return true;
    }
}
