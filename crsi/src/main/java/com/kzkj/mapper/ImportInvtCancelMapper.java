package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.ImportInvtCancel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 * @since 2019-12-12
 */
@Mapper
public interface ImportInvtCancelMapper extends BaseMapper<ImportInvtCancel> {

    ImportInvtCancel getByAgentCodeAndCopNo(@Param("agentCode") String agentCode, @Param("copCode")String copCode);

}
