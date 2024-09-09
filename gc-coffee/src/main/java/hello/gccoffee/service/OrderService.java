package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static hello.gccoffee.entity.OrderEnum.ORDER_ACCEPTED;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements OrderMainService{
    private final OrderRepository orderRepository;

    public Order addOrders(OrderDTO orderDTO) {
        log.info("DTO정보 = {},{},{}",orderDTO.getEmail(),orderDTO.getAddress(),orderDTO.getPostcode());
        Order order = orderDTO.toEntity();

        orderRepository.save(order);
        return order;
    }

    public Order findById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}
