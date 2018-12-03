package dqcer.selenium.util;

import dqcer.selenium.model.AttrModel;
import dqcer.selenium.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.ho.yaml.Yaml;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 12:28
 * @Description: 公共调用
 */
@Slf4j
public class CommonUtil {
    private static final String USER_DIR = "user.dir";
    private static final String XPATH = "/jtm_selenium/src/main/resources/";
    private static final String SEND_KEYS = "sendKeys";
    private static final String CLICK = "click";
    private static final String PRESCCODE = "prescCode";
    private static final String REGISTERCODE = "registerCode";
    private static final Long MILLIS = 250L;

    /**
     * 运行的主方法
     *
     * @param webDriver 句柄
     * @param fileName 文件名称
     * @param hashMap 要传递的参数
     */
    public static void runMain(WebDriver webDriver, String fileName, LinkedHashMap hashMap){
        String path = System.getProperty(USER_DIR) + XPATH + fileName;
        File dumpFile=new File(path);
        if (!dumpFile.exists()){
            log.error("文件不存在 {}", path);
            return;
        }
        try {
            BaseModel entity = Yaml.loadType(dumpFile, BaseModel.class);
            for (Object object : entity.getLinkedList()) {
                AttrModel attrModel = (AttrModel) HashMapBeanTools.hashMapToJavaBean((HashMap<?, ?>) object, AttrModel.class);
                String methodName = attrModel.getMethod();
                String xpath = attrModel.getXpath();
                if (methodName == null || CLICK.equals(methodName)) {
                    WebDriverUtil.click(webDriver, xpath);
                } else if (SEND_KEYS.equals(methodName)) {
                    String value = attrModel.getValue();
                    WebDriverUtil.sendKeys(webDriver, xpath, value);
                } else if (methodName != null && PRESCCODE.equals(methodName)){
                    WebDriverUtil.sendKeys(webDriver, xpath, (String) hashMap.get(PRESCCODE));
                } else if (methodName != null && REGISTERCODE.equals(methodName)) {
                    WebDriverUtil.sendKeys(webDriver, xpath, (String) hashMap.get(REGISTERCODE));
                }
                Thread.sleep(MILLIS);
                Long millis = attrModel.getTime();
                if (null != millis && millis > 0) {
                    Thread.sleep(millis);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void runMain(WebDriver webDriver, String fileName) {
        runMain(webDriver,fileName,null);
    }
}
