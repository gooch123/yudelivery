package seproject.yudelivery.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import seproject.yudelivery.entity.RiderEntity;

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


}
