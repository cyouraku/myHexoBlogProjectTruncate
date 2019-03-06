package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jp.co.ultmarc.log.Logger;
import jp.co.ultmarc.util.CipherException;
import jp.co.ultmarc.util.CipherUtil;
import jp.co.ultmarc.util.StringUtil;
import jp.co.ultmarc.web.WebManager;
import jp.co.ultmarc.web.controller.session.Attribute;
import jp.co.ultmarc.web.controller.session.FileData;
import jp.co.ultmarc.web.controller.session.IllegalAttributeException;
import jp.co.ultmarc.web.controller.session.SessionTimeoutException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class DecodeSessionTest {

    private static final Logger log = WebManager.getLogger();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DecodeSessionTest.class.getName());
    /** セッション文字列の暗号化パスフレーズの初期値 */
    private final static String DEFAULT_CRYPT_PASSWORD = "Forestec2011";
    /** セッション文字列の暗号化パスフレーズ */
    private String _cryptPassword = DEFAULT_CRYPT_PASSWORD;
    //Session Sample 1
    private String session1 =  "WXxbrFyqK2RBpYQ/ugfXlrPxjnJ2BwiWApH5ggxg6x09t0ak3ZwQ3d0UCJ110k2Qp14Qdp4AYKqTGTdEdErxC7/0Zd3SooHwj+KNp+dFYovot+Mu20055fZyZVry5XEyXbS/kO//5nFZtYe2MSjVhfsRVDvp/FlYurpNw/MXF769iFtKR11X8FR2jg/ErUKxnSJbpdmeLOTJszuJ6UeYzE6y6pxN8OxhVa+kNZzLNw7rCNfAlH5926Fj6CTiSwTlTW6DDL+/Z36O38p/5c9OI17qtv9NcthzvaqeXC+eCo7feEZwYj9wF7j4DRi2k17LafhQijkebNeN5atPjKtdc8FOKDJsY7XzJQAfmLo3Zg660e7bd5BHRLGadRKCXfXgbP7DnXpNY8rVuIso15Grl9t3L7mqqs8CzHHpo6snjlE=";
    //Session Sample 2,localhost
    private String session2 =  "Xlc6TseVf1ee1cPABKHf/I+kkDq9P7of403+JVVudes+dfs/jUDMvK5ROFbx2M67s4Kgyh5obhdYQsf/PhQUnGPWH3U8crsMjTWAs+IpsekEYmaeAE3Gyxi8kX+mOd0y+uHPP359FfQB4X1cG+ibmx3xKILOpQSSHqk5njkS3H+791C51TTzpuBVW+n68chT2S57/OLJUNVha8lY4bX0M7TO/5gxIIiM1EQ6H4xraaCT6sEqpODKBC2iPSIoVEEONJWqa7PCqTEDq/oSbNipsF3eerDuLP30G7iO3Tpva4lYnFP5CBhlwJ37+5xIYMTZAXwYqqVYUbfyof6Rqw8bgeWs+LxLkS224whPDIZvFIwzJrifGUYu7DQ9KL+Bk8cEc5FOX6Omi8WJyrkhy0texQUAbczV6wzdWkGLfH5O5J5IwQRqGYLEBx8FVXm+xOZt";
    //Session Sample 3,Honban Zhangle:  user_id = 92236
    private String session3 = "WXxbrFyqK2RBpYQ/ugfXliZJX0tOcBgI+G/zaVf4du0ycrMCd2oLx83Da0s1/0SO4oTBU3EWoLNq3xfftfTIjVf50S//7TQSi9sKRTNpifugAtnNNX06fba6/6h2CEt4yHbg6i2waSEkOZf/ZKs/Bd1Z3MCcTk7rymbFZrOjUeYdV+tPJ7O8o7B7wdfBywpts1A/ZlE3F6TiKuFuEiFS3ctuBGu3aXI6EsrayETx8+sG0eX64b0QPgyRuQyvizKKKIEZrH0Zk6dhRffh/STlYpBEBY5h2duCtf70C5pgmm6wqb3BmZYfDBvaQ9wJ49dgQjRw5JQVOtmgfZJ3dCEUlme29zpxDjVGzy+m8HZCpMTmQbse6o92ut7wrvYARcUV+MZ6YTrUxMJZsWimYLXG89EXwj5y4W1rIct0sVS5xDkmmEcDwE+X5Bc8blUfwQHx8mlskeCxagSKbCKXUaWgbQ==";
    //Session Sample 4,nakayama
    private String session4 = "PUlKnOjcIc7dGAAbODw3pdOWucBLRTua4nt6bXlRNo+/emcpo9FyU5PHkXRl5ssK4a+otDafyDpasHJg9MBCZ3B5zPqdkSjdErgjCehzDCNcH+yTKTi2H9cYAyN0SzefP79TQOZEBwuGq7zL/hICkWPVJ/h1dJxfH4s9MLRiJkUxk7XWY9ZuSAxE+M3rvEA6TbBMnMTdOTTup2dXDwhBiqWlYGiQfIynZCvYBn6pN+kSb17ynO3fP2oGMrHA294oPMA1bqCyxaVGVdneJSQl2qmd/8MlDjiSx8/ohDR5rROMnMgTGO2zhjIddPBnS8iHi//LQd7x7/SpySiGDwe+AcoImePOzTgcqD/JUzZa3TUCrKue224qfIp8R6CCzAxHw1DPLNCZaXMFt9aLa+j4JC3xQ7flDHRCsW2cfstGjUw=";
    //Session Sample 5
    private String session5 = "JEJJOHqaR4jglCY7wVnAT5AXSVtr8qXzGZtM08ZORLB6CwqaFuaya1YLEMCMlD6yyceA8EA+OGodaALBXhG4GYgPluBEoONUjM0O1WVmioPCw/k5M36scgScPktKsSvJxbB5GjuFnNGe7+zNql3ClLtuOGLb5bIEFFqlMzhy41tBlAO7fOsbQe7DmjKIpBCw+ojd+3yEj2Ng8T7/P0DAsNE+kj0rsWNlF5r20d3zrrw=";
    //Session Sample 6
    private String session6 = "JEJJOHqaR4jglCY7wVnATwgdOh6gYfrJN2hMHNHhaljQqO3eOCCt5tRMgylk3pCVFTeoWDRavb7zPFXCHP5H7etbMB5KEI2dw4xMfwn2kLx34ZGP9hoUAhq0t9QsAl+K5kwx2J8yHKms9/+a2CAjDm4RJxqt5VBbnXYoQrlHeGHjsP9YkuImvpjIUSO5YH+QT7VYIi/+TmgTh4y2z0nPHFOWTXZc11UgVy1byOKG6M8=";
    //Session Sample 7,d2pstage
    private String session7 = "TgTfOmzkQugdf6MmI2YIB3y7vMOzkoKmhzh39/Bi9kliTt5XEhtarvc1DzgsrhcVms2jZl0kytR8hKG19XTzAANLHYMPyxtqPBOVKjIEuc+WK4Up5zctK5fpZDzfGqQOmIJGqJn6bs8W78/JX5AzeXofFw1m5NhD93d6AyzXGAOLhxlQa5ZWRalmBUEj/X7uM5JRU4Iuo1daNc3RBF1BmhwP/YITDiWGQlGfat+LKpWCUlgdALl/W6vOlhso/0FHSvFk+c1Pjr44lg8J9cHwq79eU2aJbGT9pINPWAT5Qoq3Mc2PoebHflvD73qOptFuyztDYzPSXL4c0TO3tObqxq3qxHwr/1YRxHBu8tFb0fRH2s2wOiMZk7F34J83hJGK5KGKr6P+AP7X3VvfKqFnW+WlO/AdEMQh4tPhcmVvajh0tk7rIpic8WdwWtUDk45w";
    //Session Sample 8,d2padmin,MLS
    private String session8 = "8J5vSSkk6PBY0LQ4Ifie+E5A7GASrl4ylt+f88RC2fHJBLUIrp6Keu/kGsi6R1DsTI3E7GxMj2E0zJhXkXSRGKAmDN+LeBkc0eo3h3rM0GrqrPv8bgDjZ8I8DKKPUcsrXjfPLhg0tPakroqPn8/7QBjhpqoXr7qVvZT0BRjtctWYVs76vWZzvlwZDxfMzVyk1MtpsYKZVVSRf7RTkZ/kdAfVMd/GCkCQ+eOZ0ZMvN4g82BdbfVzy0SOq0sa7pgbmwZfeJj8yh5kzt2upXjflnsGTZTWZPd4XbHtAxLDtOE3NaN8GQhc9wrk52+eVYcqO";


    @Test
    public void tesConvertBirthDay() throws IllegalAttributeException, SessionTimeoutException{

       String input = session6;
       String str = decodeSessionStr(input);
       logger.info(String.format("uls = %s --> decode = %s \n",input,str));
       Map<String, String> cookies = decodeSession(input);
       logger.info(String.format("ID = %s \n",cookies.get("ID")));
       logger.info(String.format("CERT = %s \n",cookies.get("CERT")));
       logger.info(String.format("ATTR = %s \n",cookies.get("ATTR")));
       logger.info(String.format("SID = %s \n",cookies.get("SID")));
       logger.info(String.format("LSID = %s \n",cookies.get("LSID")));

       input = session8;
       str = decodeSessionStr(input);
       cookies.clear();
       logger.info(String.format("mls = %s --> decode = %s \n",input,str));
       cookies = decodeSession(input);
       logger.info(String.format("ID = %s \n",cookies.get("ID")));
       logger.info(String.format("CERT = %s \n",cookies.get("CERT")));
       logger.info(String.format("ATTR = %s \n",cookies.get("ATTR")));
       logger.info(String.format("SID = %s \n",cookies.get("SID")));
       logger.info(String.format("LSID = %s \n",cookies.get("LSID")));

       //Create ULS
       String uls = createSessionStr();
       str = decodeSessionStr(uls);
       logger.info(String.format("uls = %s \n --> decode = %s \n",uls,str));
//       assertEquals("WXxbrFyqK2RBpYQ/ugfXlrPxjnJ2BwiWApH5ggxg6x09t0ak3ZwQ3d0UCJ110k2Qp14Qdp4AYKqTGTdEdErxC7/0Zd3SooHwj+KNp+dFYovot+Mu20055fZyZVry5XEyXbS/kO//5nFZtYe2MSjVhfsRVDvp/FlYurpNw/MXF769iFtKR11X8FR2jg/ErUKxnSJbpdmeLOTJszuJ6UeYzE6y6pxN8OxhVa+kNZzLNw7rCNfAlH5926Fj6CTiSwTlTW6DDL+/Z36O38p/5c9OI17qtv9NcthzvaqeXC+eCo7feEZwYj9wF7j4DRi2k17LafhQijkebNeN5atPjKtdc8FOKDJsY7XzJQAfmLo3Zg660e7bd5BHRLGadRKCXfXgbP7DnXpNY8rVuIso15Grl9t3L7mqqs8CzHHpo6snjlE=",uls);

    }

    private String decodeSessionStr(String input){
       return decode(input);
    }

    private String createSessionStr() throws IllegalAttributeException{
        //Set user attribute
        HashMap<String, Attribute> _attribute = new HashMap<String, Attribute>();
        _attribute.put("currentSubTabId", new Attribute("currentSubTabId", "310"));
        _attribute.put("SCREEN_ID", new Attribute("SCREEN_ID", "SC-USR-101-001"));
        _attribute.put("attributeUpdateTime", new Attribute("attributeUpdateTime", 1539927622039L));
        _attribute.put("optinClip", new Attribute("optinClip", "0"));
        _attribute.put("currentTabId", new Attribute("currentTabId", "100"));
        _attribute.put("ID", new Attribute("ID", "100000200"));
        _attribute.put("CERT", new Attribute("CERT", 0));
//        _attribute.put("ATTR", new Attribute("ATTR", "null"));
//        _attribute.put("SID", new Attribute("SID", "null"));
        _attribute.put("LSID", new Attribute("LSID", "9ad9f5c5cba285cd4673d9920c5d65338d7cf1f70a6741d68789c53a3dd2bd96"));
        return toSessionString(_attribute);
    }

    public static String decode(String value) {
        byte[] bDecode = null;
        String decode = null;
        if (value != null) {
            try {
                bDecode = CipherUtil.decryptAESAndBase64(value,
                        "session_crypt_password");//session_crypt_password //Forestec2011
                decode = new String(bDecode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                //
            } catch (CipherException e) {
                //
            }
        } else {
            decode = null;
        }
        return decode;
    }


    /**
     * セッションXMLにノード(属性データ)を追加する。
     * @param doc Documentインスタンス
     * @param attrib 属性データ
     * @return ノード情報
     */
    private Node insertNode(Document doc, Attribute attrib) {
        Element element = null;
        FileData fileData = null;
        Text text;

        try {
            element = doc.createElement(attrib.getKey());
            element.setAttribute("type", String.valueOf(attrib.getType()));

            switch (attrib.getType()) {
            case Attribute.TYPE_STRING:         /* 属性データ種別(文字列) */
                text = doc.createTextNode(StringUtil.htmlEncode(attrib.getString()));
                element.appendChild(text);
                break;
            case Attribute.TYPE_INTEGER:        /* 属性データ種別(数値) */
                text = doc.createTextNode(String.valueOf(attrib.getInteger()));
                element.appendChild(text);
                break;
            case Attribute.TYPE_LONG:       /* 属性データ種別(数値) */
                text = doc.createTextNode(String.valueOf(attrib.getLong()));
                element.appendChild(text);
                break;
            case Attribute.TYPE_BYTE_ARRAY:     /* 属性データ種別(バイト配列) */
                text = doc.createTextNode(attrib.getBase64Bytes());
                element.appendChild(text);
                break;
            case Attribute.TYPE_FILE:           /* 属性データ種別(ファイル) */
                fileData = attrib.getFileData();
                element.setAttribute("name", fileData.getName());
                element.setAttribute("size", String.valueOf(fileData.getLength()));
                text = doc.createTextNode(fileData.getBase64Content());
                element.appendChild(text);
                break;
            }
        } catch (IllegalAttributeException iae) {
            element = null;
        } catch (DOMException de) {
            element = null;
        }
        return element;
    }

    /**
     * セッション文字列を取得する。
     *
     * @return セッション文字列
     * @throws IllegalAttributeException 属性データがバイト配列またはFileDataの場合にスローする
     */
    public String toSessionString(HashMap<String, Attribute> _attribute) throws IllegalAttributeException {
        DocumentBuilderFactory dbfactory = null;
        DocumentBuilder docbuilder = null;
        Document doc = null;
        Iterator<Entry<String, Attribute>> iterator = null;
        Entry<String, Attribute> entry = null;
        Attribute attrib = null;
        Node root, element, paramRoot, ctrlRoot;
        TransformerFactory tfactory = null;
        Transformer transformer = null;
        ByteArrayOutputStream baos = null;
        GZIPOutputStream zos = null;
        StreamResult result = null;
        String session = null;
        Text text;

        Exception e = null;
        try {
            dbfactory = DocumentBuilderFactory.newInstance();
            dbfactory.setValidating(true);
            dbfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            dbfactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbfactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbfactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            docbuilder = dbfactory.newDocumentBuilder();

            doc = docbuilder.newDocument();

            root = doc.createElement("Session");
            doc.appendChild(root);

            /* セッション制御データ */
            ctrlRoot = doc.createElement("Control");
            root.appendChild(ctrlRoot);

            element = doc.createElement("Date");
            text = doc.createTextNode(StringUtil.dateParseString(new java.util.Date(), "yyyyMMddHHmmss"));
            element.appendChild(text);
            ctrlRoot.appendChild(element);

            /* セッションパラメータ */
            paramRoot = doc.createElement("Parameter");
            root.appendChild(paramRoot);

            iterator = _attribute.entrySet().iterator();
            while (iterator.hasNext()) {
                entry = iterator.next();

                attrib = entry.getValue();
                element = insertNode(doc, attrib);
                if (element != null) {
                    paramRoot.appendChild(element);
                }
            }

            baos = new ByteArrayOutputStream();
            zos = new GZIPOutputStream(baos);

            result = new StreamResult(zos);

            tfactory = TransformerFactory.newInstance();
            transformer = tfactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(doc), result);

            zos.finish();
            /*
            session = java.net.URLEncoder.encode(CryptUtil.encryptAESAndBase64(baos.toByteArray(), _cryptPassword), "iso-8859-1");
             */
            session = CipherUtil.encryptAESAndBase64(baos.toByteArray(), _cryptPassword);
        } catch (ParserConfigurationException pce) {
            e = pce;
        } catch (TransformerConfigurationException tfce) {
            e = tfce;
        } catch (TransformerException te) {
            e = te;
        } catch (IOException ioe) {
            e = ioe;
        } catch (CipherException ce) {
            e = ce;
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException ioe) {
            }
            try {
                if (zos != null)
                    zos.close();
            } catch (IOException ioe) {
            }
        }
        if (e != null) {
            log.error(e, "Session#toSessionString(): セッション文字列の生成に失敗しました。");
            throw new IllegalAttributeException("セッション文字列の生成に失敗しました。");
        }
        return session;
    }

    public Map<String, String> decodeSession(String sSession) throws IllegalAttributeException, SessionTimeoutException {
        Map<String, String> cookies = new HashMap<String, String>();
        ByteArrayInputStream bais = null;
        ByteArrayInputStream baisXML = null;
        ByteArrayOutputStream baos = null;
        GZIPInputStream zin = null;
        byte[] recvBuffer = null;
        int retSize = 0;
        DocumentBuilderFactory dbfactory = null;
        DocumentBuilder docbuilder = null;
        Document doc = null;
        Element root, element;
        NodeList nodeList = null;
        NodeList nodeParamList = null;
        NodeList nodeCtrlList = null;
        int i, ic;
        String sKey;
        FileData fileData = null;
        byte[] bDecode = null;

        Exception e = null;
        try {
            bDecode = CipherUtil.decryptAESAndBase64(sSession, "session_crypt_password");
            if (bDecode == null) {
                return null;
            }
            bais = new ByteArrayInputStream(bDecode);
            /*
            bais = new ByteArrayInputStream(CryptUtil.decryptAESAndBase64(java.net.URLDecoder.decode(sSession, "iso-8859-1"), _cryptPassword));
             */
            baos = new ByteArrayOutputStream();
            zin = new GZIPInputStream(bais);
            recvBuffer = new byte[10240];

            while ((retSize = zin.read(recvBuffer)) != -1) {
                baos.write(recvBuffer, 0, retSize);
            }
            if (baos.size() == 0) {
                throw new IllegalAttributeException("セッション文字列の復号化に失敗しました。");
            }
            baisXML = new ByteArrayInputStream(baos.toByteArray());

            dbfactory = DocumentBuilderFactory.newInstance();
            dbfactory.setValidating(true);
            dbfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            dbfactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbfactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbfactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            docbuilder = dbfactory.newDocumentBuilder();
            doc = docbuilder.parse(baisXML);

            root = (Element)doc.getFirstChild();
            nodeList = root.getChildNodes();
            for (i = 0; i < nodeList.getLength(); i++) {
                element = (Element)nodeList.item(i);
                if (element.getNodeType() == Node.ELEMENT_NODE) {
                    sKey = element.getTagName();
                    if (sKey.equals("Parameter")) {
                        nodeParamList = element.getChildNodes();
                        for (ic = 0; ic < nodeParamList.getLength(); ic++) {
                            element = (Element)nodeParamList.item(ic);
                            if (element.getNodeType() == Node.ELEMENT_NODE) {
                                sKey = element.getTagName();

                                switch (StringUtil.parseInt(element.getAttribute("type"), -1)) {
                                case Attribute.TYPE_STRING:         /* 属性データ種別(文字列) */
                                    if(element.getTextContent() != null){
                                        logger.info(String.format("type = %s ; skey = %s ; text = %s! \n","string",sKey,element.getTextContent()));
                                        cookies.put(sKey, element.getTextContent());
                                    }else{
                                        logger.info(String.format("skey = %s is null! \n",sKey));
                                    }
//                                  _attribute.put(sKey, new Attribute(sKey, StringUtil.htmlDecode(element.getTextContent())));
                                    break;
                                case Attribute.TYPE_INTEGER:        /* 属性データ種別(数値) */
                                    if(element.getTextContent() != null){
                                        logger.info(String.format("type = %s ; skey = %s ; text = %s! \n","int",sKey,element.getTextContent()));
                                        cookies.put(sKey, element.getTextContent());
                                    }else{
                                        logger.info(String.format("skey = %s is null! \n",sKey));
                                    }
//                                    _attribute.put(sKey, new Attribute(sKey, StringUtil.parseInt(element.getTextContent(), -1)));
                                    break;
                                case Attribute.TYPE_LONG:           /* 属性データ種別(数値) */
                                    if(element.getTextContent() != null){
                                        logger.info(String.format("type = %s ; skey = %s ; text = %s! \n","long",sKey,element.getTextContent()));
                                        cookies.put(sKey, element.getTextContent());
                                    }else{
                                        logger.info(String.format("skey = %s is null! \n",sKey));
                                    }
//                                    _attribute.put(sKey, new Attribute(sKey, StringUtil.parseLong(element.getTextContent(), -1)));
                                    break;
                                case Attribute.TYPE_BYTE_ARRAY:     /* 属性データ種別(バイト配列) */
                                    if(element.getTextContent() != null){
                                        logger.info(String.format("type = %s ; skey = %s ; text = %s! \n","byte_array",sKey,element.getTextContent()));
                                        cookies.put(sKey, element.getTextContent());
                                    }else{
                                        logger.info(String.format("skey = %s is null! \n",sKey));
                                    }
//                                    _attribute.put(sKey, new Attribute(sKey, Base64.decodeBase64(element.getTextContent().getBytes())));
                                    break;
                                case Attribute.TYPE_FILE:           /* 属性データ種別(ファイル) */
                                    fileData = new FileData(element.getAttribute("name"),
                                            Base64.decodeBase64(element.getTextContent().getBytes()));
                                    if(element.getTextContent() != null){
                                        logger.info(String.format("type = %s ; skey = %s ; text = %s! \n","file",sKey,element.getTextContent()));
                                        cookies.put(sKey, element.getTextContent());
                                    }else{
                                        logger.info(String.format("skey = %s is null! \n",sKey));
                                    }
//                                    _attribute.put(sKey, new Attribute(sKey, fileData));
                                    break;
                                default:
                                    throw new IllegalAttributeException("セッション文字列に不正な属性種別が含まれます。");
                                }
                            }
                        }
                    } else if (sKey.equals("Control")) {
                        nodeCtrlList = element.getChildNodes();
                        for (ic = 0; ic < nodeCtrlList.getLength(); ic++) {
                            element = (Element)nodeCtrlList.item(ic);
                            if (element.getNodeType() == Node.ELEMENT_NODE) {
                                sKey = element.getTagName();
                                if (sKey.equals("Date")) {
                                    Date _sessionDate  = StringUtil.parseSQLDate("yyyyMMddHHmmss", element.getTextContent());
                                    logger.info(" " + _sessionDate);
                                    if (_sessionDate == null) {
                                        throw new IllegalAttributeException("セッション文字列に不正な制御情報が含まれます。");
                                    }
                                    cookies.put(sKey, _sessionDate.toString());
//                                    java.util.Date curDate = new java.util.Date();
//                                    if (_sessionTimeout >= 0) {
//                                        if ((_sessionDate.getTime() + _sessionTimeout) < curDate.getTime()) {
//                                            throw new SessionTimeoutException("[" + element.getTextContent() + "]セッション・タイムアウトが発生しました。");
//                                        }
//                                    }
                                } else {
                                    throw new IllegalAttributeException("セッション文字列に不正な制御情報が含まれます。");
                                }
                            }
                        }
                    } else {
                        throw new IllegalAttributeException("セッション文字列に不正な情報が含まれます。");
                    }
                }
            }
        } catch (CipherException de) {
            e = de;
        } catch (ParserConfigurationException pce) {
            e = pce;
        } catch (SAXException se) {
            e = se;
        } catch (UnsupportedEncodingException uee) {
            e = uee;
        } catch (EOFException ee) {
            e = ee;
        } catch (IOException  ioe) {
            e = ioe;
        } finally {
            try {
                if (bais != null)
                    bais.close();
            } catch (IOException ioe) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException ioe) {
            }
            try {
                if (zin != null)
                    zin.close();
            } catch (IOException ioe) {
            }
            try {
                if (baisXML != null)
                    baisXML.close();
            } catch (IOException ioe) {
            }
        }
        if (e != null) {
            throw new IllegalAttributeException(e);
        }

        return cookies;
    }

}
