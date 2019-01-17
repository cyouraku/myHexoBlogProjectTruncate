package cn.zifangsky.webservice.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.zifangsky.manager.VideoManager;
import cn.zifangsky.model.Video;
import cn.zifangsky.webservice.VideoService;

public class VideoServiceImpl implements VideoService {

	@Resource(name="videoManager")
	private VideoManager videoManager;

	@Override
	public Video getVideoById(String id) {
		return videoManager.findVideo(Integer.parseInt(id));
	}

	@Override
	public Video getVideoByVideoName(String name) {
		return videoManager.getVideoByVideoName(name);
	}

	@Override
	public List<Video> getAllVideos() {
		return videoManager.getAllVideos();
	}

	@Override
	public Boolean addVideo(Video video) {
		return videoManager.addVideo(video);
	}

	@Override
	public Boolean deleteVideo(String id) {
		return videoManager.deleteVideo(Integer.parseInt(id));
	}

	@Override
	public Boolean updateVideo(Video video) {
		return videoManager.updateVideo(video);
	}


}
