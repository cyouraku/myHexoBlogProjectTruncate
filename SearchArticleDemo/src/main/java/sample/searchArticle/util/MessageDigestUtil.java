package sample.searchArticle.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.logging.Logger;

public class MessageDigestUtil {

	private static final Logger logger = Logger.getLogger(MessageDigestUtil.class.getName());

	public static void main(String[] args) {

		try {
			testCreateLoginSessionId();
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * ログインセッションIDの生成
	 * @param userId
	 * @param sysDate
	 * @return 生成したログインセッションID
	 */
	public static String createLoginSessionId(long userId, Date sysDate) {
		String hashVal = null;
		hashVal = String.valueOf(sysDate.getTime()) + String.valueOf(userId);
		logger.info(String.format("sysDate  = %s",sysDate.toString()));
		logger.info(String.format("hashVal  = %s",hashVal));
		MessageDigest md = null;
	    StringBuilder sb = null;
	    try {
	        md = MessageDigest.getInstance("SHA-256");
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    md.update(hashVal.getBytes());
	    sb = new StringBuilder();
	    for (byte byt : md.digest()) {
	        String hex = String.format("%02x", byt);
//	        logger.info(String.format("full byt = %x",byt));
//	        logger.info(String.format("hex = %s",hex));
	        sb.append(hex);
	    }

	    return sb.toString();
	}

	public static void testCreateLoginSessionId() throws InterruptedException{
		String lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid = " + lsid);
		Thread.sleep(1);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 1 = " + lsid);
		Thread.sleep(10);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 10 = " + lsid);
		Thread.sleep(100);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 100 = " + lsid);
		Thread.sleep(1000);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 1000 = " + lsid);
		Thread.sleep(2000);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 2000 = " + lsid);
		Thread.sleep(5000);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 5000 = " + lsid);
		Thread.sleep(10000);
		lsid = createLoginSessionId(201, new Date(System.currentTimeMillis()));
		System.out.println("lsid 10000 = " + lsid);
	}

}
