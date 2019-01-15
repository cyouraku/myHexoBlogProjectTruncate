package sample.medyPass.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import sample.medyPass.entity.Attributes;
import sample.medyPass.entity.CodeAndState;
import sample.medyPass.entity.SubAttributes;
import sample.medyPass.entity.TokenResponseDto;
import sample.medyPass.entity.UserAttribute;
import sample.medyPass.entity.UserAttribute.ResponseData;

public class MedyPassUtil {

//	private static Logger logger = Logger.getLogger(MedyPassUtil.class.getName());

	/**
	 * UserAttributeを取得する
	 * @return
	 */
	public static UserAttribute getUserAttribute(){
		UserAttribute user = new UserAttribute();
		user.setStatus(0);
		user.setMd("2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232");
		Attributes attr = new Attributes();
		attr.setBirthDate("1970-01-01T00:00:00+09:00");
		attr.setConfirmationLevel("0");
		attr.setDoctorLicenseRegistYear("5");
		attr.setFacilityNameAbbr("テスト病院 AAA");
		attr.setFacilityPrefecture("東京都");
		attr.setFamilyName("テスト");
		attr.setFamilyNameKana("テスト");
		attr.setGender("男性");
		attr.setGivenName("太郎");
		attr.setGivenNameKana("タロウ");
		attr.setJob("0");
		attr.setMdbFacilityCode("003630276");
		attr.setMailAddress("sample@sample.com");
		attr.setOldSchool("テスト大学　医学部");
		attr.setRawMailAddresses("sample@sample.com");
		attr.setUserName("testmedy001");
		attr.setWorkplace("テスト病院");
		ResponseData resp = new ResponseData();
		resp.setClaimedId("saku4cfcwuqq");
		resp.setContractId("24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e");
		resp.setAttributes(attr);
		user.setResponseData(resp);
		return user;
	}

	/**
	 * UserAttributeを取得する
	 * @return
	 */
	public static UserAttribute getUserAttribute2(){
		UserAttribute user = new UserAttribute();
		ResponseData resp = new ResponseData();
		user.setStatus(0);
		//return different md
		user.setMd("7cffb92335dae3b157cdf2c9393012daec8b19a6037f889e76d5efd8e7566eb9");
		Attributes attr = new Attributes();
		attr.setBirthDate("1970-01-01T00:00:00+09:00");
		attr.setConfirmationLevel("0");
		attr.setDoctorLicenseRegistYear("5");
		attr.setFacilityNameAbbr("テスト病院 AAA");
		attr.setFacilityPrefecture("東京都");
		attr.setFamilyName("テスト");
		attr.setFamilyNameKana("テスト");
		attr.setGender("男性");
		attr.setGivenName("太郎");
		attr.setGivenNameKana("タロウ");
		attr.setJob("0");
		attr.setMdbFacilityCode("003630276");
		attr.setMailAddress("sample@sample.com");
		attr.setOldSchool("テスト大学　医学部");
		attr.setRawMailAddresses("sample@sample.com");
		attr.setUserName("testmedy001");
		attr.setWorkplace("テスト病院");
		//return null claimedId
		resp.setClaimedId(null);//dao44cfcwpyl -> top
		resp.setContractId("24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e");
		resp.setAttributes(attr);
		user.setResponseData(resp);
		return user;
	}

	/**
	 * Attributesを取得する
	 * @return
	 */
	public static Attributes getAttributes(){
		Attributes attr = new Attributes();
		attr.setBirthDate("1970-01-01T00:00:00+09:00");
		attr.setConfirmationLevel("0");
		attr.setDoctorLicenseRegistYear("5");
		attr.setFacilityNameAbbr("テスト病院 AAA");
		attr.setFacilityPrefecture("東京都");
		attr.setFamilyName("テスト");
		attr.setFamilyNameKana("テスト");
		attr.setGender("男性");
		attr.setGivenName("太郎");
		attr.setGivenNameKana("タロウ");
		attr.setJob("0");
		attr.setMdbFacilityCode("003630276");
		attr.setMailAddress("sample@sample.com");
		attr.setOldSchool("テスト大学　医学部");
		attr.setRawMailAddresses("sample@sample.com");
		attr.setUserName("testmedy001");
		attr.setWorkplace("テスト病院");
		return attr;
	}

	/**
	 * CodeAndStatusを取得する
	 * @param code
	 * @param state
	 * @return
	 */
	public static CodeAndState getCodeAndState(String code, String state){
		CodeAndState cas = new CodeAndState(code,state);
//		logger.info(String.format("code=%s, state=%s \n",cas.getCode(),cas.getState()));
		return cas;
	}

	/**
	 * TokenResponseDtoを取得する
	 * @param id_token
	 * @param access_token
	 * @param token_type
	 * @param expires_in
	 * @param refresh_token
	 * @return
	 */
	public static TokenResponseDto getTokenResponseDto(
			String id_token,
			String access_token,
			String token_type,
			String expires_in,
			String refresh_token){
		TokenResponseDto trd = new TokenResponseDto(id_token,access_token,token_type,expires_in,refresh_token);
		return trd;
	}

	/**
	 * SubAttributesを取得する
	 * @return
	 */
	public static SubAttributes getSubAttributes(){
		SubAttributes attr = new SubAttributes();
		attr.setSub("6578349645");
		attr.setBirthDate("1970-01-01T00:00:00+09:00");
		attr.setConfirmationLevel("0");
		attr.setDoctorLicenseRegistYear("5");
		attr.setFacilityNameAbbr("テスト病院 AAA");
		attr.setFacilityPrefecture("東京都");
		attr.setFamilyName("テスト");
		attr.setFamilyNameKana("テスト");
		attr.setGender("男性");
		attr.setGivenName("太郎");
		attr.setGivenNameKana("タロウ");
		attr.setJob("0");
		attr.setMdbFacilityCode("003630276");
		attr.setMailAddress("sample@sample.com");
		attr.setOldSchool("テスト大学　医学部");
		attr.setRawMailAddresses("sample@sample.com");
		attr.setUserName("testmedy001");
		attr.setWorkplace("テスト病院");
		return attr;
	}

	/**
	 * ローカルファイルを読み込み
	 * @param filepath
	 * @return
	 */
	public static String readFile(String filepath) {
	        File file02 = new File(filepath);
	        FileInputStream is = null;
	        StringBuilder stringBuilder = null;
	        try {
	            if (file02.length() != 0) {
	                is = new FileInputStream(file02);
	                InputStreamReader streamReader = new InputStreamReader(is);
	                BufferedReader reader = new BufferedReader(streamReader);
	                String line;
	                stringBuilder = new StringBuilder();
	                while ((line = reader.readLine()) != null) {
	                    stringBuilder.append(line);
	                }
	                reader.close();
	                is.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return String.valueOf(stringBuilder);
	    }
}
