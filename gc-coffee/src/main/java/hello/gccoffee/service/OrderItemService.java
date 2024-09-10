package hello.gccoffee.service;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import hello.gccoffee.repository.OrderItemRepository;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService implements OrderMainService{

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository; //문제점1

    public List<OrderItem> addItems(Order order, List<OrderItemDTO> items) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDTO item : items) {

            //문제1 productName을 productId로 변환하기 위해 productRepository에 의존하는 게 맞는가?
            String productName = item.getProductName();
            Product product = productRepository.findByProductName(productName);
            int productId = product.getProductId();

            OrderItem orderItem = item.toEntity(productId, order.getOrderId());
            //오더 객체가 같은지 검증
            if(orderItem.getOrderItemId()!=order.getOrderId()){
                //오더 객체는 다르고 이메일도 다르다? -> 오더 객체가 다르다면 -> 필요한 부분인가?
                if (!orderItem.getOrder().getEmail().equals(order.getEmail())) {
                    return null;
                }
            }
            orderItemRepository.save(orderItem);
            order.addOrderItem(orderItem);
        }
        return orderItemList;
    }
}
