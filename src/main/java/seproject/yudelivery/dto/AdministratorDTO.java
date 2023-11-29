package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import seproject.yudelivery.entity.*;
import seproject.yudelivery.repository.AdministratorRepository;
import seproject.yudelivery.repository.StoreRepository;

import java.sql.Date;

@AllArgsConstructor
@Getter @Setter
public class AdministratorDTO {
    private Long id;
    private String content;

    public AdministratorEntity toEntity() {
        return AdministratorEntity.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }

    @Override
    public String toString() {
        return "AdministratorDTO{" +
                "id='" + id + '\'' +
                ", content=" + content +
                '}';
    }
}
