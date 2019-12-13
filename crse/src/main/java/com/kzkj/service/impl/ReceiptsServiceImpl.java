package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ReceiptsMapper;
import com.kzkj.pojo.po.Receipts;
import com.kzkj.pojo.vo.response.receipts.ReceiptsReturn;
import com.kzkj.service.ReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 * @since 2019-12-11
 */
@Service
public class ReceiptsServiceImpl extends ServiceImpl<ReceiptsMapper, Receipts> implements ReceiptsService {

    @Autowired
    private ReceiptsMapper receiptsMapper;

    @Override
    public Receipts getByEbcCodeAndOrderNo(String ebcCode, String orderNo) {
        return receiptsMapper.getByEbcCodeAndOrderNo(ebcCode,orderNo);
    }

    @Override
    public ReceiptsReturn checkReceipts(ReceiptsReturn receiptsReturn, Receipts receipts) {
        String msg = "新增申报成功["+receipts.getOrderNo()+"+"+receipts.getEbcCode()+"]";
        String status = "2";
        receiptsReturn.setReturnStatus(status);
        receiptsReturn.setReturnInfo(msg);
        return receiptsReturn;
    }
}
