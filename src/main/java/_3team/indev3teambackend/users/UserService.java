package _3team.indev3teambackend.users;

import _3team.indev3teambackend.users.dto.UserCreateRequest;
import _3team.indev3teambackend.users.dto.UserResponse;
import _3team.indev3teambackend.users.dto.UserUpdateRequest;
import _3team.indev3teambackend.users.exception.UserNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: id=" + id));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest req) {
        User user = new User(req.getProviderId(), req.getProviderIdEmail(), req.getName(), req.getProfileImageUrl());
        User saved = userRepository.save(user);
        return UserResponse.from(saved);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest req) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: id=" + id));

        if (req.getProviderIdEmail() != null) {
            if (!req.getProviderIdEmail().equals(user.getProviderIdEmail()) && userRepository.existsByProviderIdEmail(req.getProviderIdEmail())) {
                throw new IllegalArgumentException("providerIdEmail already in use: " + req.getProviderIdEmail());
            }
            user.changeProviderIdEmail(req.getProviderIdEmail());
        }

        if (req.getName() != null) {
            user.changeName(req.getName());
        }

        if (req.getProfileImageUrl() != null) {
            user.changeProfileImageUrl(req.getProfileImageUrl());
        }

        return UserResponse.from(user);
    }
}
