package cn.beforedark.servicegateway.filter;

import cn.beforedark.common.config.jwt.JwtProperties;
import cn.beforedark.common.model.gateway.GatewayRequestHeader;
import cn.beforedark.common.model.response.R;
import cn.beforedark.common.util.JsonUtil;
import cn.beforedark.common.util.JwtRsaUtil;
import cn.beforedark.servicegateway.config.FilterPathProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private FilterPathProperties filterPathProperties;
    @Autowired
    private JwtProperties jwtProperties;

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

        // 使用 JwtRsaUtil 验证 token 并获取 claims
        try {
            Map<String, Object> claims = JwtRsaUtil.parseToken(token, jwtProperties.getPublicKey());
            // 将 claims 转换为 JSON 字符串
            String userInfoJson = JsonUtil.getJson(claims);

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header(GatewayRequestHeader.USER_CONTEXT_HEADER_NAME, userInfoJson)
                    .build();
            log.info("用户信息: {}", userInfoJson);
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            return unauthorizedResponse(exchange, "认证失败: " + e.getMessage());
        }
    }

    private String getTokenFromHeader(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = JsonUtil.getJson(R.authError());
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
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
