package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.InvtCancel;
import com.kzkj.pojo.vo.response.invtCancel.InvtCancelReturn;

/**
 * <p>
 * 撤销申请单 服务类
 * </p>
 * @since 2019-12-11
 */
public interface InvtCancelService extends IService<InvtCancel> {

    InvtCancel getByAgentCodeAndCopNo(String agentCode,String copNo);

    InvtCancelReturn checkInvtCancel(InvtCancelReturn invtCancelReturn,InvtCancel invtCancel);

}
