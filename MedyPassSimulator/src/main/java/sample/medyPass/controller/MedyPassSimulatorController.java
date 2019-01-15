/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.medyPass.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.medyPass.entity.CheckAlterData;
import sample.medyPass.entity.PrepareAuthentication;
import sample.medyPass.util.HttpClientUtil;
import sample.medyPass.util.MedyPassUtil;
import sample.medyPass.util.MyException;
import sample.medyPass.util.UserAttributeTransUtil;

@Controller
public class MedyPassSimulatorController {

	private Logger logger = Logger.getLogger(MedyPassSimulatorController.class.getName());
	private static final String DEFAULT_TOKEN  = "cde9c85aaf9e848541ffe79560952edb";
	private static final String SPACE = " ";
	@Value("${application.hostMedy:http://localhost:8088}")
	private String hostMedy;
//	@Value("${application.realm:http://192.168.56.1:8081}")
	@Value("${application.realm:http://localhost:8081}")
	private String realm;
//	@Value("${application.returnTo:http://192.168.56.1:8081/api/1/medy?rl=http://192.168.56.1:8081/}")
	@Value("${application.returnTo:http://localhost:8081/api/1/medy?rl=http://localhost/}")
	private String returnTo;
//	@Value("${application.r:http://192.168.56.1:8081}")
	@Value("${application.r:http://localhost:8081}")
	private String r;

	/**
	 * Tokenを取得するStub
	 * URL_PREPAREAUTHENTICATE=${HOST_MEDY}/_api/sharedRp/prepareAuthenticate?apiVersion=1.0&realm=%realm&returnTo=%returnURL&r=%backURL
	 * @param apiVersion
	 * @param realm
	 * @param returnTo
	 * @param backURL
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/_api/sharedRp/prepareAuthenticate")
	public @ResponseBody PrepareAuthentication prepareAuthenticate(
			@RequestParam("apiVersion") String apiVersion,
			@RequestParam("realm") String realm,
			@RequestParam("returnTo") String returnTo,
			@RequestParam("r") String backURL) throws IOException {
		logger.info(String.format("apiVersion = %s \n", apiVersion));
		logger.info(String.format("realm = %s \n", realm));
		logger.info(String.format("returnTo = %s \n", returnTo));
		logger.info(String.format("backURL = %s \n", backURL));
		PrepareAuthentication preAuth = new PrepareAuthentication();
		preAuth.setStatus("0");
		sample.medyPass.entity.PrepareAuthentication.ResponseData response = new sample.medyPass.entity.PrepareAuthentication.ResponseData();
		response.setToken(DEFAULT_TOKEN );
		preAuth.setResponseData(response);
		return preAuth;
	}

	/**
	 * 「認証＋属性」を取得するStub
	 * URL_AUTHENTICATE=${HOST_MEDY}/_api/sharedRp/authenticate?token=%s
	 * http://localhost:8081/_api/sharedRp/authenticate?token=cde9c85aaf9e848541ffe79560952edb
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/_api/sharedRp/authenticate")
	public String authenticate(@RequestParam("token") String token,Map<String, Object> model) throws IOException {
		logger.info(String.format("token = %s \n", token));
		String result = "";
//		String result = "authenticate";
		if(DEFAULT_TOKEN .equals(token)){
			//post form to d2pweb host
//			Map<String, String> param = UserAttributeTransUtil.transUserAttrToMap(this.getUserAttribute());
//			logger.info(String.format("Post data = %s \n",param.get("data")));
//			HttpClientUtil.executeStringByHttpPost(returnTo, param, HttpClientUtil.getCharsetOfUTF8());
			String htmlEncoder = UserAttributeTransUtil.parsePostFormFrJson(JSON.encode(MedyPassUtil.getUserAttribute()));
			model.put("message", htmlEncoder);
			logger.info(String.format("message = %s \n", model.get("message")));
			result = "alterAuthenticate";
//			logger.info(String.format("Post result = %s \n", result));
			//Test:return URLEncoder data
//			result = URLEncoder.encode(JSON.encode(this.getUserAttribute()), "UTF-8");
			//Test: return JSON encode data
//			result = JSON.encode(this.getUserAttribute());
			//Test:return UserAttribute
//			UserAttribute user = new UserAttribute();
//			user.setStatus("0");
//			user.setMessageDigest("2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232");
//			Attributes attr = this.getAttributes();
//			ResponseData data = new ResponseData();
//			data.setAttributes(attr);
//			data.setClaimedId("saku4cfcwuqq");
//			data.setContractId("24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e");
//			user.setResponseData(data);
//			return user;
			return result;
		}
		return result;
	}

	@GetMapping("/_api/sharedRp/authenticatePost")
	public void authenticate_post(@RequestParam("token") String token,Map<String, Object> model) throws IOException {
		logger.info(String.format("token = %s \n", token));
//		String result = "";
		if(DEFAULT_TOKEN .equals(token)){
			//post form to d2pweb host
			Map<String, String> param = UserAttributeTransUtil.transUserAttrToMap(MedyPassUtil.getUserAttribute());
			logger.info(String.format("Post data = %s \n",param.get("data")));
			HttpClientUtil.executeStringByHttpPost("http://localhost:8088/_api/sharedRp/authenticatePostTest", param, HttpClientUtil.getCharsetOfUTF8());
//			String htmlEncoder = UserAttributeTransUtil.parsePostFormFrJson(JSON.encode(MedyPassUtil.getUserAttribute()));
//			model.put("data", htmlEncoder);
//			logger.info(String.format("data = %s \n", model.get("data")));
//			result = "htmlEncoder";
		}
//		return result;
	}

	@RequestMapping(value="/_api/sharedRp/authenticatePostTest",
    method=RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public @ResponseBody String authenticate_postTest(@RequestBody MultiValueMap<String, String> data) throws IOException {
		String result = "authenticate_postTest completed!";
		logger.info(String.format("This is authenticate_postTest data received data= %s \n", data.get("data")));
		return result;
	}

	/**
	 * 改ざんチェック API
	 * URL_CHECKALTERDATA=${HOST_MEDY}/_api/sharedRp/checkFalsification?apiVersion=1.0&realm=%s&target=%s&md=%s
	 * @param apiVersion
	 * @param realm
	 * @param target
	 * @param md
	 * @return
	 */
	@GetMapping("/_api/sharedRp/checkFalsification")
	public @ResponseBody CheckAlterData checkAlterData(
			@RequestParam("apiVersion") String apiVersion,
			@RequestParam("realm") String realm,
			@RequestParam("target") String target,
			@RequestParam("md") String md) {
		logger.info(String.format("apiVersion = %s \n", apiVersion));
		logger.info(String.format("realm = %s \n", realm));
		logger.info(String.format("target = %s \n", target));
		logger.info(String.format("md = %s \n", md));
		CheckAlterData check = new CheckAlterData();
		check.setStatus("0");
		return check;
	}

