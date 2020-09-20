package com.ddf.boot.quick.controller;

import com.ddf.boot.quick.model.bo.PageUserHistoryBo;
import com.ddf.boot.quick.mongo.collection.UserLoginHistoryCollection;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户登录日志控制器$
 *
 * @author dongfang.ding
 * @date 2020/9/19 0019 14:42
 */
@RestController
@RequestMapping("userLoginHistory")
@Api(value = "用户登录日志控制器$", tags = {"用户登录日志控制器$"})
public class UserLoginHistoryController {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 分页查询用户登录日志
     * @return
     */
    @PostMapping("pageList")
    public Page<UserLoginHistoryCollection> pageList(@RequestBody PageUserHistoryBo pageUserHistoryBo) {
        Query query = new Query();
        if (StringUtils.isNotBlank(pageUserHistoryBo.getUsername())) {
            query.addCriteria(Criteria.where("username").regex(pageUserHistoryBo.getUsername()));
        }
        if (pageUserHistoryBo.getUserId() != null) {
            query.addCriteria(Criteria.where("userId").is(pageUserHistoryBo.getUserId()));
        }
        long count = mongoTemplate.count(query, UserLoginHistoryCollection.class);
        if (count > 0) {
            Pageable pageable = pageUserHistoryBo.ofSpringData();
            query.with(pageable);
            List<UserLoginHistoryCollection> list = mongoTemplate.find(query, UserLoginHistoryCollection.class);
            return new PageImpl<>(list, pageable, count);
        }
        return Page.empty();
    }
}
