package hello.gccoffee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public class OrderDTO {
    @Email
    private String email;
    @NotBlank
    private String postcode;
    @NotBlank
    private String address;




}
