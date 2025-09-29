package _3team.indev3teambackend.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _3team.indev3teambackend.users.dto.UserCreateRequest;
import _3team.indev3teambackend.users.dto.UserCreateResponse;
import _3team.indev3teambackend.users.dto.UserResponse;
import _3team.indev3teambackend.users.dto.UserUpdateRequest;
import _3team.indev3teambackend.users.dto.UserUpdateResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { 
        this.userService = userService; 
    }
    
    // 사용자 정보 조회
    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    // 신규 사용자 생성
    // POST /api/users
    @PostMapping
    public ResponseEntity<UserCreateResponse> create(@RequestBody @Valid UserCreateRequest req) {
        Long id = userService.createUser(req);
        //201 및 response 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserCreateResponse(true, id));
    }

    // 사용자 정보 수정
    // PATCH /api/users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequest req) {
        return ResponseEntity.ok(userService.updateUser(id, req));
    }
}
