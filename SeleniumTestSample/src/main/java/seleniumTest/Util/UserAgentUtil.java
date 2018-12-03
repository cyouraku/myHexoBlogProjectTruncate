package seleniumTest.Util;

public class UserAgentUtil {
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
            browser = browserDecision(userAgent);
            os = IOS;
        }
        else if(userAgent.indexOf(D2P_ANDROID) != -1) {
            browser = browserDecision(userAgent);
            os = ANDROID;
        }
        else if(userAgent.indexOf(ANDROID) != -1) {
            browser = browserDecision(userAgent);
            os = ANDROID;
        }
        else if(userAgent.indexOf(MAC) != -1) {
            if(userAgent.indexOf(IPHONE) != -1 || userAgent.indexOf(IPAD) != -1 || userAgent.indexOf(IPOD) != -1) {
                browser = browserDecision(userAgent);
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



    public static String browserDecision(String userAgent) {

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

    public static String isClient(String userAgent) {
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
