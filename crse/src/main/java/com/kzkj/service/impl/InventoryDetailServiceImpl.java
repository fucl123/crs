package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.InventoryDetailMapper;
import com.kzkj.pojo.po.InventoryDetail;
import com.kzkj.service.InventoryDetailService;
import org.springframework.stereotype.Service;

@Service
public class InventoryDetailServiceImpl extends ServiceImpl<InventoryDetailMapper, InventoryDetail> implements InventoryDetailService {
}
