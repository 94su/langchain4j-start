package com.example.langchain4jstart.project.ai10_skills;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import java.time.Duration;

public class SkillsMain {

    public static void main(String[] args) {
        System.out.println("====== [1. 系统初始化] 正在加载 IoT 智能助手及专属 Skills ====== ");

        // 1. 配置大模型（记住加 timeout ！）
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b")
                .timeout(Duration.ofMinutes(3)) 
                .build();

        // 2. 简单的内存记忆提供者
        ChatMemoryProvider memoryProvider = chatId -> MessageWindowChatMemory.builder()
                .id(chatId)
                .maxMessages(10)
                .build();

        // 3. 核心：构建 AI 服务，把我们的技能类丢进去！
        IotAssistant assistant = AiServices.builder(IotAssistant.class)
                .chatLanguageModel(model)
                .chatMemoryProvider(memoryProvider)
                .tools(new DeviceSkills()) // 🌟 重点：在这里注册你的技能包！可以传多个对象
                .build();

        String sessionId = "user_01";

        // ====== 测试场景 1：闲聊（不会触发工具） ======
        System.out.println("\n====== [测试 1：普通闲聊] ======");
        String r1 = assistant.chat("你好，你是谁？", sessionId);
        System.out.println("AI 回复: " + r1);

        // ====== 测试场景 2：精准触发工具 ======
        System.out.println("\n====== [测试 2：业务精确触发] ======");
        // 💡 故意用自然语言刁难它，看它能不能精准提取出 "gateway_01" 并调你的 Java 方法
        String r2 = assistant.chat("帮我查查 301 教室那个编号是 gateway_01 的网关，看看它死了没？", sessionId);
        System.out.println("AI 最终答复: \n" + r2);
        
        // ====== 测试场景 3：胡诌一个设备 ======
        System.out.println("\n====== [测试 3：未知设备触发] ======");
        String r3 = assistant.chat("查一下 gateway_999", sessionId);
        System.out.println("AI 最终答复: \n" + r3);
    }
}