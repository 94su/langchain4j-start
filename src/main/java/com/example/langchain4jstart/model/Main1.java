package com.example.langchain4jstart.model;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class Main1 {

    public static void main(String[] args) {

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434/")
                .modelName("qwen3.5:9b")
                .build();

        String answer = model.chat("你好，请介绍一下你自己");

        System.out.println(answer);
    }
}