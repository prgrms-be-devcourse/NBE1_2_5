package hello.gccoffee.service;


import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.OrderItemException;
import hello.gccoffee.exception.OrderTaskException;
import hello.gccoffee.repository.OrderItemRepository;
import hello.gccoffee.repository.OrderRepository;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    //모든 주문 목록 조회
    public List<OrderItemDTO> getAllOrders() {
        log.info("OrderItemService ===> getAllOrders() ");
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

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
    public OrderItemDTO modify(OrderItemDTO orderItemDTO, int orderItemId) {

        //수정할 OrderItem 찾기
        OrderItem foundOrderItem = orderItemRepository.findById(orderItemId).orElseThrow(OrderItemException.NOT_FOUND::get);

        //OrderItem 수정
        foundOrderItem.changeProduct(foundOrderItem.getProduct());
        foundOrderItem.changeCategory(orderItemDTO.getCategory());
        foundOrderItem.changeOrder(foundOrderItem.getOrder());
        foundOrderItem.changeQuantity(orderItemDTO.getQuantity());
        foundOrderItem.changePrice(foundOrderItem.getProduct().getPrice() * orderItemDTO.getQuantity());
        orderItemRepository.save(foundOrderItem);

        return new OrderItemDTO(foundOrderItem);
    }

    public List<OrderItem> addItems(Order order, List<OrderItemDTO> items) {
        if (!order.getOrderItems().isEmpty()) {
            throw OrderException.ORDER_LIST_EXIST.get();
        }

        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDTO item : items) {
            //문제1 productName을 productId로 변환하기 위해 productRepository에 의존하는 게 맞는가?
            String productName = item.getProductName();

            Product product = productRepository.findByProductName(productName);
            //상품 확인 절차
            if (product == null) throw OrderException.BAD_RESOURCE.get();
            if (product.getPrice() != item.getPrice()) throw OrderException.BAD_RESOURCE.get();
            int productId = product.getProductId();

            OrderItem orderItem = item.toEntity(productId, order.getOrderId());
            //회원정보 확인 절차
            if (!orderItem.getOrder().getEmail().equals(order.getEmail()))
                throw OrderException.WRONG_ORDER_IN_ITEM_LIST.get();
            if (!orderItem.getOrder().getPostcode().equals(order.getPostcode()))
                throw OrderException.WRONG_ORDER_IN_ITEM_LIST.get();
            if (!orderItem.getOrder().getAddress().equals(order.getAddress()))
                throw OrderException.WRONG_ORDER_IN_ITEM_LIST.get();
            //가격
            orderItemRepository.save(orderItem);
            order.addOrderItems(orderItem);
        }
        return orderItemList;
    }

    public boolean deleteAllByOrderId(int orderId) {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId).orElseThrow(OrderException.ORDER_ITEM_NOT_FOUND::get);
        try {
            for (OrderItem orderItem : orderItemList) {
                orderItemRepository.delete(orderItem);
            }
            return true;
        } catch (OrderTaskException e) {
            return false;
        } catch (Exception e) {
            return orderItemList.isEmpty();
        }
    }

    public Integer deleteOneItem(OrderItemDTO orderItemDTO, int orderId) {
        try {
            Product product = productRepository.findByProductName(orderItemDTO.getProductName());

            int deletedCount = orderItemRepository.deleteByProductNameAndCategoryAndPrice(
                    product.getPrice(),
                    orderItemDTO.getCategory(),
                    orderItemDTO.getProductName()
            );

            if (deletedCount == 0) {
                throw OrderException.NOT_DELETE_ITEM.get();
            }

            return product.getProductId();
        } catch (OrderTaskException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllItems(int orderId) {
        orderItemRepository.deleteByOrderOrderId(orderId);
    }

    public void deleteoneTem(String email, int orderId, int orderItemId) {
        orderItemRepository.deleteByEmailAndOrderIdAndOrderItemId(email, orderId, orderItemId);
    }

    // 해당 OrderItem을 새로운 내역으로 수정
    public OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemDTO.getOrderItemId())
                .orElseThrow(OrderException.NOT_FOUND_ORDER_ITEM::get);

        Product foundProduct = productRepository.findByProductName(orderItemDTO.getProductName());
        if (foundProduct == null) throw OrderException.BAD_RESOURCE.get();

        // 수량이 0인 경우 예외 -> OrderItem 삭제 기능 사용하도록 유도
        if (orderItemDTO.getQuantity() == 0) {
            throw OrderException.INVALID_ORDER_ITEM_QUANTITY.get();
        }

        try {
            orderItem.changeProduct(foundProduct);
            orderItem.changeCategory(foundProduct.getCategory());
            orderItem.changePrice(foundProduct.getPrice());
            orderItem.changeQuantity(orderItemDTO.getQuantity());
            return new OrderItemDTO(orderItem);
        } catch (OrderTaskException e) {
            throw OrderException.ORDER_ITEM_NOT_UPDATED.get();
        }

    }
}

