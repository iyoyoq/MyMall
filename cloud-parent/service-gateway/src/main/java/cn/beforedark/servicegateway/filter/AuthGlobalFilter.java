package cn.beforedark.servicegateway.filter;

import cn.beforedark.common.config.gateway.GatewayRequestHeader;
import cn.beforedark.common.config.redis.RedisKey;
import cn.beforedark.common.model.gateway.UserInfoByToken;
import cn.beforedark.common.util.JsonUtil;
import cn.beforedark.servicegateway.config.FilterPathProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private FilterPathProperties filterPathProperties;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 排除路径检查
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = getTokenFromHeader(request);
        if (token == null) {
            return unauthorizedResponse(exchange, "缺少或无效的授权头信息");
        }

        // 从Redis获取token信息（String类型）
        return reactiveRedisTemplate.opsForValue().get(RedisKey.TOKEN + token)
                .flatMap(tokenJson -> {
                    if (tokenJson == null || tokenJson.isEmpty()) {
                        return unauthorizedResponse(exchange, "无效或已过期的令牌");
                    }
                    // 反序列化为UserInfoByToken对象
                    // UserInfoByToken userInfo = JsonUtil.getObject(tokenJson, UserInfoByToken.class);

                    // 验证token是否过期（可选）
                    // if (userInfo.getTokenCreatTime().plusDays(7).isBefore(LocalDateTime.now())) {
                    //     return unauthorizedResponse(exchange, "Token expired");
                    // }

                    // 转换为JSON并添加到请求头
                    // String userInfoJson = JsonUtil.getJson(userInfo);
                    ServerHttpRequest mutatedRequest = request.mutate()
                            .header(GatewayRequestHeader.USER_CONTEXT_HEADER_NAME, tokenJson)
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());

                })
                .onErrorResume(e -> unauthorizedResponse(exchange, "认证失败: " + e.getMessage()))
                .switchIfEmpty(unauthorizedResponse(exchange, "无效或已过期的令牌"));
    }

    private String getTokenFromHeader(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        if (!CollectionUtils.isEmpty(headers)) {
            String authHeader = headers.get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring("Bearer ".length());
            }
        }
        return null;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("X-Auth-Error", message);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private boolean isExclude(String path) {
        return filterPathProperties.getExcludePath().stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
