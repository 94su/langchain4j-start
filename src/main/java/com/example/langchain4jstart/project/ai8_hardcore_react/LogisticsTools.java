package com.example.langchain4jstart.project.ai8_hardcore_react;

import dev.langchain4j.agent.tool.Tool;

public class LogisticsTools {

    @Tool("第一步必调：根据运单号查询该单据表面的物流状态和派送失败次数")
    public String queryOrder(String orderNo) {
        System.out.println("\n[Java 运行时感知] ⚙️ ⚡ 状态：【Action 1 触发】");
        System.out.println("[Java 运行时感知] ➡️ AI 正在调用 queryOrder，提取参数 orderNo = " + orderNo);

        return """
                运单号：YT1001
                物理状态：连续派送失败
                失败次数：3次
                failCount: 1
                快递员备注：收件人电话始终无人接听
                """;
    }

    @Tool("第二步必调：传入物流异常状态和失败次数，核对官方风控防诈骗拦截规则，获取风险等级")
    public String queryRiskRule(String statusDescription, int failCount) {
        System.out.println("\n[Java 运行时感知] ⚙️ ⚡ 状态：【Action 2 触发】");
        System.out.println("[Java 运行时感知] ➡️ AI 正在调用 queryRiskRule");
        System.out.println("[Java 运行时感知] ➡️ 参数1(statusDescription) = " + statusDescription + ", 参数2(failCount) = " + failCount);
        
        if (failCount >= 3) {
            return """
                    核查结果：触发【刷单诈骗/失联件】拦截预警！
                    风险评级：CRITICAL（极高风险）
                    处置建议：立刻暂停后续派送，由内勤启动人工二次反诈外呼。
                    """;
        }
        return "风险评级：LOW（正常重试即可）";
    }
}