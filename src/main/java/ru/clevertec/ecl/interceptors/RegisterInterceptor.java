package ru.clevertec.ecl.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RegisterInterceptor implements WebMvcConfigurer {

    private final RequestProcessing requestProcessing;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestProcessing).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.index.html/**");
    }
}
