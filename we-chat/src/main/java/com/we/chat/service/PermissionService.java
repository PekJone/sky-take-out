package com.we.chat.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  17:31
 */
@Service
public class PermissionService {
    // 模拟权限库
    private final Map<String, Set<String>> uriPermissions = Map.of(
            "/order/create", Set.of("order:create"),
            "/user/info", Set.of("user:info")
    );

    public List<String> getPermissions(Long userId) {
        // 从DB/Redis获取
        return Arrays.asList("user:info", "order:create");
    }

    public boolean hasPermission(String uri, List<String> userPermissions) {
        Set<String> required = uriPermissions.getOrDefault(uri, Set.of());
        return userPermissions.containsAll(required);
    }
}
