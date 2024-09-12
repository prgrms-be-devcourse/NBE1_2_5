package hello.gccoffee.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Grids & Circles")
                .description("카페 온라인 웹 사이트 커피 주문/관리 API")
                .version("1.0.0");
    }

    // 설정해놓은 예외처리 코드가 뜨도록 설정하는 작업 필요
//    @Bean
//    public OperationCustomizer customizer() {
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//            ApiErrorCode apiErrorCode =
//        }
//    }
}
