package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.InvtCancelMapper;
import com.kzkj.pojo.po.InvtCancel;
import com.kzkj.pojo.vo.response.invtCancel.InvtCancelReturn;
import com.kzkj.service.InvtCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 撤销申请单 服务实现类
 * </p>
 * @since 2019-12-11
 */
@Service
@Slf4j
public class InvtCancelServiceImpl extends ServiceImpl<InvtCancelMapper, InvtCancel> implements InvtCancelService {

    @Autowired
    private InvtCancelMapper invtCancelMapper;

    @Override
    public InvtCancel getByAgentCodeAndCopNo(String agentCode, String copNo) {
        return invtCancelMapper.getByAgentCodeAndCopNo(agentCode,copNo);
    }

    @Override
    public InvtCancelReturn checkInvtCancel(InvtCancelReturn invtCancelReturn, InvtCancel invtCancel) {
        String msg = "新增申报成功["+invtCancel.getAgentCode()+"+"+invtCancel.getCopNo()+"]";
        String status = "2";
        invtCancelReturn.setReturnInfo(msg);
        invtCancelReturn.setReturnStatus(status);
        return invtCancelReturn;
    }
}
