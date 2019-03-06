package test;

import jp.co.ultmarc.log.Logger;
import jp.co.ultmarc.web.WebManager;
import jp.co.ultmarc.web.controller.session.IllegalAttributeException;
import jp.co.ultmarc.web.controller.session.SessionTimeoutException;
import org.junit.Test;

public class CreateUserAgentTest {

    private static final Logger log = WebManager.getLogger();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CreateUserAgentTest.class.getName());
    /* ユーザーエジェント識別用の値 */
    public static String WINDOWS = "Windows";
    public static String MAC = "Mac";
    public static String LINUX = "Linux";
    public static String IOS = "iOS";
    public static String ANDROID = "Android";

    public static String IPHONE = "iPhone";
    public static String IPAD = "iPad";
    public static String IPOD = "iPod";
    public static String D2P_IOS = "D2P iOS";
    public static String D2P_ANDROID = "D2P Android";

    /* ブラウザ用の値 */
    public static String TRIDENT = "Trident";
    public static String OPR = "OPR";

    public static String IE = "IE";
    public static String EDGE = "Edge";
    public static String CHROME = "Chrome";
    public static String FIREFOX = "Firefox";
    public static String SAFARI = "Safari";
    public static String OPERA = "Opera";

    /* その他の値 */
    public static String OTHER = "Other";
    @Test
    public void tesConvertBirthDay() throws IllegalAttributeException, SessionTimeoutException{

       //Test Android
       String header = "Mozilla/5.0 (Linux; Android 4.4.2; XMP-6250 Build/HAWK) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Safari/537.36 ADAPI/2.0 (UUID:9e7df0ed-2a5c-4a19-bec7-2cc54800f99d) RK3188-ADAPI/1.2.84.533 (MODEL:XMP-6250)";
       String userAgent = createLoginUserAgent(header);
       logger.info(String.format("userAgent = %s \n",userAgent));
       //Test iOS
       header = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/35.0.1916.38 Mobile/10B350 Safari/8536.25";
       userAgent = createLoginUserAgent(header);
       logger.info(String.format("userAgent = %s \n",userAgent));
       //Test Windows
       header = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22 Swing/1.2.1.0";
       userAgent = createLoginUserAgent(header);
       logger.info(String.format("userAgent = %s \n",userAgent));
       //Test Mac
       header = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36";
       userAgent = createLoginUserAgent(header);
       logger.info(String.format("userAgent = %s \n",userAgent));
       //Test Linux
       header = "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/532.2 (KHTML, like Gecko) Chrome/4.0.223.3 Safari/532.2";
       userAgent = createLoginUserAgent(header);
       logger.info(String.format("userAgent = %s \n",userAgent));


    }

    /**
     * ユーザーエージェントからログイン端末識別値を成型する
     * @param userAgent
     * @return 成型したログイン端末識別値
     */
    public static String createLoginUserAgent(String userAgent) {

        String os = "";
        String browser = "";

        if(userAgent.indexOf(WINDOWS) != -1) {
            browser = browserDecision(userAgent);
            os = WINDOWS;
        }
        else if(userAgent.indexOf(D2P_IOS) != -1) {
            os = IOS;
        }
        else if(userAgent.indexOf(D2P_ANDROID) != -1) {
            os = ANDROID;
        }
        else if(userAgent.indexOf(ANDROID) != -1) {
            os = ANDROID;
        }
        else if(userAgent.indexOf(MAC) != -1) {
            if(userAgent.indexOf(IPHONE) != -1 || userAgent.indexOf(IPAD) != -1 || userAgent.indexOf(IPOD) != -1) {
                os = IOS;
            }
            else {
                browser = browserDecision(userAgent);
                os = MAC;
            }
        }
        else if(userAgent.indexOf(LINUX) != -1) {
            browser = browserDecision(userAgent);
            os = LINUX;
        }
        else {
            browser = browserDecision(userAgent);
            os = OTHER;
        }


        if(os == OTHER && browser == OTHER || os == ANDROID || os == IOS) {
            return os;
        }

        return os + " " + browser;
    }



    private static String browserDecision(String userAgent) {

        if(userAgent.indexOf(ANDROID) != -1) {
            if(userAgent.indexOf(WINDOWS) == -1) {
                return ANDROID;
            }
        }
        else {
            if(userAgent.indexOf(TRIDENT) != -1) {
                return IE;
            }
            else if(userAgent.indexOf(EDGE) != -1) {
                return EDGE;
            }
            else if(userAgent.indexOf(IPHONE) != -1 || userAgent.indexOf(IPAD) != -1 || userAgent.indexOf(IPAD) != -1) {
                if(userAgent.indexOf(WINDOWS) == -1) {
                    return IOS;
                }
            }
            else {
                if(userAgent.indexOf(FIREFOX) != -1) {
                    return FIREFOX;
                }
                else if(userAgent.indexOf(OPR) != -1) {
                    return OPERA;
                }
                else if(userAgent.indexOf(CHROME) != -1) {
                    return CHROME;
                }
                else if(userAgent.indexOf(SAFARI) != -1) {
                    if(userAgent.indexOf(MAC) != -1) {
                        return SAFARI;
                    }
                }
            }
        }
        return OTHER;
    }

    private static String isClient(String userAgent) {
        if (userAgent == null || userAgent.indexOf("windows nt") == -1 ? false : true) { // 判断当前客户端是否为PC
            return "pc";
        } else if (userAgent == null || userAgent.indexOf("android") == -1 ? false : true) { // 判断当前客户端是否为android
            return "android";
        } else if (userAgent == null || userAgent.indexOf("iphone") == -1 ? false : true) { // 判断当前客户端是否为iPhone
            return "iPhone";
        }
        return "";
    }

}