	/**
	 * UserAttributeを取得する
	 * URL_GET_ATTRIBUTE=${HOST_MEDY}/_api/sharedRp/getAttributes?apiVersion=1.0&realm=%s&contractId=%s
	 * @param apiVersion
	 * @param realm
	 * @param contractId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 */
	@GetMapping("/_api/sharedRp/getAttributes")
	public @ResponseBody String getAttribute(
			@RequestParam("apiVersion") String apiVersion,
			@RequestParam("realm") String realm,
			@RequestParam("contractId") String contractId) throws UnsupportedEncodingException, JSONException {
		String result = "";
		logger.info(String.format("apiVersion = %s \n", apiVersion));
		logger.info(String.format("realm = %s \n", realm));
		logger.info(String.format("contractId = %s \n", contractId));
		//Use JSON decode only
		result = JSON.encode(MedyPassUtil.getUserAttribute2());
		//Use URLDecoder
//		result = URLEncoder.encode(JSON.encode(this.getUserAttribute2()),"UTF-8");
		return result;
	}

	/**
	 * UserAttributeを取得する
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 */
	@GetMapping("/_api/sharedRp/getAttributesWithoutParams")
	public @ResponseBody String getAttribute() {
		String result = "";
		//Use JSON decode only
		result = JSON.encode(MedyPassUtil.getUserAttribute());
		logger.info(String.format("result = %s \n", result));
		//Use URLDecoder
//		result = URLEncoder.encode(JSON.encode(this.getUserAttribute2()),"UTF-8");
		return result;
	}


