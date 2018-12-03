package dqcer.selenium.thread;


import dqcer.selenium.util.WebDriverUtil;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 11:51
 * @Description: 初始化相关配置信息
 */
public class InitConfig implements Callable<WebDriver> {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    private CyclicBarrier cyclicBarrier;

    private CountDownLatch downLatch;

    public InitConfig(CyclicBarrier cyclicBarrier, CountDownLatch downLatch) {
        this.cyclicBarrier = cyclicBarrier;
        this.downLatch = downLatch;
    }

    @Override
    public WebDriver call() throws Exception {
        String httpUrl = "http://www.baidu.com";

        WebDriver webDriver = WebDriverUtil.getWebDriver(httpUrl);

        cyclicBarrier.await();

        //  等价于下面代码
        new DemoThread().runMain(webDriver);

        /*//  搜索spring boot
        //  定位当前的输入框
        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"kw\"]"));

        //  在输入框输入"spring boot"
        element.sendKeys("spring boot");

        //  定位当前的"百度一下"按钮所在的位置
        WebElement submint = webDriver.findElement(By.xpath("//*[@id=\"su\"]"));

        //  点击提交
        submint.click();

        //  休息3秒，等待搜索结果并查看
        Thread.sleep(3000);*/

        //  最后退出，关闭浏览器
       webDriver.quit();

       downLatch.countDown();
       atomicInteger.incrementAndGet();
        return null;
    }




}
