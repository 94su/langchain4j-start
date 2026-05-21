package com.example.langchain4jstart.project.ai5_tool_choice;

import dev.langchain4j.service.SystemMessage;

public interface Assistant {

    @SystemMessage("""
            你是物流 AI 助手。

            你可以：

            1. 查询运单
            2. 查询站点风险
            3. 查询地图风险

            请根据用户问题，
            自动选择最合适的工具。
            """)
    String chat(String message);
}