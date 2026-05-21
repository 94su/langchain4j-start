package com.example.langchain4jstart.project.ai7_planning_agent;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import dev.langchain4j.service.AiServices;

public class Main {

    public static void main(String[] args) {

        /*
         * 创建模型
         */
        ChatLanguageModel model =
                OllamaChatModel.builder()

                        .baseUrl("http://192.168.31.228:11434")

                        .modelName("qwen3.5:9b")

                        .build();


        /*
         * 创建 Assistant
         */
        Assistant assistant =
                AiServices.builder(Assistant.class)

                        .chatLanguageModel(model)

                        .tools(new LogisticsTools())

                        .build();


        /*
         * 用户请求
         */
        String answer =
                assistant.chat(
                        "帮我分析成都最近物流异常，并给出整改方案"
                );


        /*
         * 输出结果
         */
        System.out.println("==============");

        System.out.println(answer);
    }
}