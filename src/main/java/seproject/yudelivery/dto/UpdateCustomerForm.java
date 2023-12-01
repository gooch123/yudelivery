package seproject.yudelivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UpdateCustomerForm {

    private Long id;
    @NotBlank
    private String nickName;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}
