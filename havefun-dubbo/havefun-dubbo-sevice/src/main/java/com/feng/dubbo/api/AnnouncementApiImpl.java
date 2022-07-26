package com.feng.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.domain.db.Announcement;
import com.feng.domain.vo.PageResult;
import com.feng.dubbo.mapper.AnnouncementMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author f
 * @date 2022/7/26 22:10
 */
@Service
public class AnnouncementApiImpl implements AnnouncementApi{

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public PageResult<Announcement> findAll(int page, int pageSize) {
        Page<Announcement> pages = new Page<>(page, pageSize);
        IPage<Announcement> pageInfo = announcementMapper.selectPage(pages, new QueryWrapper<>());
        PageResult<Announcement> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getSize(),
                pageInfo.getPages(), pageInfo.getCurrent(), pageInfo.getRecords());
        return pageResult;
    }
}
