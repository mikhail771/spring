package spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.UserResponseDto;
import spring.model.User;
import spring.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.listUsers()
                .stream()
                .map(this::getMappedUserToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/inject")
    public void injectUsers() {
        for (int i = 0; i < 4; i++) {
            userService.add(new User("user" + i, "Lastname" + i,
                    "user" + i + "gmail.com", "password" + i));
        }
    }

    @GetMapping(value = "/{id}")
    public UserResponseDto getUser(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        return getMappedUserToDto(user);
    }

    private UserResponseDto getMappedUserToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        dto.setName(user.getFirstName());
        return dto;
    }
}
