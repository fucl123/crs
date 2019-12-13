package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportDeliveryDetailMapper;
import com.kzkj.mapper.ImportDeliveryMapper;
import com.kzkj.pojo.po.ImportDelivery;
import com.kzkj.pojo.po.ImportDeliveryDetail;
import com.kzkj.pojo.vo.response.delivery.DeliveryReturn;
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
    public boolean insertImportDelivery(List<ImportDelivery> importDeliveries) {
        if(importDeliveries == null || importDeliveries.size() <= 0) return false;
        for(ImportDelivery w : importDeliveries)
        {
            importDeliveryMapper.insert(w);
            if (w.getImportDeliveryDetailList() == null || w.getImportDeliveryDetailList().size() <= 0) continue;
            for(ImportDeliveryDetail wd : BeanMapper.mapList(w.getImportDeliveryDetailList(),ImportDeliveryDetail.class))
            {
                wd.setDeliveryId(w.getId());
                importDeliveryDetailMapper.insert(wd);
            }
        }
        return true;
    }

    @Override
    public ImportDelivery getByOperatorCodeAndCopNo(String operatorCode, String copNo) {
        return importDeliveryMapper.getByOperatorCodeAndCopNo(operatorCode,copNo);
    }

    @Override
    public DeliveryReturn checkImportDelivery(DeliveryReturn wayBillReturn, ImportDelivery importDelivery) {
        wayBillReturn.setReturnInfo("新增申报成功["+importDelivery.getOperatorCode()+"+"+importDelivery.getCopNo()+"]");
        wayBillReturn.setReturnStatus("2");
        return wayBillReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateImportDelivery(List<ImportDelivery> importDeliveryList) {
        if(importDeliveryList == null || importDeliveryList.size() <= 0) return false;
        for(ImportDelivery w : importDeliveryList)
        {
            importDeliveryMapper.updateById(w);
            if (w.getImportDeliveryDetailList() == null || w.getImportDeliveryDetailList().size() <= 0) continue;
            EntityWrapper<ImportDelivery> wrapper = new EntityWrapper<ImportDelivery>();
            wrapper.eq("delivery_id",w.getId());
            importDeliveryMapper.delete(wrapper);
            for(ImportDeliveryDetail wd : w.getImportDeliveryDetailList())
            {
                wd.setDeliveryId(w.getId());
                importDeliveryDetailMapper.insert(wd);
            }
        }
        return true;
    }
}
