package com.example.langchain4jstart.project.ai7_planning_agent;

import dev.langchain4j.agent.tool.Tool;

public class LogisticsTools {

    /*
     * 查询站点异常率
     */
    @Tool("查询站点异常率")
    public String queryStationRisk(String city) {

        System.out.println("调用 queryStationRisk");

        return """
                成都：
                异常率 21%
                风险等级 HIGH
                """;
    }


    /*
     * 查询高风险区域
     */
    @Tool("查询高风险区域")
    public String queryHighRiskArea(String city) {

        System.out.println("调用 queryHighRiskArea");

        return """
                高风险区域：

                1. 高新区
                2. 武侯区
                3. 双流区
                """;
    }


    /*
     * 查询异常原因
     */
    @Tool("查询异常原因")
    public String queryRiskReason(String city) {

        System.out.println("调用 queryRiskReason");

        return """
                主要异常原因：

                1. 电话无人接听
                2. 地址错误
                3. 派送人力不足
                """;
    }
}