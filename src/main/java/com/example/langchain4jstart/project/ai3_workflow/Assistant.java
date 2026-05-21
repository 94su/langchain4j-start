package com.example.langchain4jstart.project.ai3_workflow;

public interface Assistant {

    /*
     * AI 分析运单
     */
    AnalysisResult analyze(String orderInfo);
}