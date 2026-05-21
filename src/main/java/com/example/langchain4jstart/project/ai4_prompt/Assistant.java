package com.example.langchain4jstart.project.ai4_prompt;

import dev.langchain4j.service.SystemMessage;

public interface Assistant {

    /*
     * System Prompt
     *
     * 告诉 AI：
     * 它是谁
     * 应该怎么做
     */
    @SystemMessage("""
            你是一个物流异常分析专家。

            你的任务：
            1. 分析运单异常原因
            2. 判断风险等级
            3. 给出处理建议

            风险等级规则：

            HIGH：
            - 电话无人接听
            - 多次派送失败
            - 地址异常

            MEDIUM：
            - 一次派送失败

            LOW：
            - 正常延迟

            请返回专业结果。
            """)
    AnalysisResult analyze(String orderInfo);
}