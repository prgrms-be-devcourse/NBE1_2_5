package hello.gccoffee.service;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
