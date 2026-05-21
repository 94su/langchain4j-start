package com.example.langchain4jstart.project.ai13_mcp_client;

import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import java.io.IOException;

public class ExternalApiChains {

    private final HttpClient client = HttpClient.newHttpClient();

    // 1. 查询快递物流信息（快递100公开API示例）
    public String queryLogistics(String com, String num) throws IOException, InterruptedException {
        // 示例接口（无key，可直接访问）
        String url = String.format("https://www.kuaidi100.com/query?type=%s&postid=%s", com, num);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 2. 查询天气信息（Open-Meteo 免费 API）
    public String queryWeather(String city) throws IOException, InterruptedException {
        // Open-Meteo 不需要 key
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m",
                getLatitude(city),
                getLongitude(city)
        );
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 3. 查询地理信息（高德地图公共接口示例）
    public String queryGeoInfo(String address) throws IOException, InterruptedException {
        // 高德免费Web服务需要注册Key，这里用模拟地址测试
        String url = String.format("https://restapi.amap.com/v3/geocode/geo?address=%s&key=你的高德Key", address);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 模拟城市到经纬度
    private String getLatitude(String city) {
        switch (city) {
            case "成都": return "30.5728";
            case "北京": return "39.9042";
            case "上海": return "31.2304";
            default: return "0";
        }
    }

    private String getLongitude(String city) {
        switch (city) {
            case "成都": return "104.0668";
            case "北京": return "116.4074";
            case "上海": return "121.4737";
            default: return "0";
        }
    }
}