package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportDelivery;
import com.kzkj.pojo.vo.response.delivery.DeliveryReturn;

import java.util.List;

public interface ImportDeliveryService extends IService<ImportDelivery> {

    boolean insertImportDelivery(List<ImportDelivery> importDeliveries);

    ImportDelivery getByOperatorCodeAndCopNo(String operatorCode,String copNo);

    DeliveryReturn checkImportDelivery(DeliveryReturn deliveryReturn, ImportDelivery importDelivery);

    boolean batchUpdateImportDelivery(List<ImportDelivery> importDeliveryList);
}
