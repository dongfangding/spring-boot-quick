package com.ddf.boot.quick.event;

import com.ddf.boot.quick.model.dto.UserLoginHistoryDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>系统用户登录事件</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 14:14
 */
public class SysUserLoginEvent extends ApplicationEvent {

    /**
     * 登录数据
     */
    @Getter
    private final UserLoginHistoryDTO userLoginHistoryDTO;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with which the event is associated (never {@code
     *               null})
     */
    public SysUserLoginEvent(Object source, UserLoginHistoryDTO userLoginHistoryDTO) {
        super(source);
        this.userLoginHistoryDTO = userLoginHistoryDTO;
    }
}
