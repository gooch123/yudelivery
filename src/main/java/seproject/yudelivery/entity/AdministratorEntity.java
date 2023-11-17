package seproject.yudelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AdministratorEntity {
    @Id @GeneratedValue
    @Column(name = "report_target_id") // 리뷰의 id 외래키
    private Long id;

    @Column(name = "content")
    private String content;
}
