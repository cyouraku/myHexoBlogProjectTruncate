package cn.zifangsky.manager;

import java.util.List;

import cn.zifangsky.model.WeatherWeather;
import cn.zifangsky.model.dto.StationDetailWeatherDTO;
import cn.zifangsky.model.dto.WeatherWeatherDTO;

public interface JsonStrCreatorManager {


	/**
	 *create json string for WeatherWeather list
	 * @return
	 */
	String creatJsonStrList(List<WeatherWeather> beanList);

	/**
	 *create json string for WeatherWeatherDto list
	 * @return
	 */
	String creatJsonStrListDto(List<WeatherWeatherDTO> beanList);

	/**
	 *create json string for StationDetailWeatherDto list
	 * @return
	 */
	String creatJsonStrListDetailDto(List<StationDetailWeatherDTO> beanList);



}
