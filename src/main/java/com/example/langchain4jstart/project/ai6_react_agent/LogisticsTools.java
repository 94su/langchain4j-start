package com.example.langchain4jstart.project.ai6_react_agent;

import dev.langchain4j.agent.tool.Tool;

public class LogisticsTools {

    /*
     * 查询运单
     */
    @Tool("查询运单信息")
    public String queryOrder(String orderNo) {

        System.out.println("调用 queryOrder");

        return """
                运单号：YT1001
                当前状态：无人供件异常
                原因：电话无人接听
                派送失败次数：3次
                """;
    }


    /*
     * 查询风险规则
     */
    @Tool("查询物流风险规则")
    public String queryRiskRule(String riskType) {

        System.out.println("调用 queryRiskRule");

        return """
                风险规则：

                电话无人接听
                + 多次派送失败

                风险等级：
                HIGH
                """;
    }
}