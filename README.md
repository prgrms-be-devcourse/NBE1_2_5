# 커피 메뉴 관리 API

## 소개

**Grids & Circles**라는 작은 로컬 카페를 위한 **커피 메뉴 관리 API**입니다.  
고객들은 웹 사이트를 통해 **Coffee Bean package**를 온라인으로 주문할 수 있습니다.  
매일 전날 오후 2시부터 오늘 오후 2시까지의 주문을 수집하고 처리하는 기능을 제공하여 주문을 효율적으로 관리하고 고객에게 원활한 서비스를 제공할 수 있습니다.

## 기술 스택 (Tech Stack)

- **Java 17**: 메인 프로그래밍 언어
- **Spring Boot 3.3.3**: 웹 애플리케이션 프레임워크
    - **Spring Boot Starter Web**: 웹 애플리케이션 개발
    - **Spring Boot Starter Data JPA**: JPA를 사용한 데이터베이스 연동
    - **Spring Boot Starter Validation**: 데이터 유효성 검사
- **MySQL**: 데이터베이스 (JDBC 연결)
- **QueryDSL**: 쿼리 작성을 위한 라이브러리
- **Lombok**: 코드 간결화를 위한 라이브러리
- **Swagger (SpringDoc)**: API 문서화 및 테스트 도구
- **JUnit**: 테스트 프레임워크
- **Spring Boot DevTools**: 개발 편의성 향상 도구
- **Maven Central**: 의존성 관리를 위한 리포지토리
- **Jira**: 프로젝트 일정 관리 도구
- **Slack**: 팀 소통 및 협업 도구

## Swagger 사용 방법

**Swagger (SpringDoc)** 를 사용하여 API 문서화 및 테스트를 지원합니다.

### 접근 방법

1. 서버를 실행한 후, 웹 브라우저를 열고 아래 주소로 이동합니다.
    - http://localhost:8050/swagger-ui/index.html
2. API 엔드포인트 목록을 확인하고, 각 엔드포인트의 세부 사항을 볼 수 있습니다.
3. 각 API 엔드포인트를 선택하면, 요청 파라미터를 입력하고 요청을 실행하여 응답을 확인할 수 있습니다.

이 기능을 사용하여 API의 동작을 시연하고, 요청 및 응답을 실시간으로 테스트할 수 있습니다.

## Project Structure

- **gccoffee**
    - **config**
        - `SwaggerConfig`: Swagger 설정.
    - **controller**
        - `OrderApiController`: 고객용 컨트롤러. 주문 요청 처리.
        - `AdminApiController`: 관리자용 컨트롤러. 상품과 주문 요청을 처리.
        - `advice`: 컨트롤러의 예외 처리를 위한 Advice 클래스 포함.
    - **dto**
        - 엔티티 객체를 담는 데이터 전송 객체(DTO)를 포함.
    - **entity**
        - `Product`: 상품 정보를 담고 있는 엔티티.
        - `Order`: 주문 정보를 담고 있는 엔티티.
        - `OrderItem`: 주문 내역 정보를 담고 있는 엔티티.
    - **exception**
        - `AdminAuthenticationException`: 관리자 인증 관련 예외 처리.
        - `OrderException`: 주문 관련 예외 처리.
        - `ProductException`: 상품 관련 예외 처리.
    - **repository**
        - `ProductRepository`: 상품 데이터 접근을 위한 리포지토리.
        - `OrderRepository`: 주문 데이터 접근을 위한 리포지토리.
        - `OrderItemRepository`: 주문 내역 데이터 접근을 위한 리포지토리.
    - **service**
        - `ProductService`: 상품 요청을 처리하는 서비스.
        - `OrderMainService`: 주문 요청에 따라 `OrderService`와 `OrderItemService`로부터 데이터를 받아 처리하고, 컨트롤러에 반환하는 서비스.
        - `OrderService`: 주문 요청을 처리하는 서비스.
        - `OrderItemService`: 주문 내역 요청을 처리하는 서비스.
    - **swagger**
        - 각 컨트롤러의 기능에 대한 API 문서 파일.
