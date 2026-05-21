package com.example.langchain4jstart.project.ai13_mcp_client;

import dev.langchain4j.service.*;

public interface MCPAgent {

    @SystemMessage("""
        你是企业 MCP Agent。
        你可以调用外部 API：
        - 物流信息
        - 天气信息
        - 地理信息
        多条任务链独立执行并汇总结果。
        """)
    String analyzeOrder(
            // @UserName @UserMessage @MemoryId  根据具体业务/功能选择对应注解
            @UserMessage String orderNo,          // 用户输入，用于物流单号
            @UserMessage String city    // 用户输入，用于天气/地理查询
    );
}