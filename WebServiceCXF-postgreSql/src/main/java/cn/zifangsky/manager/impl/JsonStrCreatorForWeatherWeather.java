package cn.zifangsky.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.zifangsky.manager.JsonStrCreatorManager;
import cn.zifangsky.model.WeatherWeather;
import cn.zifangsky.model.dto.StationDetailWeatherDTO;
import cn.zifangsky.model.dto.WeatherWeatherDTO;

@Service("jsonCreatorForWeatherWeather")
public class JsonStrCreatorForWeatherWeather implements JsonStrCreatorManager{

	@Override
	public String creatJsonStrList(List<WeatherWeather> beanList) {
		return JSON.toJSONString(beanList,SerializerFeature.UseSingleQuotes);
	}

	@Override
	public String creatJsonStrListDto(List<WeatherWeatherDTO> beanList) {
		return JSON.toJSONString(beanList,SerializerFeature.UseSingleQuotes);
	}

	@Override
	public String creatJsonStrListDetailDto(List<StationDetailWeatherDTO> beanList) {
		return JSON.toJSONString(beanList,SerializerFeature.UseSingleQuotes);
	}

}
