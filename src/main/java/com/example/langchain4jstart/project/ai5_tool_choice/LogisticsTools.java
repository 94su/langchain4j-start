package com.example.langchain4jstart.project.ai5_tool_choice;

import dev.langchain4j.agent.tool.Tool;

public class LogisticsTools {

    /*
     * 查询运单
     */
    @Tool("查询运单信息")
    public String queryOrder(String orderNo) {

        System.out.println("调用了 queryOrder");

        return """
                运单号：YT1001
                状态：无人供件异常
                原因：电话无人接听
                """;
    }


    /*
     * 查询站点风险
     */
    @Tool("查询站点异常风险")
    public String queryStationRisk(String stationName) {

        System.out.println("调用了 queryStationRisk");

        return """
                成都武侯站：
                今日异常率 23%
                风险等级 HIGH
                """;
    }


    /*
     * 查询地图风险
     */
    @Tool("查询区域地图风险")
    public String queryMapRisk(String area) {

        System.out.println("调用了 queryMapRisk");

        return """
                高新区：
                无人供件异常高发
                最近三天增长40%
                """;
    }
}