package com.ddf.boot.quickstart.core.controller;


import com.ddf.boot.quickstart.api.dto.SysDictDTO;
import com.ddf.boot.quickstart.api.request.sys.SysDictGetByCodeRequest;
import com.ddf.boot.quickstart.core.biz.ISysDictBizService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @menu 字典服务
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@RestController
@RequestMapping("/sysDict")
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysDictController {

    private final ISysDictBizService sysDictBizService;


    /**
     * 根据字典代码获取字典明细数据
     *
     * @param request
     * @return
     */
    @PostMapping("getDictByCode")
    public List<SysDictDTO> getDictByCode(@RequestBody @Validated SysDictGetByCodeRequest request) {
        return sysDictBizService.getDictByCode(request);
    }
}

