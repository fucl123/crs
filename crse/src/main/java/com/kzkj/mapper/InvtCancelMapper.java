package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.InvtCancel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 撤销申请单 Mapper 接口
 * </p>
 * @since 2019-12-11
 */
@Mapper
public interface InvtCancelMapper extends BaseMapper<InvtCancel> {

    InvtCancel getByAgentCodeAndCopNo(@Param("agentCode") String agentCode,@Param("copNo") String copNo);
}
