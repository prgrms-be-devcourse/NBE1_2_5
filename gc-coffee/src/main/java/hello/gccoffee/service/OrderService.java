package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.repository.OrderRepository;
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
public class OrderService {
    private final OrderRepository orderRepository;

    // email을 받고, 해당하는 주문의 orderId 반환
    public OrderDTO findByEmail(String email) {
        Order foundOrder = orderRepository.findByEmail(email).orElseThrow(OrderException.NOT_FOUND_ORDER::get);
        return new OrderDTO(foundOrder);
    }
    //Email 엔티티 받기
    public List<Order> findEntityByEmail(String email) {
        return orderRepository.findByEmails(email).orElseThrow(OrderException.NOT_FOUND_ORDER::get);
    }

    public Order addOrders(OrderDTO orderDTO) {
        try {
            Order order = orderDTO.toEntity();
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw OrderException.ORDER_NOT_REGISTERED.get();
        }
    }

    public Order findById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderException.NOT_FOUND_ORDER::get);
    }

    public List<Order> findAllByEmail(String email) {
        List<Order> foundOrders = orderRepository.findAllByEmail(email);
        return foundOrders;
    }

    // 이메일 주문자번호 주문번호로 찾기
    public Order getOrders(String email, int orderId, int orderItemId) {
        return orderRepository.findByEmailAndOrderIdAndOrderItemId(email, orderId, orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with given email, orderId, and orderItemId."));
    }

    public void deleteOrder(String email) {
        orderRepository.deleteByEmail(email);
    }
}
