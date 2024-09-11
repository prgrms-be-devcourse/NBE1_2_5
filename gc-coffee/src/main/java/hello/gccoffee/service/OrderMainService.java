package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderMainService {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    // 주문 조회
    public List<OrderItemDTO> readOrder(String email) {
        log.info("OrderMainService ===> readOrder() ");

        OrderDTO orderDTO = orderService.findByEmail(email);
        log.info("orderMainservice ===> findByEmail() : " + orderDTO);

        log.info("orderMainservice ===> getAllItems " + orderItemService.getAllItems(orderDTO.getOrderId()));
        return orderItemService.getAllItems(orderDTO.getOrderId());
    }

    // 주문 삭제
    public void removeOrder(String email) {
        log.info("OrderMainService ===> removeOrder() ");
        List<Order> orders = orderService.findAllByEmail(email);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("not found email " + email);
        }

        for (Order order : orders) {
            orderItemService.deleteAllItems(order.getOrderId());
        }

        orderService.deleteOrder(email);
    }
}
