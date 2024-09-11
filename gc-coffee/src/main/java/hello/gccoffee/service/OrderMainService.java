package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.repository.OrderItemRepository;
import hello.gccoffee.repository.OrderRepository;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderMainService {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 주문 조회
    public List<OrderItemDTO> readOrder(String email) {
        log.info("OrderMainService ===> readOrder() ");

        OrderDTO orderDTO = orderService.findByEmail(email);
        log.info("orderMainservice ===> findByEmail() : " + orderDTO);

        return orderItemService.getAllItems(orderDTO.getOrderId());
    }

    // 주문 주문자 전체 삭제
    public void removeAll(String email) {
        log.info("OrderMainService ===> removeAll() ");
        List<Order> orders = orderService.findAllByEmail(email);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("not found email " + email);
        }

        for (Order order : orders) {
            orderItemService.deleteAllItems(order.getOrderId());
        }

        orderService.deleteOrder(email);
    }

    // 주문자의 원하는 주문만 삭제
    public void removeOrder(String email, int orderId, int orderItemId) {
        log.info("OrderMainService ===> removeOrder() ");
        Order order = orderService.getOrders(email, orderId, orderItemId);
        orderItemService.deleteoneTem(email, orderId, orderItemId);
    }

    // 관리자 주문 수정
    public OrderItemDTO modifyOrder(OrderItemDTO orderItemDTO, int orderItemId) {
        List<Order> order = orderService.findEntityByEmail(orderItemDTO.getEmail());
        return orderItemService.modify(orderItemDTO, order, orderItemId);
    }

}