	//medyパス(OpenID Connect) start
	/**
	 * 「認証リクエスト」API
	 * scope:”offline_access”（固定値）
	 * response_type:"code"(固定値)
	 * client_id:Medパスから事前に配布した固定値
	 * redirect_uri:(例)news2.medy-id.jp（固定値）
	 * state:ランダム値（OneTimeToken値）
	 * nonce:任意文字列
	 * @param scope
	 * @param responseType
	 * @param clientID
	 * @param redirectUri
	 * @param state
	 * @param nonce
	 * @param prompt
	 * @param max_age
	 * @param login_hint
	 * @param login_type
	 * @param openid2_realm
	 * @return
	 */
	@GetMapping("/opid/authorize")
	public @ResponseBody String getMedPassAuthorize(
			@RequestParam("scope") String scope,
			@RequestParam("response_type") String responseType,
			@RequestParam("client_id") String clientID,
			@RequestParam("redirect_uri") String redirectUri,
			@RequestParam("state") String state,
			@RequestParam("nonce") String nonce,
			@RequestParam("prompt") String prompt,
			@RequestParam("max_age") String max_age,
			@RequestParam("login_hint") String login_hint,
			@RequestParam("login_type") String login_type,
			@RequestParam("openid2_realm") String openid2_realm) {
		logger.info(String.format("response_type = %s \n", responseType));//code,固定値
		//client_id,固定値
		logger.info(String.format("state = %s \n", state));//same as one time token
		String result = "";
		if("openid2".equals(scope)){
			if("".equals(openid2_realm) || "null".equals(openid2_realm) || openid2_realm == null){
				//sBody = {"timestamp":1539161117321,"status":500,"error":"Internal Server Error","exception":"sample.medyPass.util.MyException","message":"openid2_reals is empty or null!","path":"/opid/authorize"}
				throw new MyException("openid2_reals is empty or null!");
			}
			result = JSON.encode(MedyPassUtil.getCodeAndState("code4openid2",state));
		}else{
			result = JSON.encode(MedyPassUtil.getCodeAndState("adfd2de3ddsaa",state));
		}
		logger.info(String.format("result = %s \n", result));
		return result;
	}

	/**
	 * 「トークンリクエスト」API
	 * @param grant_type
	 * @param redirect_uri
	 * @param refresh_token
	 * @return
	 */
	@GetMapping("/opid/token")
	public @ResponseBody String getMedPassToken(
			@RequestParam("grant_type") String grant_type,
			@RequestParam("code") String code,
			@RequestParam("redirect_uri") String redirect_uri,
			@RequestParam("refresh_token") String refresh_token) {
		//grant_type,code,redirect_uri,refresh_token
		String result = "";
		String id_token = "";
		if("code4openid2".equals(code)){
		    //TODO:Base64urlにてエンコード処理実装待ち
			id_token = "xxx.dao44cfcwpyl.xxx";
		}else{
			id_token = "xxx.xxx.xxx";
		}
		if("authorization_code".equals(grant_type)){
			//アクセストークンをリクエストする際に
			result = JSON.encode(MedyPassUtil.getTokenResponseDto(id_token, "access2342asdfe", "Bearer", "3600", null));
		}else if("refresh_token".equals(grant_type)){
			//リフレッシュトークンをリクエストする際に
			result = JSON.encode(MedyPassUtil.getTokenResponseDto(id_token, null, "Bearer", null, "refresh222312sfhtffg"));
		}
		//json response:id_token,access_token,token_type,expires_in,refresh_token
		logger.info(String.format("result = %s \n", result));
		return result;
	}

	/**
	 * 「属性取得リクエスト」API。
	 * @return
	 */
	@GetMapping("/opid/userInfo")
	public @ResponseBody String getMedPassUserInfo(
			@RequestHeader("Authorization") String header) {
		logger.info(String.format("Authorization = %s \n", header));
//		logger.info(String.format("header.split(SPACE)[1] = %s \n", header.split(" ")[1]));
		String result = "";
//		if("access2342asdfe".equals(header.split(SPACE)[1])){
//			result = JSON.encode(MedyPassUtil.getSubAttributes());
//			logger.info(String.format("result = %s \n", result));
//		}
		if("access2342asdfe".equals(header)){
			result = JSON.encode(MedyPassUtil.getSubAttributes());
			logger.info(String.format("result = %s \n", result));
		}
		return result;
	}
	//medyパス(OpenID Connect) end


}
