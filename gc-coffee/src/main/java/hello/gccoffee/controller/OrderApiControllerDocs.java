package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "User", description = "사용자 기능에 대한 API")
public interface OrderApiControllerDocs {

    @Operation
    public ResponseEntity<List<OrderItemDTO>> readOrder(@RequestParam("email") String email);
}

