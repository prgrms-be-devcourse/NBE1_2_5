package hello.gccoffee.service;



import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;
import hello.gccoffee.entity.Product;
import hello.gccoffee.repository.ProductRepository;

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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository; //문제점1

    // orderId(혹은 order)에 해당하는 상품들 조회
    public List<OrderItemDTO> getAllItems(int orderId) {

        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId).orElseThrow(OrderException.NOT_FOUND_ORDER_ID::get);
        log.info("orderItemList: " + orderItemList);

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
    public OrderItemDTO modify(OrderItemDTO orderItemDTO, List<Order> order, int orderItemId) {
        // 1개의 order 찾기
        List<Order> collectOrderItemId = order.stream().filter(i -> i.getOrderId() == orderItemId).toList();
        Order foundOrder = collectOrderItemId.get(0);

        //수정할 주문상세 페이지 찾기
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(foundOrder.getOrderId())
                .orElseThrow(OrderException.NOT_FOUND_ORDER_ID::get);
        List<OrderItem> collect = orderItemList.stream().filter(i -> i.getOrderItemId()==orderItemId).toList();
        OrderItem orderItem = collect.get(0);

        //상품찾기
        Product foundProduct = productRepository.findByProductName(orderItemDTO.getProductName());

        //수정
        orderItem.changeProduct(foundProduct);
        orderItem.changeOrder(foundOrder);
        orderItem.changeCategory(orderItemDTO.getCategory());
        orderItem.changeQuantity(orderItemDTO.getQuantity());
        orderItem.changePrice(orderItemDTO.getPrice());
        orderItemRepository.save(orderItem);

        return new OrderItemDTO(orderItem);
    }

    public List<OrderItem> addItems(Order order, List<OrderItemDTO> items) {

            try {
                List<OrderItem> orderItemList = new ArrayList<>();
                for (OrderItemDTO item : items) {
                //문제1 productName을 productId로 변환하기 위해 productRepository에 의존하는 게 맞는가?
                String productName = item.getProductName();

                Product product = productRepository.findByProductName(productName);
                //상품 확인 절차
                if (product == null) throw OrderException.BAD_RESOURCE.get();
                if(product.getPrice()!=item.getPrice())throw OrderException.BAD_RESOURCE.get();
                int productId = product.getProductId();

                OrderItem orderItem = item.toEntity(productId, order.getOrderId());
                //회원정보 확인 절차
                if(!orderItem.getOrder().getEmail().equals(order.getEmail()))throw OrderException.WRONG_ORDER_IN_ITEM_LIST.get();
                if(!orderItem.getOrder().getPostcode().equals(order.getPostcode()))throw OrderException.WRONG_ORDER_IN_ITEM_LIST.get();
                if(!orderItem.getOrder().getAddress().equals(order.getAddress()))throw OrderException.WRONG_ORDER_IN_ITEM_LIST.get();
                //가격
                orderItemRepository.save(orderItem);
                order.addOrderItems(orderItem);
                }
                order.changeOrderEnum(OrderEnum.ORDER_ACCEPTED);
                return orderItemList;

    }

}
