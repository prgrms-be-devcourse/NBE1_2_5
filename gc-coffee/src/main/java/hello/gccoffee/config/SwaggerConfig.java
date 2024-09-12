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

    // 예외 응답 작업 중!!
//    @Bean
//    public OperationCustomizer customizer() {
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//            orderApiErrorCode apiErrorCode = handlerMethod.getMethodAnnotation(orderApiErrorCode.class);
//
//            if (apiErrorCode != null) { // @ApiErrorCode가 붙어있는 경우
//                generateErrorCodeResponse(operation, apiErrorCode.orderValue());
//            }
//            return null;
//        };
//    }
//
//    // ResponseCode는 SuccessCode와 ErrorCode의 인터페이스입니다.
//    private void generateResponseCodeResponseExample(Operation operation, OrderException[] orderExceptionResponse) {
//        ApiResponses responses = operation.getResponses();
//
//        // 에러 응답값 객체를 만들어 에러 코드별로 그룹화
//        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(responseCodes)
//                .map(
//                        responseCode -> ExampleHolder.builder()
//                                .holder(getSwaggerExample(responseCode))
//                                .httpStatus(responseCode.getHttpStatus().value())
//                                .name(responseCode.name())
//                                .build()
//                )
//                .collect(Collectors.groupingBy(ExampleHolder::getHttpStatus));
//
//        addExamplesToResponses(responses, statusWithExampleHolders);
//    }
}
