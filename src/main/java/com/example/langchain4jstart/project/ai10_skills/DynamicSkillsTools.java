package com.example.langchain4jstart.project.ai10_skills;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema; // 👈 官方核心参数包
import dev.langchain4j.service.tool.ToolExecutor;
import java.util.Collections;

/**
 * 🛠️ 动态工具技能箱（零注解、动态参数注入方案）
 * * 📝 【排障终结小记】：
 * 1. 之前所有碰壁的原因在于：1.0.0-beta3 版本为了杜绝松散的 Map 拼写错误，彻底把 properties(Map) 降级为私有。
 * 2. 查阅官方 Javadoc 源码后确认，新版最正统、最稳固的 API 设计，是直接利用 JsonObjectSchema.builder() 
 * 身上公开自带的快捷属性添加器。
 * 3. 我们直接调用 .addStringProperty(键, 描述) 即可，API 纯净，没有任何多余的中间转换层。
 */
public class DynamicSkillsTools {

    /**
     * 🔮 动态获取大模型听得懂的工具说明书（Specification）
     */
    public ToolSpecification getDynamicSpecification() {
        
        // ✨ 查阅官方 1.0.0-beta3 源码后的正规军写法：
        JsonObjectSchema parametersSchema = JsonObjectSchema.builder()
                // 💡 官方公开自带的快捷添加器：直接传入参数名和它的具体功能描述
                .addStringProperty("classroomId", "教室的唯一编号，例如 CLS_001")
                .required(Collections.singletonList("classroomId")) // 声明必填项
                .build();

        return ToolSpecification.builder()
                .name("fetch_classroom_status")
                .description("根据教室ID，动态查询该智慧教室的设备运行状态与环境数据（物联网边缘网关通道）")
                .parameters(parametersSchema) 
                .build();
    }

    /**
     * ⚙️ 核心执行器：大模型外呼工具后，Java 后台的真实执行逻辑
     */
    public ToolExecutor getDynamicExecutor() {
        return new ToolExecutor() {
            @Override
            public String execute(ToolExecutionRequest request, Object memoryId) {
                System.out.println("🤖 [Dynamic 技能箱] 大模型 Action 触发，解析出参数: " + request.arguments());
                return "{\"status\": \"SUCCESS\", \"temperature\": \"24.5°C\", \"matter_devices\": \"12台在线\"}";
            }
        };
    }
}