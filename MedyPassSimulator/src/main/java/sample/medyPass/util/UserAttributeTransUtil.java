package sample.medyPass.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.jboss.logging.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sample.medyPass.entity.UserAttribute;

public class UserAttributeTransUtil {

	private static Logger logger = Logger.getLogger(UserAttributeTransUtil.class.getName());

//	{"status":"0","md":"2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232","responseData":{"claimedId":"saku4cfcwuqq","contractId":"24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e","attributes":{"userName":"testmedy001","familyName":"テスト","givenName":"太郎","familyNameKana":"テスト","givenNameKana":"タロウ","workplace":"テスト病院","workingDepartment":null,"mdbFacilityCode":"003630276","oldSchool":"テスト大学　医学部","mdbPersonalCode":null,"mdbService":null,"mdbServiceCategory":null,"doctorLicenseRegistYear":"5","birthDate":"1970-01-01T00:00:00+09:00","gender":"男性","job":"0","mailAddress":"sample@sample.com","rawMailAddresses":"sample@sample.com","confirmationLevel":"0","facilityFullName":null,"facilityNameAbbr":"テスト病院 AAA","facilityPrefecture":"東京都","facilityPrefectureCode":null,"facilityMunicipalityCode":null}},"messageDigest":"2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232"}

	public static Map<String, String> transUserAttrToMap(UserAttribute user) throws UnsupportedEncodingException, JSONException{
		Map<String, String> param = new HashMap<String, String>();
		String data = "";
		//Use URLEncoder
//		data = URLEncoder.encode(JSON.encode(user), "UTF-8");
		//Use JSON.encode only
		data = JSON.encode(user);
		param.put("data", data);
		logger.info(String.format("Classname = %s;Data = %s \n", UserAttributeTransUtil.class.getName(), data));
		return param;
	}

	public static MultiValueMap<String, String> transUserAttrToMultiValueMap(UserAttribute user) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		String data = JSON.encode(user);
		map.add("data",data);
		return map;
	}

	/**
	 * parse "\"" to HTML code "&quot"
	 * @param json
	 * @return
	 */
	public static String parsePostFormFrJson(String json){
		String result = "";
		result = json.replaceAll("\"", "&quot;");
		return result;
	}
}
