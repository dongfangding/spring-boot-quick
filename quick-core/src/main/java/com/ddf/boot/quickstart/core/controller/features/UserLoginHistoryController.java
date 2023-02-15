package com.ddf.boot.quickstart.core.controller.features;

import com.ddf.boot.common.api.model.common.PageResult;
import com.ddf.boot.mongo.helper.MongoTemplateHelper;
import com.ddf.boot.quickstart.api.request.features.PageUserHistoryBo;
import com.ddf.boot.quickstart.core.features.mongo.collection.UserLoginHistoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录日志控制器$
 *
 * @author dongfang.ding
 * @menu 用户登录日志
 * @date 2020/9/19 0019 14:42
 */
@RestController
@RequestMapping("userLoginHistory")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserLoginHistoryController {
    private final MongoTemplateHelper mongoTemplateHelper;

    /**
     * 分页查询用户登录日志
     *
     * @return
     */
    @PostMapping("pageList")
    public PageResult<UserLoginHistoryCollection> pageList(@RequestBody PageUserHistoryBo pageUserHistoryBo) {
        Query query = new Query();
        if (StringUtils.isNotBlank(pageUserHistoryBo.getUsername())) {
            query.addCriteria(Criteria.where("username").regex(pageUserHistoryBo.getUsername()));
        }
        if (pageUserHistoryBo.getUserId() != null) {
            query.addCriteria(Criteria.where("userId").is(pageUserHistoryBo.getUserId()));
        }
        return mongoTemplateHelper.handlerPageResult(pageUserHistoryBo, query, UserLoginHistoryCollection.class);
    }
}
