package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter @Setter
public class CustomerEntity extends UserEntity{

    @Id @GeneratedValue
    @OneToOne
    @Column(name = "userId")
    private UserEntity id;

    @Column(name = "customer_name")
    private String customer_name;

    @Column(name = "customer_address")
    private String customer_address;

    @Column(name = "customer_birthdate")
    private Date customer_birthdate;
}
