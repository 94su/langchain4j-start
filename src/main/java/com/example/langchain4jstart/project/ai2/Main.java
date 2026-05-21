package com.example.langchain4jstart.project.ai2;


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
         * AI 分析
         */
        AnalysisResult result =
                assistant.analyze(
                        """
                        运单异常：
                        收件人电话无人接听，
                        多次派送失败
                        """
                );


        /*
         * 输出结果
         */
        System.out.println(result);
    }
}