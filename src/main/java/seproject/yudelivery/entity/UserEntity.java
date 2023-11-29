package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import seproject.yudelivery.dto.UserRole;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    private UserRole role;

}
