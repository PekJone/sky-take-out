package com.face.test.demo.util;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-01  15:22
 */
@Component
public class WeatherUtilTool {
    private static final Map<String, String> MOCK_WEATHER = Map.of(
            "北京", "晴，22℃",
            "上海", "多云，25℃",
            "深圳", "雷阵雨，28℃"
    );

    public String queryWeather(String city) {
        return MOCK_WEATHER.getOrDefault(city, "未知城市天气");
    }
}
