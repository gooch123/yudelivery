package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.JoinRepuest;
import seproject.yudelivery.dto.LoginRequest;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // login id 중복체크
    public boolean checkLoginIdDuple(String userId) {
        return userRepository.existsByUserId(userId);
    }

    //nickname 중복체크
    public boolean checkNicknameDuple(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    //회원가입기능. JoinRequest(userId, password, nickname 등)을 입력받아 UserEnity로 받아 저장
    //아이디와 닉네임 중복체크는 컨트롤러에서 진행 > 에러메세지 출력을 위해
    public void join(JoinRepuest req) {
        userRepository.save(req.toEntity());
    }

    //로그인기능 LoginRequest를 입력받아 userID와 password가 일치하면 UserEntity return
    //아이디가 존재하지않거나 비밀번호 틀릴 시 null return


    public UserEntity Login(LoginRequest req) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(req.getUserId());

        if (optionalUserEntity.isEmpty()) {
            return null;
        }

        UserEntity userEntity = optionalUserEntity.get();
        //찾아온 User의 패스워드와 입력된 패스워드가 다르다면 null return
        if(!userEntity.getPassword().equals(req.getPassword())) {
            return null;
        }

        return userEntity;
    }


}
