package com.example.langchain4jstart.model;

import dev.langchain4j.agent.tool.Tool;

public class WeatherTools {

    @Tool("获取天气")
    public String getWeather(String city) {

        System.out.println("天气工具被调用了，城市：" + city);

        return city + "天气晴朗 25度";
    }

    @Tool("获取风力")
    public String getWindScale(String city) {

        System.out.println("风力工具被调用了，城市：" + city);

        return city + "风力 4级";
    }
}