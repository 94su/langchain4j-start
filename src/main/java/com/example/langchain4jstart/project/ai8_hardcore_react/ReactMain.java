package com.example.langchain4jstart.project.ai8_hardcore_react;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import java.time.Duration;

public class ReactMain {

    public static void main(String[] args) {
        System.out.println("====== [1. 系统初始化] 正在加载 Qwen 模型并注册特种兵工具箱 ======");
        
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b")
                .timeout(Duration.ofMinutes(3)) // ReAct多轮对话耗时较长，放宽超时
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(new LogisticsTools()) // 注册我们的互锁工具箱
                .build();

        String question = "帮我排查运单YT1001到底有什么风险";
        System.out.println("\n====== [2. 用户发起任务] ======");
        System.out.println("用户提问: " + question);

        System.out.println("\n====== [3. ReAct 推理链条开始（请观察下方 Java 方法触发）] ======");
        String finalAnswer = assistant.chat(question);

        System.out.println("\n====== [4. AI 最终综合分析报告] ======");
        System.out.println(finalAnswer);
    }
}