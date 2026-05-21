package com.example.langchain4jstart.project.ai3_workflow;

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

                        .build();


        /*
         * 创建工具
         */
        LogisticsTools tools =
                new LogisticsTools();


        /*
         * =========================
         * Workflow 开始
         * =========================
         */


        /*
         * 第一步：
         * 查询运单
         */
        String orderInfo =
                tools.queryOrder("YT1001");

        System.out.println("运单信息：");
        System.out.println(orderInfo);


        /*
         * 第二步：
         * AI 分析运单
         */
        AnalysisResult result =
                assistant.analyze(orderInfo);


        /*
         * 第三步：
         * 输出 AI 结果
         */
        System.out.println("==============");

        System.out.println("AI分析结果：");

        System.out.println(result);
    }
}