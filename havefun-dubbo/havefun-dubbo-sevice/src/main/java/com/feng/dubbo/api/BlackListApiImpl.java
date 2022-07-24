package com.feng.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.domain.db.BlackList;
import com.feng.domain.db.UserInfo;
import com.feng.domain.vo.PageResult;
import com.feng.dubbo.mapper.BlackListMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author f
 * @date 2022/7/24 17:06
 */
@Service
public class BlackListApiImpl implements BlackListApi{

    @Autowired
    private BlackListMapper blackListMapper;

    @Override
    public PageResult<UserInfo> findBlackList(int page, int pageSize, Long id) {
        Page pageRequest = new Page(page, pageSize);
        IPage<UserInfo> pageInfo = blackListMapper.findBlackList(pageRequest, id);
        PageResult<UserInfo> pageResult = new PageResult<UserInfo>(pageInfo.getTotal(), pageInfo.getSize(),pageInfo.getPages(),pageInfo.getCurrent(),pageInfo.getRecords());
        return pageResult;
    }

    @Override
    public void delete(Long userId, Long blackUserId) {
        QueryWrapper<BlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("black_user_id", blackUserId);
        blackListMapper.delete(queryWrapper);
    }
}
