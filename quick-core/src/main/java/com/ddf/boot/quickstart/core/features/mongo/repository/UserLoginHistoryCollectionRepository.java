package com.ddf.boot.quickstart.core.features.mongo.repository;

import com.ddf.boot.quickstart.core.features.mongo.collection.UserLoginHistoryCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongo 用户登录日志
 *
 * @author dongfang.ding
 * @date 2020/9/19 0019 12:54
 */
public interface UserLoginHistoryCollectionRepository extends MongoRepository<UserLoginHistoryCollection, String> {

}
