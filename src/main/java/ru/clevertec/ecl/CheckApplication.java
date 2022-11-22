package ru.clevertec.ecl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CheckApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CheckApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("public-api")
				.apiInfo(testApiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.
				addResourceHandler( "/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
				.resourceChain(false);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController( "/swagger-ui/")
				.setViewName("forward:/swagger-ui/index.html");
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
				.addMapping("/**");
	}

	private ApiInfo testApiInfo() {
		return new ApiInfoBuilder()
				.title("Gift certificate") // Заголовок
				.description("Develop web service for Gift Certificates system with the following entities (many-to-many) \n")
				.version("1.0 version")
				.build();
	}
}
