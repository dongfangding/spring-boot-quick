package com.ddf.boot.quick.mongo.repository;

import com.ddf.boot.quick.mongo.collection.UserLoginHistoryCollection;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Mongo 用户登录日志
 *
 * @author dongfang.ding
 * @date 2020/9/19 0019 12:54
 */
public interface UserLoginHistoryCollectionRepository extends PagingAndSortingRepository<UserLoginHistoryCollection, String> {

}
