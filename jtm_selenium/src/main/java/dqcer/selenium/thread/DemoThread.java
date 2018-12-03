package dqcer.selenium.thread;

import dqcer.selenium.util.CommonUtil;
import org.openqa.selenium.WebDriver;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 16:01
 * @Description: 测试示例
 */

public class DemoThread {

    private static final String DEMO = "demo.yml";

    /**
     * 启动示例
     *
     * @param webDriver
     */
    public void runMain(WebDriver webDriver){
        CommonUtil.runMain(webDriver, DEMO);
    }
}
