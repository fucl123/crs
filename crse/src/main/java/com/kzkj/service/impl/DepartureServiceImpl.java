package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.DepartureMapper;
import com.kzkj.pojo.po.Departure;
import com.kzkj.service.DepartureService;
import org.springframework.stereotype.Service;

@Service
public class DepartureServiceImpl extends ServiceImpl<DepartureMapper, Departure> implements DepartureService {
}
