package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportInvtCancelMapper;
import com.kzkj.pojo.po.ImportInvtCancel;
import com.kzkj.pojo.vo.response.invtCancel.ImportInvtCancelReturn;
import com.kzkj.service.ImportInvtCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 撤销申请单 服务实现类
 * </p>
 * @since 2019-12-12
 */
@Service
@Slf4j
public class ImportInvtCancelServiceImpl extends ServiceImpl<ImportInvtCancelMapper, ImportInvtCancel> implements ImportInvtCancelService {

    @Autowired
    private ImportInvtCancelMapper importInvtCancelMapper;

    @Override
    public ImportInvtCancel getByAgentCodeAndCopNo(String agentCode, String copCode) {
        return importInvtCancelMapper.getByAgentCodeAndCopNo(agentCode,copCode);
    }

    @Override
    public ImportInvtCancelReturn checkImportInvtCancel(ImportInvtCancelReturn importInvtCancelReturn, ImportInvtCancel importInvtCancel) {
        String msg = "新增申报成功["+importInvtCancel.getAgentCode()+"+"+importInvtCancel.getCopNo()+"]";
        String status = "2";
        importInvtCancelReturn.setReturnInfo(msg);
        importInvtCancelReturn.setReturnStatus(status);
        return importInvtCancelReturn;
    }
}
