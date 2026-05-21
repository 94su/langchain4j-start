package com.example.langchain4jstart.model;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class Main2 {

    public static void main(String[] args) {

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434/")
                .modelName("qwen3.5:9b")
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(new WeatherTools())
                .build();

        String answer = assistant.chat("北京风力怎么样");
        System.out.println("111"+answer);


        String answer2 = assistant.chat("北京降雨怎么样");
        System.out.println("222"+answer2);

    }
}