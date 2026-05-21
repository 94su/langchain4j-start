package com.example.langchain4jstart.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.nio.file.Paths;
import java.util.List;

public class RagDemo {

    public static void main(String[] args) {
        // 1. 创建聊天模型 (Qwen3.5)
        ChatLanguageModel chatModel = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b")
                .build();

        // 2. 创建 Embedding 模型 (Nomic)
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("nomic-embed-text")
                .build();

        // 3. 创建内存向量库
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 4. 读取知识库文件 (增加路径引导，防止找不到文件)
        System.out.println("请确保你的 knowledge.txt 放在此路径下: " + Paths.get("knowledge.txt").toAbsolutePath());
        Document document = FileSystemDocumentLoader.loadDocument(Paths.get("knowledge.txt"));

        // 5. 获取文档全文
        String text = document.text();

        // 6. 手动创建文本切片 (这里为了演示方便，把全文当作一个切片)
        TextSegment segment = TextSegment.from(text);

        // 7. 文本转向量
        var embedding = embeddingModel.embed(segment).content();

        // 8. 向量和文本切片一起存入向量库
        embeddingStore.add(embedding, segment);

        // 9. 用户问题
        String question = "无人供件怎么处理";

        // 10. 用户问题转向量
        var questionEmbedding = embeddingModel.embed(question).content();

        // 11. 创建搜索请求对象 (新版 API 规范)
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(questionEmbedding)
                .maxResults(3)
                .build();

        // 12. 执行向量检索
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(request);

        // 13. 获取相似度匹配结果列表
        List<EmbeddingMatch<TextSegment>> matches = searchResult.matches();

        // 14. 循环遍历并拼接检索出的知识上下文
        StringBuilder context = new StringBuilder();
        for (EmbeddingMatch<TextSegment> match : matches) {
            String content = match.embedded().text();
            context.append(content).append("\n");
        }

        // 15. 组装最终喂给大模型的 Prompt
        String finalPrompt = "请根据以下知识回答问题：\n\n" + context + "\n用户问题：" + question;

        // 16. 调用大模型获取最终回答
        String answer = chatModel.chat(finalPrompt);

        // 17. 打印输出结果
        System.out.println("==============\n最终Prompt：\n" + finalPrompt);
        System.out.println("==============\nAI回答：\n" + answer);
    }
}