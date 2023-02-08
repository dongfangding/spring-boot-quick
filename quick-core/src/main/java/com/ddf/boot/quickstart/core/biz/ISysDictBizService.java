package com.ddf.boot.quickstart.core.biz;


import com.ddf.boot.quickstart.api.dto.SysDictDTO;
import com.ddf.boot.quickstart.api.request.sys.SysDictGetByCodeRequest;
import java.util.List;

/**
 * <p>字典业务处理类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/15 22:16
 */
public interface ISysDictBizService {

    /**
     * 根据字典代码获取字典明细数据
     *
     * @param request
     * @return
     */
    List<SysDictDTO> getDictByCode(SysDictGetByCodeRequest request);
}
