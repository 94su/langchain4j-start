package com.example.langchain4jstart.project.ai12_rag_document_splitter;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class RagIngestMain {
    public static void main(String[] args) throws Exception {
        System.out.println("====== 🚀 [高级灌顶] 开始执行：私有文档智能切片入库 ======");

        // 1. 锁定绝对路径
        String projectPath = System.getProperty("user.dir");
        Path docFolderPath = Paths.get(projectPath, "src", "main", "resources", "ai-docs");
        System.out.println("📂 正在扫描目录: " + docFolderPath.toAbsolutePath());

        // 2. 批量加载原始长文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(docFolderPath);
        System.out.println("📄 成功读取原始文档数量: " + documents.size() + " 篇");

        // 3. 🌟 核心升级：定义工业级智能切片策略
        // 按段落切分，每个切片最多 300 个 Token（约 300-400 字），前后重叠 40 个 Token
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 40);

        // 4. 初始化本地内存向量库
        InMemoryEmbeddingStore embeddingStore = new InMemoryEmbeddingStore();

        // 5. 锁定你的 Nomic 向量双子星
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("nomic-embed-text") 
                .timeout(Duration.ofMinutes(3))
                .build();

        // 6. 🌟 将切片策略和向量模型拧在一起
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)     // 👈 告诉灌顶器先切片、再算向量
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();

        System.out.println("====== ⏳ 正在进行文本切片，并通知 Ollama 计算高维向量... ======");
        
        // 这一步会自动把你的长文档切成几十个小片，然后逐个翻译成 768 维向量存入
        ingestor.ingest(documents); 

        // 7. 固化落盘到源码目录
        Path storeOutputPath = docFolderPath.resolve("vector-store.json");
        embeddingStore.serializeToFile(storeOutputPath); 

        System.out.println("====== 🏁 恭喜！智能切片向量库 `vector-store.json` 已重新生成成功！ ======");
    }
}