package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.OrderTaskException;
import hello.gccoffee.repository.OrderItemRepository;
import hello.gccoffee.repository.OrderRepository;
import hello.gccoffee.entity.Order;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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


    //주문 목록 조회
    public List<OrderItemDTO> getList() {
        log.info("OrderMainService ===> getList() ");
        return orderItemService.getAllOrders();

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
    public OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO, int orderItemId) {
        return orderItemService.modify(orderItemDTO, orderItemId);
    }

    // 주문 조회 창에서 개별 주문 수정
    public OrderItemDTO updateOrderItemInOrder(OrderItemDTO orderItemDTO) {
        // 해당 주문 번호와 내용을 orderItemService에 넘겨서 수정
        // 트랜잭션 완료 시 Order 객체의 orderList에도 자동으로 반영
        return orderItemService.updateOrderItem(orderItemDTO);
    }

    public void removeOrder(Integer orderId, String email) {
        // 확인 절차
        List<Integer> orderIdsByEmail = orderService.findOrderIdsByEmail(email);
        if (orderIdsByEmail.contains(orderId)) {
            // 삭제 로직
            try {
                orderItemService.deleteAllByOrderId(orderId);
                orderService.deleteOneOrderOfOne(orderId);
            } catch (OrderTaskException e) {
                // 실패 반환
                throw OrderException.ORDER_NOT_REMOVED.get();
            }
        } else {
            //주문번호가 해당 이메일이 아니면 실패 반환
            throw OrderException.MISSING_EMAIL.get();
        }
    }

    public List<Integer> removeAllOrder(String email) {
        try {
            List<Integer> orderIdsByEmail = orderService.findOrderIdsByEmail(email);
            for (Integer orderId : orderIdsByEmail) {
                orderItemService.deleteAllByOrderId(orderId);
            }
            orderService.deleteAllOrderOfOne(email);
            return orderIdsByEmail;
        } catch (OrderTaskException e) {
            throw OrderException.ORDER_NOT_REMOVED.get();
        }
    }

    public OrderDTO addOrders(OrderDTO orderDTO) {
        return orderService.addOrders(orderDTO);
    }

//    public Order addOrderItems(Integer orderId, List<OrderItemDTO> items) {
//        Order findOrder = orderService.findById(orderId);
//
//        List<OrderItem> orderItemList = orderItemService.addItems(findOrder, items);
//        return
//    }
}
