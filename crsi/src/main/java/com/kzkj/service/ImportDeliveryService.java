package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportDelivery;
import com.kzkj.pojo.vo.request.delivery.Delivery;

import java.util.List;

public interface ImportDeliveryService extends IService<ImportDelivery> {

    boolean insertImportDelivery(List<Delivery> deliverys);
}
