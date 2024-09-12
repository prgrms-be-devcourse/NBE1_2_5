package hello.gccoffee.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

//    @Bean
//    public OperationCustomizer customizer() {
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//            ApiErrorCode apiErrorCode = handlerMethod.getMethodAnnotation(ApiErrorCode.class);
//
//            if (apiErrorCode != null) { // @ApiErrorCode가 붙어있는 경우
//                generateErrorCodeResponse(operation, apiErrorCode.orderValue());
//            }
//            return null;
//        };
//    }
//
//    private void generateErrorCodeResponse(Operation operation, OrderException[] orderExceptions) {
//
//    }
}
