package seproject.yudelivery.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import seproject.yudelivery.entity.UserEntity;

@Data
@AllArgsConstructor
public class JoinRepuest {
    @NotBlank(message = " = ")
    private String userId;
    @NotBlank(message = " = ")
    private String password;
    @NotBlank(message =  " = ")
    private String nickname;
    private String username;
    private String email;
    private String phone;
    private UserRole userRole;


    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(this.userId)
                .username(this.username)
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .phone(this.phone)
                .role(this.userRole)
                .build();
    }
}
