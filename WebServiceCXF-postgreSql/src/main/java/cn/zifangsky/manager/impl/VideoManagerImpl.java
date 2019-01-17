package cn.zifangsky.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.VideoManager;
import cn.zifangsky.mapper.VideoMapper;
import cn.zifangsky.model.Video;

@Service("videoManager")
public class VideoManagerImpl implements VideoManager {

	@Autowired
    private VideoMapper videoMapper;

	@Override
	public Video findVideo(int id) {
		return this.videoMapper.selectVideoById(id);
	}

	@Override
	public Video getVideoByVideoName(String name) {
		return this.videoMapper.selectVideoByName(name);
	}

	@Override
	public List<Video> getAllVideos() {
		return this.videoMapper.selectAllVideos();
	}

	@Override
	public Boolean addVideo(Video video) {
		return this.videoMapper.insertSelective(video) > 0;
	}

	@Override
	public Boolean deleteVideo(int id) {
		return this.videoMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public Boolean updateVideo(Video video) {
		return this.videoMapper.updateByPrimaryKeySelective(video) > 0;
	}



}
