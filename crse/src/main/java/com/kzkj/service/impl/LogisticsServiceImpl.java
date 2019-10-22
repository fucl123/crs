package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.LogisticsMapper;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.service.LogisticsService;
import org.springframework.stereotype.Service;

@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {
}
