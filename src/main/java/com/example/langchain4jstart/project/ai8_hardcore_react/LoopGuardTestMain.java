package com.example.langchain4jstart.project.ai8_hardcore_react;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.listener.*;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class LoopGuardTestMain {

    public static void main(String[] args) {
        System.out.println("====== 🛡️ [硬核防线演练] 正在注入 Loop Guard 计数拦截器 ======");

        // 1. 初始化模型，绑定 ThreadLocal 计数器
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b") 
                .timeout(Duration.ofMinutes(2))
                .listeners(Collections.singletonList(new ChatModelListener() {
                    
                    private final ThreadLocal<AtomicInteger> loopCounter = ThreadLocal.withInitial(() -> new AtomicInteger(0));

                    @Override
                    public void onRequest(ChatModelRequestContext context) {
                        int currentStep = loopCounter.get().incrementAndGet();
                        System.out.printf("\n⚙️ [Java 听诊器] 当前 Agent 推理自主循环进入第 [%d] 轮...\n", currentStep);
                        
                        // 🚨 铁壁熔断：超过 4 轮直接强制拍死，防止本地显卡拉满空转！
                        if (currentStep > 4) {
                            loopCounter.remove(); 
                            throw new RuntimeException("🚨 [安全熔断] 警告：监测到大模型陷入 ReAct 无限死循环，Java 拦截器强制熔断！");
                        }
                    }

                    @Override
                    public void onResponse(ChatModelResponseContext context) {
                        // 适配最新版 API：如果没有后续的 Tool 调用信号了，说明闭环，释放计数器
                        if (context.chatResponse() != null && context.chatResponse().aiMessage() != null) {
                            if (!context.chatResponse().aiMessage().hasToolExecutionRequests()) {
                                System.out.println("🏁 [Java 听诊器] 大模型推理正常闭环，主动释放计数器。");
                                loopCounter.remove();
                            }
                        }
                    }

                    @Override
                    public void onError(ChatModelErrorContext context) {
                        loopCounter.remove(); 
                    }
                }))
                .build();

        // 2. 注入刚刚新建的恶意陷阱工具箱
        LoopTrapTools trapTools = new LoopTrapTools();

        // 3. 构建智能体，直接绑定你原本写好的最高查验权限的 Assistant 接口
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(trapTools) 
                .build();

        System.out.println("\n--- 💀 开始进行死循环硬核熔断测试 ---");
        try {
            // 问一个必须有确定结果的问题，逼大模型由于拿不到正确数据而在两个工具之间反复横跳
            String response = assistant.chat("最高专家请注意，帮我彻查运单 YT1001 的风控级别！必须给出最终结论！");
            System.out.println("\n🤖 模型最终回包:\n" + response);
        } catch (Exception e) {
            System.err.println("\n🔥 [拦截成功] Java 成功守住了后台！捕获到熔断异常: " + e.getMessage());
        }
    }
}