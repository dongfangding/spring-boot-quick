package com.ddf.boot.quick.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddf.boot.common.core.util.IdsUtil;
import com.ddf.boot.quick.model.entity.UserArticle;
import com.ddf.boot.quick.service.UserArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2020/9/4 0004 23:39
 */
@RestController
@RequestMapping("userArticle")
@Api(value = "用户文章控制器", tags = {"用户文章控制器"})
public class UserArticleController {

    @Autowired
    private UserArticleService userArticleService;

    @PostMapping("createArticle")
    @ApiOperation("创建用户文章")
    public Boolean createArticle(@RequestBody UserArticle userArticle) {
        LambdaQueryWrapper<UserArticle> articleQuery = Wrappers.lambdaQuery();
        articleQuery.eq(UserArticle::getUserId, userArticle.getUserId());
        userArticleService.list(articleQuery);

        userArticle.setId(IdsUtil.getNextLongId());
        return userArticleService.save(userArticle);
    }

    @GetMapping("pageList")
    @ApiOperation("分页查询用户文章列表")
    public IPage<UserArticle> pageList(Page<UserArticle> page) {
        return userArticleService.page(page);
    }
}
