package com.example.langchain4jstart.project.ai3_workflow;

public class AnalysisResult {

    /*
     * 风险等级
     */
    public String riskLevel;

    /*
     * 异常原因
     */
    public String reason;

    /*
     * AI 建议
     */
    public String suggestion;

    @Override
    public String toString() {

        return "AnalysisResult{" +
                "riskLevel='" + riskLevel + '\'' +
                ", reason='" + reason + '\'' +
                ", suggestion='" + suggestion + '\'' +
                '}';
    }
}