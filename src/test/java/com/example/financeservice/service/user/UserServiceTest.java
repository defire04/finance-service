package com.example.financeservice.service.user;

import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.user.UserRepository;
import com.example.financeservice.service.user.imp.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Test findByUsername")
    void testFindByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername(username);

        assertEquals(user, result.orElse(null), "User should be found by username");
    }

    @Test
    @DisplayName("Test findByUsername with non-existing username")
    void testFindByUsername_NonExistingUsername() {
        String username = "nonExistingUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername(username);

        assertEquals(Optional.empty(), result, "User should not be found for non-existing username");
    }
}