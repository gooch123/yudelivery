package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import seproject.yudelivery.dto.UpdateCustomerForm;

import java.sql.Date;

@Entity
@Getter @Setter
@DiscriminatorValue(value = "Customer")
@ToString
public class CustomerEntity extends UserEntity{

    @Column(name = "customer_address")
    private String customer_address;

    @Column(name = "customer_birthdate")
    private Date customer_birthdate;

    public void update(UpdateCustomerForm form){
        setNickname(form.getNickName());
        setPhone(form.getPhone());
        setCustomer_address(form.getAddress());
        setPassword(form.getPassword());
    }
}
