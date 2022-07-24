package com.feng.domain.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.Collection;

/**
 * @author f
 * @date 2022/7/24 22:01
 */
@Data
@Document(collection = "recommend_user")
public class RecommendUser implements Serializable {

    @Id
    private ObjectId id; //主键id
    @Indexed
    private Long userId; //推荐的用户id
    private Long toUserId; //登录用户id
    @Indexed
    private Double score =0d; //推荐得分
    private String date; //日期
}
