package hello.gccoffee.entity;

public enum  OrderEnum {
    ORDER_ACCEPTED,
    // 주문 완료 = 배송 준비 중 -> 관리자 창에서 관리자가 바꿔주는 서비스 필요. , (배송 중 = 배송 완료)
    READY_FOR_DELIVERY,
    SHIPPED
}
