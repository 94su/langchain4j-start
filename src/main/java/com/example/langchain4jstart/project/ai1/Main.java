package com.example.langchain4jstart.project.ai1;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import dev.langchain4j.service.AiServices;

public class Main {

    public static void main(String[] args) {

        /*
         * 创建模型
         */
        ChatLanguageModel model = OllamaChatModel.builder()

                .baseUrl("http://192.168.31.228:11434")

                .modelName("qwen3.5:9b")

                .build();


        /*
         * 创建 AI Assistant
         */
        Assistant assistant = AiServices.builder(Assistant.class)

                .chatLanguageModel(model)

                // 注册 AI 工具
                .tools(new LogisticsTools())

                .build();


        /*
         * 用户提问
         */
        String answer = assistant.chat(
                "帮我查询运单 YT1001"
        );


        /*
         * 输出 AI 回复
         */
        System.out.println(answer);
    }
}