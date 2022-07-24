package com.feng.dubbo.api.mongo;

import com.feng.domain.mongo.RecommendUser;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
}
