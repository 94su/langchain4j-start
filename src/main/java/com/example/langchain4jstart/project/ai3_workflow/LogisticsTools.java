package com.example.langchain4jstart.project.ai3_workflow;

import dev.langchain4j.agent.tool.Tool;

public class LogisticsTools {

    /*
     * 查询运单
     */
    @Tool("查询运单")
    public String queryOrder(String orderNo) {

        System.out.println("正在查询运单：" + orderNo);

        return """
                运单号：YT1001
                状态：无人供件异常
                原因：收件人电话无人接听
                派送次数：3次
                当前站点：成都武侯站
                """;
    }
}