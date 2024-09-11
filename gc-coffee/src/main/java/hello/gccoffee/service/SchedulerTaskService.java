package hello.gccoffee.service;

import hello.gccoffee.entity.OrderEnum;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class SchedulerTaskService {
    private final OrderRepository orderRepository;

//    @Scheduled(cron = "0 0 14 * * *") // 매일 14시에 실행되는 스케쥴러
    @Scheduled(cron = "0 * * * * *") // 1분 마다 실행되는 스케줄러. 테스트용
    public void updateOrderStatus() {
        int acceptedOrder = orderRepository.countOrderByAccepted(); // 배송되지 않은 주문 수

        // 주문이 들어오지 않은 경우 스케쥴러 작업 조기 종료
        if (acceptedOrder == 0) {
            log.info("Accepted order is 0. Exiting the scheduled task.");
            return;
        }

        int shippedOrder = orderRepository.updateOrderStatus(OrderEnum.SHIPPED); // 배송 상태로 변경된 주문 수
        if (acceptedOrder != shippedOrder) {
            log.error("Failed to update order status.");
        }

        log.info("Successfully updated order status.");
    }
}
