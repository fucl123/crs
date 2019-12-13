package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Receipts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2019-12-11
 */
@Mapper
public interface ReceiptsMapper extends BaseMapper<Receipts> {

    Receipts getByEbcCodeAndOrderNo(@Param("ebcCode") String ebcCode, @Param("orderNo")String orderNo);
}
