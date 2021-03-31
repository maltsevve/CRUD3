package com.maltsevve.service;

import com.maltsevve.crud3.model.Role;
import com.maltsevve.crud3.model.User;
import com.maltsevve.crud3.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    User user = buildUser();

    @Mock
    UserService userService = mock(UserService.class);

    @Test
    public void saveTest() {
        lenient().when(userService.save(user)).thenReturn(user);
    }

    @Test
    public void updateTest() {
        lenient().when(userService.save(user)).thenReturn(user);
    }

    @Test
    public void getByIdTest() {
        user.setId(1L);
        lenient().when(userService.getById(1L)).thenReturn(user);
    }

    @Test
    public void getAllTest() {
        List<User> users = new ArrayList<>();
        user.setId(1L);
        users.add(user);
        lenient().when(userService.getAll()).thenReturn(users);
    }

    @Test
    public void deleteByIdTest() {
        userService.deleteById(1L);
        verify(userService, times(1)).deleteById(1L);
    }

    public User buildUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(Role.USER);
        return user;
    }
}
