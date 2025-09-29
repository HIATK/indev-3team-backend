package _3team.indev3teambackend.users;

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
}
