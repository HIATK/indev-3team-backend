package _3team.indev3teambackend.users.service;

import _3team.indev3teambackend.users.entity.User;
import _3team.indev3teambackend.users.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _3team.indev3teambackend.users.dto.UserCreateRequest;
import _3team.indev3teambackend.users.dto.UserResponse;
import _3team.indev3teambackend.users.dto.UserUpdateRequest;
import _3team.indev3teambackend.users.dto.UserUpdateResponse;
import _3team.indev3teambackend.users.exception.UserNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 정보 조회
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: id=" + id));
        
        return UserResponse.from(user);
    }

    // 신규 사용자 생성
    // 리턴형이 Long인 것은 Response에 담을 id를 반환하기 위함
    @Transactional
    public Long createUser(UserCreateRequest req) {
        User user = new User(req.getProviderId(), req.getProviderIdEmail(), req.getName(), req.getProfileImageUrl());
        User saved = userRepository.save(user);

        return saved.getId();
    }

    // 사용자 정보 수정
    @Transactional
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest req) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: id=" + id));

        // req의 각 필드가 null이 아닐 때만 업데이트
        if (req.getName() != null) {
            user.changeName(req.getName());
        }
        if (req.getProfileImageUrl() != null) {
            user.changeProfileImageUrl(req.getProfileImageUrl());
        }

        return UserUpdateResponse.from(user);
    }

    //애플리케이션 시작할때 라마를 유저1에 생성함
    @PostConstruct
    public void createLlamaBotUser() {
        // providerId를 "llama_bot"으로 가정하여 조회
        if (userRepository.findByProviderId("llama_bot").isEmpty()) {
            User llamaBot = new User(
                    "llama_bot",
                    "llama@ai.com",
                    "Llama 3.1 Bot",
                    "https://example.com/llama_profile.png" // 적절한 이미지 URL
            );
            // ID가 1로 저장되도록 하기 위해, DB의 sequence를 초기화하는 등의 별도 설정이 필요할 수 있습니다.
            // 여기서는 ID 1로 저장된다고 가정하거나, 저장 후 ID를 조회하여 사용합니다.
            userRepository.save(llamaBot);
            System.out.println("Llama 3.1 Bot user created/verified.");
        }
    }
}
