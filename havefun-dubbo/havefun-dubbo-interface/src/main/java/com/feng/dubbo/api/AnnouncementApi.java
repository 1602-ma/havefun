package com.feng.dubbo.api;

import com.feng.domain.db.Announcement;
import com.feng.domain.vo.PageResult;

/**
 * @author f
 * @date 2022/7/26 21:59
 */
public interface AnnouncementApi {

    /**
     * 查询公告列表
     * @param page 第几页
     * @param pageSize 每页数
     * @return 公告列表
     */
    PageResult<Announcement> findAll(int page, int pageSize);
}
