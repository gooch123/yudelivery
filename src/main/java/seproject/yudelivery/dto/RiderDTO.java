package seproject.yudelivery.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RiderDTO {

    private Long id;

    @NotBlank(message = "Delivery status cannot be blank")
    private String deliveryStatus;

    @NotBlank(message = "Delivery address1 cannot be blank")
    private String deliveryAddress1;

    @NotBlank(message = "Delivery address2 cannot be blank")
    private String deliveryAddress2;

    @NotBlank(message = "Delivery address3 cannot be blank")
    private String deliveryAddress3;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    private double latitude;

    private double longitude;

    // 기타 필요한 필드 추가

    // 생성자, setter, getter 등 필요한 메서드 추가
}
