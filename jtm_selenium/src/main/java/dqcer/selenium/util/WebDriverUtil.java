package dqcer.selenium.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 10:20
 * @Description: web driver 工具类
 */

public class WebDriverUtil {

    /**
     * 初始化加载谷歌浏览器所需的配置程序
     * http://chromedriver.storage.googleapis.com/index.html 谷歌浏览器driver
     *
     * @return
     */
    public static WebDriver initChromeDriver(){
        //  chromedriver.exe要与当前使用的谷歌浏览器版本一一对应，下载的地址可在淘宝或者GitHub，并将其解压放在与谷歌
        //  .exe 文件同级下
        File file = new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

        WebDriver webdriver = new ChromeDriver();
        webdriver.manage().window().setSize(new Dimension(1500, 800));
        return webdriver;
    }

    /**
     * 初始化火狐浏览器加载所需的配置程序
     *
     * @return
     */
    public static WebDriver initFirefox(){

        //  firefox.exe同样要与当前使用的火狐浏览器版本一一对应，下载的地址可在淘宝或者GitHub
        //  指定火狐浏览器程序的位置
        System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        //  指定firefox.exe插件的位置
        System.setProperty("webdriver.gecko.driver", "C://geckodriver.exe");
        return new FirefoxDriver();
    }

    /**
     * 初始化并打开浏览器
     *
     * @param httpUrl url
     * @return WebDriver
     */
    public static WebDriver getWebDriver(String httpUrl) {

        //  初始化配置
        WebDriver webDriver = WebDriverUtil.initChromeDriver();

        //  在打开地址前，清除cookies
        webDriver.manage().deleteAllCookies();

        //  同步浏览器
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //  打开目标地址，这个使用百度为例。只要是web系统都可以
        webDriver.get(httpUrl);

        return webDriver;
    }

    /**
     * 输入值事件
     *
     * @param webDriver
     * @param xpath
     * @param value
     */
    public static void sendKeys(WebDriver webDriver, String xpath, String value){
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        webElement.sendKeys(value);
    }

    /**
     * 点击事件
     *
     * @param webDriver
     * @param xpath
     */
    public static void click(WebDriver webDriver,  String xpath){
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        webElement.click();
    }
}
