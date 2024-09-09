package hello.gccoffee.service;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.repository.OrderItemRepository;
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

    public List<OrderItem> addItems(Order order, List<OrderItemDTO> items) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDTO item : items) {
            String productName = item.getProductName();
            int productId = 0;//productName -> productId 반환 메서드 넣어주기
            OrderItem orderItem = item.toEntity(productId, order.getOrderId());
            orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
}
