package cn.zifangsky.manager;

import java.util.List;

import cn.zifangsky.model.Video;

public interface VideoManager {

    /**
     * 查询某个video by id
     * @return
     */
	public Video findVideo(int id);

    /**
     * 查询某个video by name
     * @return
     */
	public Video getVideoByVideoName(String name);

    /**
     * 查询全体video
     * @return
     */
	public List<Video> getAllVideos();

    /**
     * 增加video
     * @return
     */
	public Boolean addVideo(Video video);

    /**
     * 删除video
     * @return
     */
	public Boolean deleteVideo(int id);

    /**
     * 更新video信息
     * @return
     */
	public Boolean updateVideo(Video video);
}
