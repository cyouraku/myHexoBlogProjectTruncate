package sample.searchArticle.entity;

import java.util.List;

import net.arnx.jsonic.JSONHint;

public class SearchKeywordResultDto {

	@JSONHint(name="responseHeader")
	public ResponseHeader responseHeader = null;
	@JSONHint(name="response")
	public ResponseBody response = null;


	public static class ResponseHeader {
		@JSONHint(name="status")
		public String status = null;
		@JSONHint(name="QTime")
		public String qtime = null;
		@JSONHint(name="params")
		public Params params = null;
	}

	public static class Params {
		@JSONHint(name="q")
		public String query = null;
		@JSONHint(name="df")
		public String df = null;
		@JSONHint(name="fl")
		public String fl = null;
		@JSONHint(name="fq")
		public String fq = null;

	}

	public static class ResponseBody {
		@JSONHint(name="numFound")
		public String numFound = null;
		@JSONHint(name="start")
		public String start = null;
		@JSONHint(name="docs")
		public List<ResponseData> docs = null;
	}

	public static class ResponseData {
		@JSONHint(name = "articleId")
		public String articleId = null;
//		@JSONHint(name = "title")
//		public String title = null;
//		@JSONHint(name = "summary")
//		public String summary = null;
//		@JSONHint(name = "keyword")
//		public String keyword = null;
//		@JSONHint(name = "url")
//		public String url = null;
	}
}
