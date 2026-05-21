package com.example.langchain4jstart.project.ai10_skills;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

interface IotAssistant {
    // 依然是我们的完全体规范写法
    // 🌟 重点：通过系统指令给大模型立规矩，限制它的行为 没有系统提示词，ai会胡乱调用工具
    @SystemMessage({
            "你是一个专业的物联网云平台智能助手。",
            "【铁律】只有当用户明确提供了具体的设备编号、并要求查询设备状态时，你才允许调用查询工具。",
            "如果用户只是跟你进行日常问候、闲聊或提出无关问题，你必须直接友好地回答，绝对禁止找借口调用任何工具或编造设备编号！"
    })
    String chat(@UserMessage String message, @MemoryId String sessionId);
}