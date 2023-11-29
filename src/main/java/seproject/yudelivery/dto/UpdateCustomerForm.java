package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCustomerForm {

    private Long id;
    private String nickName;
    private String address;
    private String phone;
    private String password;
    private String email;

}
