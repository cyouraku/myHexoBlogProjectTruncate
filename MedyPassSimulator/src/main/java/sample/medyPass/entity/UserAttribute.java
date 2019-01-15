package sample.medyPass.entity;

import net.arnx.jsonic.JSONHint;

public class UserAttribute {
	/** ステータスコード */
	@JSONHint(name="status")
	public int status;
	/** ステータスコード */
	@JSONHint(name="md")
	public String messageDigest  = null;
	/** 応答データ */
	@JSONHint(name="responseData")
	public ResponseData responseData = null;

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMd(String md) {
		this.messageDigest = md;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public static class ResponseData {
		/** クレームドID */
		@JSONHint(name="claimedId")
		public String claimedId = null;
		/** コントラクトID */
		@JSONHint(name="contractId")
		public String contractId = null;
		/** 属性情報 */
		@JSONHint(name="attributes")
		public Attributes attributes = null;

		public void setClaimedId(String claimedId) {
			this.claimedId = claimedId;
		}
		public void setContractId(String contractId) {
			this.contractId = contractId;
		}
		public void setAttributes(Attributes attributes) {
			this.attributes = attributes;
		}
	}
}
