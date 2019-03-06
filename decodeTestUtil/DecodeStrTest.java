package test;

import static org.junit.Assert.*;
import jp.co.ultmarc.D2PCommon;
import jp.co.ultmarc.log.Logger;
import jp.co.ultmarc.web.WebManager;
import jp.co.ultmarc.web.controller.session.IllegalAttributeException;
import jp.co.ultmarc.web.controller.session.SessionTimeoutException;
import org.junit.Test;

public class DecodeStrTest {

    private static final Logger log = WebManager.getLogger();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DecodeStrTest.class.getName());

    /** 生年月日暗号化文字列 */
    private String birtStr = "0qGaSGGxKNKJugZImddL6A==";
    /** 性別暗号化文字列-male */
    private String sexStrMale = "njO4u0rxjzm47CZDVwdazg==";
    /** 性別暗号化文字列-female */
    private String sexStrFemale = "TtJvDod4lPPOc0EGsCna/w==";


    @Test
    public void testDecodeStr() throws IllegalAttributeException, SessionTimeoutException{
       String str = decodeStr(birtStr);
       logger.info(String.format("%s --> %s \n",birtStr,str));
       assertEquals("19000101000000", str);

       String sexStr = sexStrMale;
       str = decodeStr(sexStr);
       logger.info(String.format("%s --> %s \n",sexStr,str));
       assertEquals("male", str);

       sexStr = sexStrFemale;
       str = decodeStr(sexStr);
       logger.info(String.format("%s --> %s \n",sexStr,str));
       assertEquals("female", str);

    }

    private String decodeStr(String input){
        return D2PCommon.decode(log, input);
    }

}
