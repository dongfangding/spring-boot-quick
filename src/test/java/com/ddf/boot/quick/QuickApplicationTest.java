package com.ddf.boot.quick;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 *
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 12:02
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class QuickApplicationTest {

    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected static MockMvc mockMvc;

//    @Autowired
//    private UserDetailsService userDetailsService;

    private static MockHttpServletRequestBuilder builder;

    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // 如果使用SpringSecrity的话
//        JwtUser userDetails = (JwtUser) this.userDetailsService.loadUserByUsername("admin");
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public MvcResult request(HttpMethod method, String url, String content) {
        return request(method, url, content, null);
    }

    public MvcResult request(HttpMethod method, String url, Map<String, String> params) {
        return request(method, url, null, params);
    }


    public MvcResult request(HttpMethod method, String url, String content, Map<String, String> params) {
        try {

            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(method, url).contentType(MediaType.APPLICATION_JSON_UTF8)
                    .characterEncoding("UTF-8")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMTIwOTQ2NjE1MzM2NjIyOTA0N1wiLFwidXNlcm5hbWVcIjpcImFkbWluXCIsXCJhdmF0YXJcIjpudWxsLFwiZW1haWxcIjpudWxsLFwicGhvbmVcIjpudWxsLFwibGFzdFBhc3N3b3JkUmVzZXRUaW1lXCI6MTU3OTQwMDk4NDAwMCxcImxhc3RMb2dpblRpbWVcIjoxNTc5NDI1OTgwOTAzLFwiaXBcIjpcIjE5Mi4xNjguMS42NlwiLFwiZW5hYmxlZFwiOnRydWUsXCJjcmVhdGVUaW1lXCI6XCIyMDE5LTEyLTI0IDIxOjI5OjM2XCIsXCJtZXJjaGFudElkXCI6XCIxMjA5NDY2MTUzMjkwNzMxNTMwXCIsXCJyb2xlSWRzXCI6W1wiMTIwOTQ2NjE1MzUyOTgwNjkwMFwiXSxcImVudlwiOlwiZGV2XCIsXCJhcHBsaWNhdGlvblwiOlwicHJvdmlkZXItYm9zcy1tZXJjaGFudFwifSIsImV4cCI6MTU4MDE0NTk4MCwiaWF0IjoxNTc5NDI1OTgwfQ.0zxDLRTg39heoF9DP1bVxT1P28QOuWaYCtAZY6YQSIjSd6iq2w2nxAyz9kbENZDEBHIdqXVJj-qn79rtXuCJ6A");
            if (StringUtils.isNotBlank(content)) {
                builder.content(content);
            }

            if (params != null && !params.isEmpty()) {
                params.forEach(builder::param);
            }

            return mockMvc.perform(builder)
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect((result -> {
                        // 单纯判断这个没有用处，现在异常是通过消息体的状态码来判断的，
                        MockMvcResultMatchers.status().isOk();
                    })).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
