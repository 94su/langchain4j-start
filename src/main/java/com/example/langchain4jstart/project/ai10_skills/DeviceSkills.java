package com.example.langchain4jstart.project.ai10_skills;

import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceSkills {
    private static final Logger log = LoggerFactory.getLogger(DeviceSkills.class);

    // 🌟 重点：这里的描述必须大白话，大模型就是靠这段话来判断什么时候该用这个方法的！
    @Tool("根据物联网网关设备的编号（deviceId），查询该设备当前的在线状态、所属教室以及绑定的 Matter 设备数量")
    public String getDeviceStatus(String deviceId) {
        log.info("🚀 [Skills 触发] 大模型正在向你下发指令，要求查询设备: {}", deviceId);

        // 模拟真实的业务线：这里我们写死一点模拟数据，真实环境你可以去查 DB 
        if ("gateway_01".equalsIgnoreCase(deviceId)) {
            return "【设备档案】状态：在线(ONLINE)；位置：301多媒体教室；绑定Matter物模型数量：18个。";
        } else if ("gateway_02".equalsIgnoreCase(deviceId)) {
            return "【设备档案】状态：离线(OFFLINE)；位置：405计算机机房；绑定Matter物模型数量：0个。";
        } else {
            return "【系统提示】未能在物联网云平台中检索到编号为 " + deviceId + " 的边缘网关设备。";
        }
    }
}