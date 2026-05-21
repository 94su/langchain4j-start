package com.example.langchain4jstart.project.ai11_rag;

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

    interface CompanyAssistant {
        String chat(String message);
    }

    public static void main(String[] args) {
        System.out.println("====== 🚀 [线上环境启动] 正在直连持久化向量库... ======");

        // 1. 初始化聊天大模型 Qwen (负责把检索出来的答案和你的问题融合，组织语言回答)
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b") // 👈 聊天用 Qwen，不动它
                .timeout(Duration.ofMinutes(3))
                .build();

        // 2. 定位并读取你在 Ingest 阶段用 Nomic 算出来的本地向量 JSON
        String projectPath = System.getProperty("user.dir");
        Path storePath = Paths.get(projectPath, "src", "main", "resources", "ai-docs", "vector-store.json");
        System.out.println("📖 当前绝对读取的索引文件: " + storePath.toAbsolutePath());

        // 加载向量数据
        InMemoryEmbeddingStore embeddingStore = InMemoryEmbeddingStore.fromFile(storePath);

        // 3. 🌟 修复核心：把检索端的“问题转换器”也同步换成 nomic-embed-text！
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("nomic-embed-text") // 👈 必须跟 Ingest 端完全一致，改成 nomic 
                .timeout(Duration.ofMinutes(3))
                .build();

        // 4. 构建检索器
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2)
                .build();

        // 5. 组装 AI 服务
        CompanyAssistant assistant = AiServices.builder(CompanyAssistant.class)
                .chatLanguageModel(model)
                .contentRetriever(contentRetriever)
                .build();

        System.out.println("====== 🎉 [服务就绪] 绝对路径加载成功，开始回答请求 ====== ");

        // 6. 发起提问
        String response = assistant.chat("跟我说说 gateway_01 遇到 18 个上限怎么清洗？还有配网要用哪个 Key？");
        System.out.println("\nAI 答复: \n" + response);
    }
}