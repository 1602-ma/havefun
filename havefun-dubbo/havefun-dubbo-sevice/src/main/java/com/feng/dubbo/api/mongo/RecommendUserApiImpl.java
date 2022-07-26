package com.feng.dubbo.api.mongo;

import com.feng.domain.mongo.RecommendUser;
import com.feng.domain.vo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author f
 * @date 2022/7/24 22:06
 */
@Service
public class RecommendUserApiImpl implements RecommendUserApi{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RecommendUser queryWithMaxScore(Long toUserId) {
        Criteria criteria = Criteria.where("toUserId").is(toUserId);
        Query query = new Query(criteria)
                .with(Sort.by(Sort.Order.desc("score")))
                .limit(1);
        return mongoTemplate.findOne(query, RecommendUser.class);
    }

    /**
     * 推荐列表分页查询
     * @param page
     * @param pagesize
     * @param userId
     * @return
     */
    @Override
    public PageResult<RecommendUser> findPage(int page, int pagesize, long userId) {
        Query query = new Query();
        // 查询条件
        query.addCriteria(Criteria.where("toUserId").is(userId));
        // 获取 总记录数
        long total = mongoTemplate.count(query, RecommendUser.class);
        // 分页参数设置
        PageRequest pageRequest = PageRequest.of(page-1,pagesize,Sort.by(Sort.Order.desc("score")));
        query.with(pageRequest);
        // 查询分页结果集
        List<RecommendUser> recommendUserList = mongoTemplate.find(query, RecommendUser.class);
        // 封装返回对象
        PageResult<RecommendUser> pageResult = new PageResult<RecommendUser>();
        pageResult.setItems(recommendUserList);
        pageResult.setPage((long)page);
        pageResult.setPagesize((long)pagesize);
        pageResult.setCounts(total);
        // 计算总页数
        long pages = total/pagesize;
        pages+=total%pagesize>0?1:0;
        pageResult.setPages(pages);

        return pageResult;
    }
}
