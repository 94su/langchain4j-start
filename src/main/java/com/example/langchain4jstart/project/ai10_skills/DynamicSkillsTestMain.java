package com.example.langchain4jstart.project.ai10_skills;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import java.util.Collections;

/**
 * 🎯 动态工具技能注入演练主入口
 * * 📝 【技术演进说明】：
 * 本类配合 {@link DynamicSkillsTools} 进行成套联动演练。
 * 彻底丢掉了硬编码的 `@Tool` 注解，演示了如何在运行时动态将【外部元数据说明】与【真实执行体】
 * 捆绑打包喂给智能体。这套骨架打通后，后续不管是做动态插件下发、还是做跨系统的 OpenAPI 动态挂载，都有了底层落地的依据。
 */
public class DynamicSkillsTestMain {

    public static void main(String[] args) {
        System.out.println("====== 🛡️ 正在动态绑定零注解动态工具链 ======");

        // 1. 初始化本地大模型
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://192.168.31.228:11434")
                .modelName("qwen3.5:9b")
                .build();

        // 2. 实例化我们的动态技能提供者
        DynamicSkillsTools skillsTools = new DynamicSkillsTools();

        // 3. 构建智能体，利用万能的 Map 映射关系，将动态元数据和执行逻辑完美缝合
        SkillAssistant assistant = AiServices.builder(SkillAssistant.class)
                .chatLanguageModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                // 🌟 核心硬核注入点：动态匹配，完全不依赖类方法上的注解
                .tools(Collections.singletonMap(
                        skillsTools.getDynamicSpecification(), 
                        skillsTools.getDynamicExecutor()
                ))
                .build();

        System.out.println("\n--- ⚡ 开始执行动态技能测试 ---");
        
        // 提问一个需要穿透物联网网关的问题
        String response = assistant.chat("帮我看一下 CLS_202 教室现在热不热？设备都正常吗？");
        
        System.out.println("\n🤖 大模型最终回包:\n" + response);
    }
}