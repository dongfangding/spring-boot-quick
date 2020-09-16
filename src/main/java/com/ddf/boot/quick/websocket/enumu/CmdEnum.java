package com.ddf.boot.quick.websocket.enumu;

import java.io.Serializable;

/**
 * 下发指令的命令码和子命令码
 *
 * 针对一些业务复杂需要携带数据的指令，每个指令都是一个指令码即具备含义；
 *
 *
 * @author dongfang.ding
 * @date 2020/3/11 0011 15:59
 *
 */
public enum CmdEnum implements Serializable {
    //----------------------------------------------服务端指令
    /**
     * pong
     */
    PONG,

    //----------------------------------------------客户端指令

    /**
     * ping
     */
    PING,


    /**
     * 运行状态报备，客户端对服务端所需的应用状态进行监听，当状态变化时要向服务端报备最新状态
     */
    RUNNING_STATE,
    ;

}
