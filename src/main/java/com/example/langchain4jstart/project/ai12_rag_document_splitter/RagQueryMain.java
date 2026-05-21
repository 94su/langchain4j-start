package com.example.langchain4jstart.project.ai12_rag_document_splitter;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.service.AiServices;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class RagQueryMain {

    // 定义 AI 助手的接口
    interface QuantAndIotAssistant {
        String chat(String message);
    }

    public static void main(String[] args) {
        System.out.println("====== 🚀 [线上环境启动] 正在加载智能切片向量库... ======");

        // 1. 聊天大模型锁定 Qwen
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b") 
                .timeout(Duration.ofMinutes(3))
                .build();

        // 2. 定位刚刚生成的 vector-store.json
        String projectPath = System.getProperty("user.dir");
        Path storePath = Paths.get(projectPath, "src", "main", "resources", "ai-docs", "vector-store.json");
        System.out.println("📖 正在读取索引文件: " + storePath.toAbsolutePath());

        // 从文件加载向量数据
        InMemoryEmbeddingStore embeddingStore = InMemoryEmbeddingStore.fromFile(storePath);

        // 3. 向量转换模型死死锁定 Nomic
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("nomic-embed-text") 
                .timeout(Duration.ofMinutes(3))
                .build();

        // 4. 构建检索器（让它每次最精准地捞出前 3 个最相关的切片喂给千问）
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(3) // 👈 调成 3 篇，让上下文更饱满
                .build();

        // 5. 组装最终服务
        QuantAndIotAssistant assistant = AiServices.builder(QuantAndIotAssistant.class)
                .chatLanguageModel(model)
                .contentRetriever(contentRetriever)
                .build();

        System.out.println("====== 🎉 [服务就绪] 智库加载完毕，开始连环轰炸提问！ ====== \n");

        // ======================== 🔥 连环刁难测试开始 ========================

        // 提问一：测试策略变量名与风控步骤
        String question1 = "我们在跑马丁格尔策略时，如果遇到单边暴跌，为了防止保证金死掉归零，有什么具体的动态救网清洗机制？";
        System.out.println("❓ 提问 1: " + question1);
        System.out.println("🤖 AI 答复: \n" + assistant.chat(question1));
        System.out.println("\n--------------------------------------------------\n");

        // 提问二：测试技术指标与硬编码常量
        String question2 = "我的量化脚本想用 MACD 动能因子，系统底层定义的资金分配控制常量叫什么名字？怎么过滤震荡市的假金叉？";
        System.out.println("❓ 提问 2: " + question2);
        System.out.println("🤖 AI 答复: \n" + assistant.chat(question2));
        System.out.println("\n--------------------------------------------------\n");

        // 提问三：测试跨领域的 IoT 手册
        String question3 = "顺便帮我查一下，Gateway_01 如果挂载达到 18 个上限，核心的数据清洗和强行剔除步骤是什么？";
        System.out.println("❓ 提问 3: " + question3);
        System.out.println("🤖 AI 答复: \n" + assistant.chat(question3));
    }
}