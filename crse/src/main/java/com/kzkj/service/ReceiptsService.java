package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Receipts;
import com.kzkj.pojo.vo.response.receipts.ReceiptsReturn;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2019-12-11
 */
public interface ReceiptsService extends IService<Receipts> {

    Receipts getByEbcCodeAndOrderNo(String ebcCode,String orderNo);

    ReceiptsReturn checkReceipts(ReceiptsReturn receiptsReturn,Receipts receipts);
}
