package com.example.langchain4jstart.project.ai7_planning_agent;

import dev.langchain4j.service.SystemMessage;

public interface Assistant {

    @SystemMessage("""
            你是物流 Planning Agent。

            你的工作流程：

            1. 先制定完整计划
            2. 再按步骤调用工具
            3. 最后总结分析结果

            必须先思考整体方案，
            不能直接回答。
            """)
    String chat(String message);
}