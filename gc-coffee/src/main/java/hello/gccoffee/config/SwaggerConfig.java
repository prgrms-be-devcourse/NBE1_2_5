package hello.gccoffee.config;

import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.swagger.ExampleHolder;
import hello.gccoffee.swagger.OrderExceptionCode;
import hello.gccoffee.swagger.ProductExceptionCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Log4j2
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
                .description("Code5팀의 카페 온라인 웹 사이트 커피 주문/관리 API")
                .version("1.0.0");
    }

    // 예외 응답 예시 생성
    @Bean
    public OperationCustomizer customizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            OrderExceptionCode orderExceptionCode = handlerMethod.getMethodAnnotation(OrderExceptionCode.class);

            if (orderExceptionCode != null) { // @OrderApiErrorCode가 붙어있는 경우
                generateOrderExceptionResponseExample(operation, orderExceptionCode.value());
            }

            ProductExceptionCode productExceptionCode = handlerMethod.getMethodAnnotation(ProductExceptionCode.class);
            if (productExceptionCode != null) { // @ProductApiErrorCode가 붙어있는 경우
                generateProductErrorResponseExample(operation, productExceptionCode.value());
            }
            return operation;
        };
    }

    // 여러 개의 예외 응답값 추가
    private void generateOrderExceptionResponseExample(Operation operation, OrderException[] orderExceptions) {
        ApiResponses responses = operation.getResponses();

        // 에외 응답값 객체 ExampleHolder를 만들어 상태 코드별로 그룹화
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(orderExceptions)
                .map(
                        orderException -> ExampleHolder.builder()
                                .holder(getSwaggerOrderExample(orderException))
                                .code(orderException.get().getCode()) // 바꿈
                                .name(orderException.get().getMessage()) // 바꿈
                                .build()
                )
                .collect(Collectors.groupingBy(ExampleHolder::getCode));
        log.info(statusWithExampleHolders);
        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private void generateProductErrorResponseExample(Operation operation, ProductException[] productExceptions) {
        ApiResponses responses = operation.getResponses();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(productExceptions)
                .map(
                        productException -> ExampleHolder.builder()
                                .holder(getSwaggerProductExample(productException))
                                .code(productException.get().getCode())
                                .name(productException.get().getMessage())
                                .build()
                )
                .collect(Collectors.groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    // Swagger의 exception response 예시 생성
    private Example getSwaggerOrderExample(OrderException orderException) {
        Example example = new Example();
        Map<String, String> errMap = Map.of(
                "result", "error",
                "message", orderException.get().getMessage());

        if (errMap.containsKey("result")) { // 다른 형태의 반환값
            errMap = Map.of("error", orderException.get().getMessage());
        }

        example.setValue(errMap);
        return example;
    }

    private Example getSwaggerProductExample(ProductException productException) {
        Example example = new Example();
        Map<String, String> errMap = Map.of(
                "result", "error",
                "message", productException.get().getMessage());

        if (errMap.containsKey("result")) { // 다른 형태의 반환값
            errMap = Map.of("error", productException.get().getMessage());
        }

        example.setValue(errMap);
        return example;
    }

    private void addExamplesToResponses(ApiResponses responses,
                                        Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();

                    v.forEach(
                            exampleHolder -> mediaType.addExamples(
                                    exampleHolder.getName(),
                                    exampleHolder.getHolder()
                            )
                    );
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(String.valueOf(status), apiResponse);
                }
        );
    }
}
