package com.ddf.boot.quickstart.core.event;

import com.ddf.boot.quickstart.api.event.UserLoginEventPayload;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/18 13:38
 */
public class SysUserLoginEvent extends ApplicationEvent {

    @Getter
    private final UserLoginEventPayload payload;

    public SysUserLoginEvent(Object source, UserLoginEventPayload payload) {
        super(source);
        this.payload = payload;
    }
}
