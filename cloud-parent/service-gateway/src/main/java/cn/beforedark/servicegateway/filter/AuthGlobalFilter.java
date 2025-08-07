package cn.beforedark.servicegateway.filter;

import cn.beforedark.common.config.redis.RedisKey;
import cn.beforedark.common.model.response.R;
import cn.beforedark.common.util.JsonUtil;
import cn.beforedark.servicegateway.config.FilterPathProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private FilterPathProperties filterPathProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 免检路径放行
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        // 获取token
        String token;
        String headerValue = request.getHeaders().getFirst("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            token = headerValue.substring(7);
        } else {
            log.info("未找到Authorization Header");
            return unauthorized(exchange);
        }

        // 鉴权
        String tokenValue = redisTemplate.opsForValue().get(RedisKey.TOKEN + token);
        // log.info("redis key is {}", RedisKey.TOKEN + token);

        if (StringUtils.hasText(tokenValue)) {
            log.info("token is {}有效, tokenValue is {}", token, tokenValue);
            return chain.filter(exchange); // 放行
        } else {
            // 如果无效，拦截
            log.info("token is {}无效, tokenValue is {}", token, tokenValue);
            return unauthorized(exchange);
        }

    }
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
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
        return filterPathProperties.getLoginCheckExcludePath().stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
