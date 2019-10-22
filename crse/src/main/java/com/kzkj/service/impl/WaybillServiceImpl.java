package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.WaybillMapper;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.service.WaybillService;
import org.springframework.stereotype.Service;

@Service
public class WaybillServiceImpl extends ServiceImpl<WaybillMapper, Waybill> implements WaybillService {
}
