package cn.zifangsky.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.ws.Holder;

import cn.zifangsky.common.PageInfo;
import cn.zifangsky.common.StationEnmuClass;
import cn.zifangsky.manager.WeatherWeatherManager;
import cn.zifangsky.model.WeatherWeather;
import cn.zifangsky.model.bo.WeatherWeatherBO;
import cn.zifangsky.model.dto.StationDetailWeatherDTO;
import cn.zifangsky.webservice.WeatherWeatherService;

public class WeatherWeatherServiceImpl implements WeatherWeatherService {

	@Resource(name="weatherWeatherManager")
	private WeatherWeatherManager weatherManager;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return weatherManager.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(WeatherWeather weather) {
		return weatherManager.insert(weather);
	}

	@Override
	public int insertSelective(WeatherWeather weather) {
		return weatherManager.insertSelective(weather);
	}

	@Override
	public WeatherWeather selectByPrimaryKey(Long id) {
		return weatherManager.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WeatherWeather weather) {
		return weatherManager.updateByPrimaryKeySelective(weather);
	}

	@Override
	public int updateByPrimaryKey(WeatherWeather weather) {
		return weatherManager.updateByPrimaryKey(weather);
	}

	@Override
	public Long findAllCount(WeatherWeather weather) {
		return weatherManager.findAllCount(weather);
	}

	@Override
	public List<WeatherWeather> findAll(Holder<PageInfo> pageInfoHolder, WeatherWeather weather) {
		pageInfoHolder.value.setCount(weatherManager.findAllCount(weather));
		return weatherManager.findAll(pageInfoHolder.value, weather);
	}

	@Override
	public WeatherWeatherBO selectByStationCode(String stationCode) {
		return weatherManager.selectByStationCode(stationCode);
	}

	@Override
	public List<WeatherWeatherBO> selectByStationName(String stationName) {
		return weatherManager.selectByStationName(stationName);
	}

	@Override
	public WeatherWeatherBO selectByStationCodeRest(String stationCode) {
		return weatherManager.selectByStationCode(stationCode);
	}

	@Override
	public List<WeatherWeatherBO> selectByStationNameRest(String stationName) {
		return weatherManager.selectByStationName(stationName);
	}

	@Override
	public List<StationDetailWeatherDTO> selectByStationNameRestDto() {
		List<StationDetailWeatherDTO> dtoList = new ArrayList<StationDetailWeatherDTO>();
		//First and second level city list
//		List<String> stationList = FileUtil.readFile(FileUtil.FILE_PATH);
		String[] stationList = {"北京","上海","广州","深圳","三亚","宣化","泰安","长春"};
		for(String stationName : stationList) {
			WeatherWeatherBO we = weatherManager.selectByStationCode(StationEnmuClass.getId(stationName));
			StationDetailWeatherDTO dtoWe = new StationDetailWeatherDTO();
			dtoWe.setCity(stationName);
			String weather = we.getWeather().getToday();
			dtoWe.setDay(weather.split(",")[0]);
			dtoWe.setWeather(String.format("%s,%s,%s,%s \n", weather.split(",")[1], weather.split(",")[2],
					weather.split(",")[3], weather.split(",")[4]));
			dtoList.add(dtoWe);
		}
		return dtoList;
	}

	@Override
	public List<StationDetailWeatherDTO> selectByStationNameRestDto2(String stationName) {
		List<StationDetailWeatherDTO> dtoList = new ArrayList<StationDetailWeatherDTO>();
		List<String> weatherList = new ArrayList<String>();
		List<WeatherWeather> weList = weatherManager.selectByStationName2(stationName);
		for(WeatherWeather we : weList) {
			weatherList.add(we.getToday());
			weatherList.add(we.getNextday());
			weatherList.add(we.getNext2day());
			weatherList.add(we.getNext3day());
			weatherList.add(we.getNext4day());
			weatherList.add(we.getNext5day());
			weatherList.add(we.getNext6day());
		}
		for(String weather : weatherList) {
			StationDetailWeatherDTO dtoWe = new StationDetailWeatherDTO();
			dtoWe.setCity(stationName);
			dtoWe.setDay(weather.split(",")[0]);
			dtoWe.setWeather(String.format("%s,%s,%s,%s \n", weather.split(",")[1], weather.split(",")[2],
					weather.split(",")[3], weather.split(",")[4]));
			dtoList.add(dtoWe);
		}
		return dtoList;
	}

	@Override
	public List<StationDetailWeatherDTO> selectByStationNameRestDto3(String stationName) {
		List<StationDetailWeatherDTO> dtoList = new ArrayList<StationDetailWeatherDTO>();
		List<WeatherWeather> weList = weatherManager.selectByStationName2(stationName);
		for(WeatherWeather we : weList) {
			String[] hours = we.getHour().split("\",\"");
			for(String weather : hours) {
				StationDetailWeatherDTO dtoWe = new StationDetailWeatherDTO();
				dtoWe.setCity(stationName);
				weather = weather.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
				dtoWe.setDay(weather.split(",")[0]);
				dtoWe.setWeather(String.format("%s,%s,%s,%s \n", weather.split(",")[2], weather.split(",")[3],
						weather.split(",")[4], weather.split(",")[5]));
				dtoList.add(dtoWe);
			}
		}
		return dtoList;
	}

}
