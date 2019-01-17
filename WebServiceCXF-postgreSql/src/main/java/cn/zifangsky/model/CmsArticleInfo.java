package cn.zifangsky.model;

public class CmsArticleInfo {

	//記事ID
	private String article_id;
	//サイトID
	private String site_id;
	//CMSコンテンツID
	private String cms_content_id;
	//最大版
	private String max_version;
	//記事タイトル
	private String article_title;
	//記事本文
	private String article_body_plain;

	public String getArticle_id() {
		return article_id;
	}
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getCms_content_id() {
		return cms_content_id;
	}
	public void setCms_content_id(String cms_content_id) {
		this.cms_content_id = cms_content_id;
	}
	public String getMax_version() {
		return max_version;
	}
	public void setMax_version(String max_version) {
		this.max_version = max_version;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_body_plain() {
		return article_body_plain;
	}
	public void setArticle_body_plain(String article_body_plain) {
		this.article_body_plain = article_body_plain;
	}
}
