package sample.medyPass.entity;

public class PrepareAuthentication {
	/** ステータスコード */
	public String status;
	/** 応答データ */
	public ResponseData responseData;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public static class ResponseData {
		/** アクセストークン */
		public String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}
}
