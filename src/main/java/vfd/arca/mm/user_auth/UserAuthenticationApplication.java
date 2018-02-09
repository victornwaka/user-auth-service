package vfd.arca.mm.user_auth;

import com.google.common.base.Predicate;
import groovy.lang.MetaClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
@SpringBootApplication
public class UserAuthenticationApplication extends SpringBootServletInitializer {

	static {
		System.setProperty("javax.net.ssl.keyStore", "/app/kongoauth.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		System.setProperty("javax.net.ssl.trustStore", "/app/kongauth-truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		System.setProperty("javax.net.debug", "ssl");
//		System.setProperty("javax.net.ssl.keyStoreAlias", "tomcat");
	}



	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(paths())
				.build()
				.ignoredParameterTypes(MetaClass.class);

	}

	@Bean
	ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Open Banking API")
				.description("Bank APIs Gateway")
				.termsOfServiceUrl("")
				.license("")
				.licenseUrl("")
				.version("1.0")
				.build();
	}

	//Here is an example where we select any api that matches one of these paths
	private static Predicate<String> paths() {
		return or(
				regex("/v1/.*")
		);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}



//	@Configuration
//	public class RestConfig {
//		@Bean
//		public CorsFilter corsFilter() {
//			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//			CorsConfiguration config = new CorsConfiguration();
//			config.setAllowCredentials(true);
//			config.addAllowedOrigin("*");
//			config.addAllowedHeader("Origin");
//			config.addAllowedHeader("X-Requested-With");
//			config.addAllowedHeader("Content-Type");
//			config.addAllowedHeader("Accept");
//			config.addAllowedHeader("Access-Control-Allow-Headers");
//			config.addAllowedHeader("Access-Control-Request-Method");
//			config.addAllowedHeader("Access-Control-Request-Headers");
//			config.addAllowedHeader("Access-Control-Allow-Origin");
//			config.addAllowedHeader("Authorization");
//			config.addAllowedMethod("OPTIONS");
//			config.addAllowedMethod("GET");
//			config.addAllowedMethod("POST");
//			config.addAllowedMethod("HEAD");
//			config.addAllowedMethod("PUT");
//			config.addAllowedMethod("DELETE");
//			source.registerCorsConfiguration("/**", config);
//			return new CorsFilter(source);
//		}
//	}



}

