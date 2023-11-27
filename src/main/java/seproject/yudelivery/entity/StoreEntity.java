package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class StoreEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "store_name")
    private String store_name;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "category")
    private String category;

    @Column(name = "phone")
    private String phone;

    @Column(name = "open_time")
    private Time open_time;

    @Column(name = "close_time")
    private Time close_time;
    @Column(name = "deliver_time")
    private int deliver_time;
    @Column(name = "store_info")
    private String store_info;
    @Column(name = "sales")
    private int sales;
}
