package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.LogisticsMapper;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.po.OrderDetail;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.service.LogisticsService;
import com.kzkj.utils.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Autowired
    LogisticsMapper logisticsMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertLogistics(List<Logistics> logisticsList) {
        if(logisticsList == null || logisticsList.size() <= 0) return false;
        for (Logistics l : logisticsList)
        {
            logisticsMapper.insert(l);
        }
        return true;
    }

    @Override
    public List<Logistics> getByLogisticsNo(String logisticsNo) {
        return logisticsMapper.getByLogisticsNo(logisticsNo);
    }

    @Override
    public Logistics getByLogisticsCodeAndNo(String logisticsCode, String logisticsNo) {
        return logisticsMapper.getByLogisticsCodeAndNo(logisticsCode,logisticsNo);
    }

    @Override
    public LogisticsReturn checkLogistics(LogisticsReturn logisticsReturn, Logistics logistics) {

        String msg = "新增申报成功["+logistics.getLogisticsCode()+"+"+logistics.getLogisticsNo()+"]";
        String status = "2";

        if(StringUtils.isBlank(logistics.getGuid()))
        {
            msg = "guid不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getAppType())){
            msg = "企业报送类型[appType]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getAppTime())){
            msg = "企业报送时间[appTime]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getAppStatus())){
            msg = "企业报送状态[appStatus]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getLogisticsCode())){
            msg = "物流企业代码[logisticsCode]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getLogisticsNo())){
            msg = "物流运单编号[logisticsNo]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getLogisticsName())){
            msg = "物流企业名称[logisticsName]不能为空";
            status = "100";
        }else if (logistics.getGrossWeight() == null){
            msg = "毛重[grossWeight]不能为空";
            status = "100";
        }else if (logistics.getInsuredFee() == null){
            msg = "保价费[insuredFee]不能为空";
            status = "100";
        }else if (logistics.getPackNo() == null){
            msg = "件数[packNo]不能为空";
            status = "100";
        }else if (logistics.getFreight() == null){
            msg = "运杂费[freight]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(logistics.getCurrency())){
            msg = "币制[currency]不能为空";
            status = "100";
        }
        logisticsReturn.setReturnInfo(msg);
        logisticsReturn.setReturnStatus(status);
        return logisticsReturn;
    }
}
