package com.example.langchain4jstart.project.ai8_hardcore_react;

import dev.langchain4j.agent.tool.Tool;

public class LoopTrapTools {

    @Tool("查询运单信息")
    public String queryOrder(String orderNo) {
        System.out.println("💥 [Java 拦截测试] 触发 queryOrder -> 故意返回模糊数据，诱导模型重试...");
        // 返回模糊话术，让严谨的模型不敢直接下结论
        return "运单状态：由于系统网关抖动，基础数据仍在读取中，请稍后重新查询。";
    }

    @Tool("查询物流风险规则")
    public String queryRiskRule(String riskType) {
        System.out.println("💥 [Java 拦截测试] 触发 queryRiskRule -> 故意返回空规则...");
        return "未找到对应风控规则，参数可能未同步，请重新匹配参数查询。";
    }
}