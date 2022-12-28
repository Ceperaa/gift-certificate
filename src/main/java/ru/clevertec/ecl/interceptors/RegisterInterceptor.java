package ru.clevertec.ecl.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RegisterInterceptor implements WebMvcConfigurer {

    private final EntitiesInterceptor entitiesInterceptor;
    private final OrderInterceptor orderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderInterceptor)
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/swagger-ui/index.html/**",
                        "/api/error/**",
                        "/v1/orders/sequence/**")
                .addPathPatterns(
                        "v1/orders/**");

        registry.addInterceptor(entitiesInterceptor)
                .excludePathPatterns(
                        "/api/swagger-resources/**",
                        "/webjars/**",
                        "/swagger-ui.index.html/**",
                        "/error/**",
                        "/v1/gift-certificates/sequence/**",
                        "/v1/users/sequence/**",
                        "/v1/tags/sequence/**")
                .addPathPatterns(
                        "/v1/tags/**",
                        "/v1/users/**",
                        "/v1/gift-certificates/**");
    }
}
