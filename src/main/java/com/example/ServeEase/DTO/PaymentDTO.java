package com.example.ServeEase.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    float amount;
    Long bookingId;

}
