package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements OrderMainService{
    private final OrderRepository orderRepository;

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
        return orderRepository.findById(orderId).orElseThrow();
    }
}
