package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportOrderDetailMapper;
import com.kzkj.pojo.po.ImportOrderDetail;
import com.kzkj.service.ImportOrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class ImportOrderDetailServiceImpl extends ServiceImpl<ImportOrderDetailMapper, ImportOrderDetail> implements ImportOrderDetailService {
}
