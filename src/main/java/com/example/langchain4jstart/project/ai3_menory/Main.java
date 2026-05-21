package com.example.langchain4jstart.project.ai3_menory;


import dev.langchain4j.memory.chat.MessageWindowChatMemory;

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
         * 创建 Memory
         *
         * maxMessages：
         * 最多记忆多少条消息
         */
        MessageWindowChatMemory memory =
                MessageWindowChatMemory.withMaxMessages(10);


        /*
         * 创建 Assistant
         */
        Assistant assistant =
                AiServices.builder(Assistant.class)

                        .chatLanguageModel(model)

                        // 开启记忆
                        .chatMemory(memory)

                        .build();


        /*
         * 第一轮对话
         */
        String answer1 =
                assistant.chat(
                        "帮我查询运单YT1001"
                );

        System.out.println("第一轮：");
        System.out.println(answer1);


        /*
         * 第二轮对话
         */
        String answer2 =
                assistant.chat(
                        "它为什么异常"
                );

        System.out.println("第二轮：");
        System.out.println(answer2);
    }
}