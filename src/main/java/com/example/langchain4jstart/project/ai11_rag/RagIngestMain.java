package com.example.langchain4jstart.project.ai11_rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class RagIngestMain {
    public static void main(String[] args) throws Exception {
        System.out.println("====== 🚀 开始执行：私有文档一键灌顶入库（Nomic版） ======");

        // 1. 安全获取资源目录
        URL resourceUrl = RagIngestMain.class.getClassLoader().getResource("ai-docs");
        if (resourceUrl == null) {
            System.err.println("❌ 错误：未在 resources 目录下找到 ai-docs 文件夹！");
            return;
        }
        Path docFolderPath = Paths.get(resourceUrl.toURI());
        System.out.println("📂 当前扫描的资源目录: " + docFolderPath.toAbsolutePath());

        // 2. 加载文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(docFolderPath);

        // 3. 初始化本地内存向量库
        InMemoryEmbeddingStore embeddingStore = new InMemoryEmbeddingStore();

        // 4. 🌟 使用你本地现成的 nomic 向量模型！
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("nomic-embed-text") // 👈 换成你的本地专属向量模型
                .timeout(Duration.ofMinutes(3))
                .build();

        // 5. 组装并执行
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();

        System.out.println("====== ⏳ 正在通知 Ollama (Nomic) 计算高维向量... ======");
        ingestor.ingest(documents);

        // 6. 🌟 终极完善：同时往 target 和 src 源码目录各写一份，彻底解决找不到文件的问题
        // 6.1 先写到运行时目录（target）
        Path targetOutputPath = docFolderPath.resolve("vector-store.json");
        embeddingStore.serializeToFile(targetOutputPath);

        // 6.2 强行写回源码目录（src/main/resources）
        try {
            // 通过 target 路径反向定位 src 路径
            String projectPath = System.getProperty("user.dir");
            Path srcOutputPath = Paths.get(projectPath, "src", "main", "resources", "ai-docs", "vector-store.json");
            embeddingStore.serializeToFile(srcOutputPath);
            System.out.println("💾 源码目录同步成功: " + srcOutputPath.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("⚠️ 提示：自动同步源码目录失败，但不影响使用，请直接检查 target 目录。");
        }

        System.out.println("====== 🏁 恭喜！两端向量库索引固化全部完成！ ======");
    }
}