package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.UserArticleMapper;
import com.ddf.boot.quick.model.entity.UserArticle;
import com.ddf.boot.quick.service.UserArticleService;
import org.springframework.stereotype.Service;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2020/9/4 0004 23:38
 */
@Service
public class UserArticleServiceImpl extends ServiceImpl<UserArticleMapper, UserArticle> implements UserArticleService {
}
