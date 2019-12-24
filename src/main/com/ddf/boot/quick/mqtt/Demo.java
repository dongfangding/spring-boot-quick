package com.ddf.boot.quick.mqtt;

import lombok.extern.slf4j.Slf4j;

/**
 * $
 * <p>
 * <p>
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ___/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * .............................................
 * 佛曰：bug泛滥，我已瘫痪！
 *
 * @author dongfang.ding
 * @date 2019/12/24 0024 17:29
 */
@Slf4j
public class Demo {

    public static void main(String[] args) throws InterruptedException {

        DefaultMqtt5Client defaultMqtt5Client = new DefaultMqtt5Client();

        defaultMqtt5Client.publish("/demo", "hello-world");

        Thread.sleep(10000);

        defaultMqtt5Client.publish("/demo", "延迟10秒，hello-world");

    }
}
