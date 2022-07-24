package com.feng.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.domain.db.BlackList;
import com.feng.domain.db.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author f
 * @date 2022/7/24 16:56
 */
public interface BlackListMapper extends BaseMapper<BlackList> {
    /**
     * 查询 黑名单用户信息
     * @param page 页数
     * @param userId 用户id
     * @return entity
     */
    @Select(value = "select tui.id,tui.avatar,tui.nickname,tui.gender,tui.age from  tb_user_info tui,tb_black_list tbl where tui.id = tbl.black_user_id and tbl.user_id=#{userId}")
    IPage<UserInfo> findBlackList(Page<UserInfo> page, @Param("userId") Long userId);
}
