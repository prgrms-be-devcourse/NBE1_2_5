package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.OrderTaskException;
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

    public OrderDTO addOrders(OrderDTO orderDTO) {
        try {
            Order order = orderDTO.toEntity();
            orderRepository.save(order);
            return new OrderDTO(order);
        } catch (OrderTaskException e) {
            throw OrderException.ORDER_NOT_REGISTERED.get();
        }
    }

    public Order findById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderException.NOT_FOUND_ORDER::get);
    }

    public boolean deleteOneOrderOfOne(int OrderId) {
        try {
            Order order = orderRepository.findById(OrderId).orElseThrow(OrderException.NOT_FOUND_ORDER::get);
            orderRepository.delete(order);
            return true;
        } catch (OrderTaskException e) {
            return false;
        } catch (Exception e) {
            return orderRepository.findById(OrderId).orElse(null) == null;
        }

    }

    public boolean deleteOneItemInOrder(OrderItemDTO orderItemDTO,int productId, int orderId){
        Order byIdOrder = findById(orderId);
        OrderItem orderItem = orderItemDTO.toEntity(productId, orderId);
        if (byIdOrder.getOrderItems().contains(orderItem)) {
            byIdOrder.getOrderItems().remove(orderItem);
            return true;
        }
        return false;
    }


    public boolean deleteAllOrderOfOne(String email) {
        List<Order> allByEmail = orderRepository.findAllByEmail(email);
        try {
            if (allByEmail.isEmpty()) return true;
            for (Order order : allByEmail) {
                orderRepository.delete(order);
            }
            return true;
        } catch (Exception e) {
            return allByEmail.isEmpty();
        }

    }

    public List<Order> findAllByEmail(String email){
        return orderRepository.findAllByEmail(email);
    }

    public List<Integer> findOrderIdsByEmail(String email) {
        log.info("문제지점2-1");
        return orderRepository.findOrderIdByEmail(email);
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
