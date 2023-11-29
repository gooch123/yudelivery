package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import seproject.yudelivery.entity.*;

@AllArgsConstructor
@Getter @Setter
public class AdminDTO {
    private Long id;
    private String content;

    public AdminEntity toEntity() {
        return AdminEntity.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "id='" + id + '\'' +
                ", content=" + content +
                '}';
    }
}
