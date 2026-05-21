package com.example.langchain4jstart.project.ai9_redis_memory;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.store.memory.chat.redis.RedisChatMemoryStore;

public class RedisMemoryMain {

    // 独立代理接口
    interface MemoryAssistant {
        // 明确告诉框架：第一个是用户消息，第二个是分布式会话 ID
        String chat(@UserMessage String message, @MemoryId String sessionId);
    }

    public static void main(String[] args) {
        System.out.println("====== [1. 架构初始化] 正在配置中央共享 Redis 记忆中心 ======");

        // 1. 初始化中央 Redis 记忆存储（这里连接你的本地或测试环境 Redis）
        // 它会把每一轮对话，以 "chat-memory:会话ID" 的 Key 格式存入 Redis
        RedisChatMemoryStore redisStore = RedisChatMemoryStore.builder()
                .host("192.168.31.228")
                .port(6379)
                //.password("")
                .build();

        // 2. 模拟前端传过来的用户唯一 Session ID
        // 在真实 Spring Boot 项目里，这个 ID 可以是用户的 Token、UserId 或者前端传来的会话 UUID
        String userSessionId = "user_tenant_10086_session_01";

        // 3. 定义记忆提供者：每个不同的 SessionID，对应 Redis 里的独立滑窗记忆
        // MessageWindowChatMemory.withMaxMessages(10) 代表只保留最近 10 条对话，防止文本无限拉长撑爆上下文
        ChatMemoryProvider memoryProvider = chatId -> MessageWindowChatMemory.builder()
                .id(chatId)
                .maxMessages(10)
                .chatMemoryStore(redisStore) // 重点：将记忆仓库指定为 Redis
                .build();

        // 4. 构建大模型客户端
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b")
                .timeout(java.time.Duration.ofMinutes(3))
                .build();

        // 5. 组装 AI 服务
        MemoryAssistant assistant = AiServices.builder(MemoryAssistant.class)
                .chatLanguageModel(model)
                .chatMemoryProvider(memoryProvider) // 注入共享记忆机
                .build();

        // ====== 模拟第一轮对话 ======
        System.out.println("\n====== [2. 第一轮对话发起（打入记忆标签）] ======");
        // 注意：在大规模生产中，我们必须将会话ID（userSessionId）作为入参传给 AiServices 识别
        // 这里简化演示，框架内部会将当前会话绑定在 userSessionId 上
        String reply1 = assistant.chat("记住了，我们公司在浙江省嘉兴市，这里的特产是五芳斋粽子。", userSessionId);
        System.out.println("AI 回复: " + reply1);
        System.out.println("[Java 运行时提示] 💾 此时对话已序列化并成功写入远端 Redis！");

        // ====== 模拟第二轮对话 ======
        System.out.println("\n====== [3. 第二轮对话发起（跨越时空提问）] ======");
        // 哪怕这个 main 方法停掉重新跑，只要 Redis 里的 userSessionId 还在，AI 就能准确抢答
        String reply2 = assistant.chat("好了，我现在问你：我们公司的特产是什么？在哪个城市？", userSessionId);
        System.out.println("AI 最终抢答结果: ");
        System.out.println(reply2);
    }
}