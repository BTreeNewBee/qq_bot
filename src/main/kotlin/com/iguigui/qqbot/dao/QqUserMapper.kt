package com.iguigui.qqbot.dao;

import com.iguigui.qqbot.entity.QqUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author iguigui
 * @since 2020-11-21
 */
@Mapper
interface QqUserMapper : BaseMapper<QqUser>
