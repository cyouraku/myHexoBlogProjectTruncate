package sample.searchArticle.entity;

import java.util.List;

public class ChannelListResponseDTO {

	//"result":"0000", "channels":[{"tab_id":0,
	//"tab_channels":
	//[{"channel_id":12345,
	//"channel_name":"チャンネル名1",
	//"subscribe_status":1,
	//"description":"説明文1",
	//"favicon_path":"http://abcd.ef.gh/ij/kl.ico",
	//"channel_type":0}

	public String result;
	public List<Channels> channels;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Channels> getChannels() {
		return channels;
	}

	public void setChannels(List<Channels> channels) {
		this.channels = channels;
	}

	public static class Channels {
		public String tab_id;
		public List<TabChannels> tab_channels;
		public String getTab_id() {
			return tab_id;
		}
		public void setTab_id(String tab_id) {
			this.tab_id = tab_id;
		}
		public List<TabChannels> getTab_channels() {
			return tab_channels;
		}
		public void setTab_channels(List<TabChannels> tab_channels) {
			this.tab_channels = tab_channels;
		}

	}

	public static class TabChannels {
		public String channel_id;
		public String channel_name;
		public String subscribe_status;
		public String description;
		public String favicon_path;
		public String channel_type;

		public String getChannel_id() {
			return channel_id;
		}

		public void setChannel_id(String channel_id) {
			this.channel_id = channel_id;
		}

		public String getChannel_name() {
			return channel_name;
		}

		public void setChannel_name(String channel_name) {
			this.channel_name = channel_name;
		}

		public String getSubscribe_status() {
			return subscribe_status;
		}

		public void setSubscribe_status(String subscribe_status) {
			this.subscribe_status = subscribe_status;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getFavicon_path() {
			return favicon_path;
		}

		public void setFavicon_path(String favicon_path) {
			this.favicon_path = favicon_path;
		}

		public String getChannel_type() {
			return channel_type;
		}

		public void setChannel_type(String channel_type) {
			this.channel_type = channel_type;
		}
	}

}
