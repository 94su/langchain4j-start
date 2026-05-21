package com.example.langchain4jstart.project.data;

import java.util.HashMap;
import java.util.Map;

public class FakeDatabase {

    // 模拟数据库
    public static final Map<String, String> ORDERS =
            new HashMap<>();

    static {

        ORDERS.put(
                "YT1001",

                """
                运单号：YT1001
                状态：无人供件异常
                站点：成都武侯站
                原因：收件人电话无人接听
                """
        );

        ORDERS.put(
                "YT1002",

                """
                运单号：YT1002
                状态：派送失败
                原因：地址错误
                """
        );
    }
}