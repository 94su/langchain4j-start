package com.example.langchain4jstart.project.ai2;

public interface Assistant {

    /*
     * AI 返回 Java 对象
     */
    AnalysisResult analyze(String message);
}