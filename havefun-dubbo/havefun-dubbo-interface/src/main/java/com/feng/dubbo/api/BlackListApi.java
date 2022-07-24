package com.feng.dubbo.api;

import com.feng.domain.db.UserInfo;
import com.feng.domain.vo.PageResult;

/**
 * @author f
 * @date 2022/7/24 17:00
 */
public interface BlackListApi {

    /**
     * 黑名单列表分页查询
     * @param page 第几页
     * @param pageSize 页数
     * @param id id
     * @return 黑名单
     */
    PageResult<UserInfo> findBlackList(int page, int pageSize, Long id);

    /**
     * 根据用户id和黑名单用户id，删除
     * @param userId 用户id
     * @param blackUserId 黑名单id
     */
    void delete(Long userId, Long blackUserId);
}
