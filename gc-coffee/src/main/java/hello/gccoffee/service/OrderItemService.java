package hello.gccoffee.service;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.OrderItemRepository;
import hello.gccoffee.repository.OrderRepository;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // orderId(혹은 order)에 해당하는 상품들 조회
    public List<OrderItemDTO> getAllItems(int orderId) {

        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId).orElseThrow(OrderException.NOT_FOUND_ORDERID::get);

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        if (orderItemList == null) {
            return orderItemDTOS;
        }
        orderItemList.forEach(orderItem -> {
            orderItemDTOS.add(OrderItemDTO.builder()
                    .email(orderItem.getOrder().getEmail())
                    .address(orderItem.getOrder().getAddress())
                    .postcode(orderItem.getOrder().getPostcode())
                    .productName(orderItem.getProduct().getProductName())
                    .price(orderItem.getPrice())
                    .quantity(orderItem.getQuantity())
                    .category(orderItem.getCategory())
                    .build());
        });
        return orderItemDTOS;
    }
    //관리자 주문 수정
    public OrderItemDTO modify(OrderItemDTO orderItemDTO, Order order, int orderItemId) {

        //수정할 주문상세 페이지 찾기
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(order.getOrderId())
                .orElseThrow(OrderException.NOT_FOUND_ORDERID::get);
        List<OrderItem> collect = orderItemList.stream().filter(i -> i.getOrderItemId()==orderItemId).toList();
        OrderItem orderItem = collect.get(0);

        //상품찾기
        Product foundProduct = productRepository.findByProductName(orderItemDTO.getProductName())
                .orElseThrow(ProductException.NOT_FOUND::get);

        //수정
        orderItem.changeProduct(foundProduct);
        orderItem.changeOrder(order);
        orderItem.changeCategory(orderItemDTO.getCategory());
        orderItem.changeQuantity(orderItemDTO.getQuantity());
        orderItem.changePrice(orderItemDTO.getPrice());
        orderItemRepository.save(orderItem);

        return new OrderItemDTO(orderItem);
    }
}
