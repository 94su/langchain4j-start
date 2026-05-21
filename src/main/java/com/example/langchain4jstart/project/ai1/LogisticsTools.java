package com.example.langchain4jstart.project.ai1;


import com.example.langchain4jstart.project.data.FakeDatabase;
import dev.langchain4j.agent.tool.Tool;

public class LogisticsTools {

    /*
     * AI 可调用工具
     */
    @Tool("查询运单信息")
    public String queryOrder(String orderNo) {

        System.out.println("AI正在查询运单：" + orderNo);

        return FakeDatabase.ORDERS.getOrDefault(
                orderNo,
                "未找到运单"
        );
    }
}