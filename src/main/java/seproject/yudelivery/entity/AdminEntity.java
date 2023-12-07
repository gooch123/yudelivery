package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminEntity {
    @Id
    @Column(name = "review_id") // 리뷰의 id 외래키
    private Long id;

    @Column(name = "content")
    private String content;

//    @Override
//    public boolean equals(Object obj) {
//        AdminEntity adminEntity = (AdminEntity) obj;
//        if(this.id.equals(adminEntity.id) && this.content.equals(adminEntity.content)) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int hashCode = 1;
//
//        hashCode = prime * hashCode + ((content == null) ? 0 : content.hashCode());
//        hashCode = prime * hashCode + (int)id.longValue();
//
//        return hashCode;
//    }
}
