package com.feng.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.domain.db.Question;
import com.feng.dubbo.mapper.QuestionMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author f
 * @date 2022/7/24 15:45
 */
@Service
public class QuestionApiImpl implements QuestionApi{

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 根据用户id查询问题
     * @param userId userId
     * @return question
     */
    @Override
    public Question findByUserId(Long userId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return questionMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public void update(Question question) {
        questionMapper.updateById(question);
    }
}
