package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportInvtCancel;
import com.kzkj.pojo.vo.response.invtCancel.ImportInvtCancelReturn;

/**
 * <p>
 * 撤销申请单 服务类
 * </p>
 * @since 2019-12-12
 */
public interface ImportInvtCancelService extends IService<ImportInvtCancel> {

    ImportInvtCancel getByAgentCodeAndCopNo(String agentCode, String copCode);

    ImportInvtCancelReturn checkImportInvtCancel(ImportInvtCancelReturn importInvtCancelReturn,ImportInvtCancel importInvtCancel);
}
