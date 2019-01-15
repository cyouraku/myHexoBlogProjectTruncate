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

package sample.searchArticle.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sample.searchArticle.entity.ChannelListResponseDTO;
import sample.searchArticle.entity.ChannelListResponseDTO.Channels;
import sample.searchArticle.entity.ChannelListResponseDTO.TabChannels;
import sample.searchArticle.util.MyException;
import sample.searchArticle.util.MyRestResponse;

@Controller
public class WelcomeController {

	private Logger logger = Logger.getLogger(WelcomeController.class.getName());

	@Value("${application.message:Hello World}")
	private String message;

	@GetMapping("/")
	public String topPage(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		model.put("tabIndex", "0");
		logger.info(String.format(
				"tabIndex = %s \n", model.get("tabIndex")));
		return "SC-USR-101-101";
	}

	@GetMapping("/index")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}

	// /api/1/user/channel/get/channellist
	// "appVersion" : null,
	// "bgflg" : 1
	//Content-Type: applicatoin/json; charset=UTF-8
	//dataType : 'json',
	@PostMapping("/api/1/user/channel/get/channelMap")
	public @ResponseBody ChannelListResponseDTO getChannelMap(@RequestBody Map<String, Object> data) {
		logger.info(String.format(
				"rest method = %s \n", "getChannelMap()"));
		if (data == null) {
			logger.info(String.format("post data is null \n"));
		} else {
			logger.info(String.format(
					"data  = %s \n", data));
			logger.info(String.format(
				"data size = %d \n", data.size()));
			logger.info(String.format(
					"post data appVersion = %s, bgflg = %s \n",
					data.get("appVersion"), data.get("bgflg")));
		}
		return this.createReponse();
	}

	@PostMapping("/api/1/user/channel/get/channellist")
	public @ResponseBody ChannelListResponseDTO getChannelList(@RequestBody String data) {
		logger.info(String.format(
				"rest method = %s \n", "getChannelList()"));
		if (data == null || "".equals(data)) {
			logger.info(String.format("post data is null \n"));
		} else {
			logger.info(String.format(
				"data  = %s \n", data));
		}
		return this.createReponse();
	}

	@RequestMapping("/fail")
	public String fail() {
		throw new MyException("Oh dear!");
	}

	@RequestMapping("/fail2")
	public String fail2() {
		throw new IllegalStateException();
	}

	@ExceptionHandler(MyException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody MyRestResponse handleMyRuntimeException(
			MyException exception) {
		return new MyRestResponse(
				"Some data I want to send back to the client.");
	}

	// "result":"0000", "channels":[{"tab_id":0,
	// "tab_channels":
	// [{"channel_id":12345,
	// "channel_name":"チャンネル名1",
	// "subscribe_status":1,
	// "description":"説明文1",
	// "favicon_path":"http://abcd.ef.gh/ij/kl.ico",
	// "channel_type":0}
	private ChannelListResponseDTO createReponse() {
		ChannelListResponseDTO resp = new ChannelListResponseDTO();
		resp.setResult("0000");
		List<Channels> list = new ArrayList<Channels>();
		list.add(this.createChannel("0"));
		list.add(this.createChannel("1"));
		list.add(this.createChannel("2"));
		list.add(this.createChannel("3"));
		list.add(this.createChannel("4"));
		list.add(this.createChannel("5"));
		list.add(this.createChannel("6"));
		resp.setChannels(list);
		return resp;
	}

	private Channels createChannel(String tabId){
		List<TabChannels> tabList = new ArrayList<TabChannels>();
		tabList.add(this.createTabChannel());
		Channels channels = new Channels();
		channels.setTab_id(tabId);
		channels.setTab_channels(tabList);
		return channels;
	}

	private TabChannels createTabChannel(){
		TabChannels tab = new TabChannels();
		tab.setChannel_id("12345");
		tab.setSubscribe_status("1");
		tab.setDescription("説明文１");
		tab.setFavicon_path("http://localhost:8008/static/images/favicon/default_site_favicon.png");
		tab.setChannel_type("0");
		return tab;
	}


}
