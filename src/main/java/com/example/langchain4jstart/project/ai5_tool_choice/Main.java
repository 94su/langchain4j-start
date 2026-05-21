package com.example.langchain4jstart.project.ai5_tool_choice;

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
        Assistant assistant =
                AiServices.builder(Assistant.class)

                        .chatLanguageModel(model)

                        // 注册多个工具
                        .tools(new LogisticsTools())

                        .build();


        /*
         * 测试1：
         * 查询运单
         */
        String result1 =
                assistant.chat(
                        "帮我查询运单YT1001"
                );

        System.out.println(result1);


        /*
         * 测试2：
         * 查询站点风险
         */
        String result2 =
                assistant.chat(
                        "成都武侯站最近异常多吗"
                );

        System.out.println(result2);


        /*
         * 测试3：
         * 查询地图风险
         */
        String result3 =
                assistant.chat(
                        "高新区最近风险怎么样"
                );

        System.out.println(result3);
    }
}