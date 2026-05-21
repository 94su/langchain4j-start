package com.example.langchain4jstart.project.ai13_mcp_client;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class Main {

    public static void main(String[] args) throws Exception {

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")  // 你本地 Ollama IP
                .modelName("qwen3.5:9b")
                .build();

        MCPAgent agent = AiServices.builder(MCPAgent.class)
                .chatLanguageModel(model)
                .tools(new ExternalApiChains())
                .build();

        String result = agent.analyzeOrder("YT1001", "成都");

        System.out.println("========== MCP 输出 ==========");
        System.out.println(result);
    }
}