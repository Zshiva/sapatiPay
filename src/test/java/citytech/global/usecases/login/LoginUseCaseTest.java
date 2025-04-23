package citytech.global.usecases.login;

import citytech.global.platform.constants.Roles;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.platform.rest.RestResponse;
import citytech.global.platform.utils.securityutils.jwt.TokenService;
import citytech.global.repository.user.UserEntity;
import citytech.global.repository.user.UserRepository;
import citytech.global.usecases.register.RegisterUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginUseCaseTest {
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
    }
    @Mock
    TokenService tokenService;
    @InjectMocks
    LoginUseCase loginUseCase;

    @Test
    void shouldTestPassIfLoginIsValid() {
        UserEntity user = new UserEntity();
        user.setId("abc6781O");
        user.setEmailId("test@gmail.com");
        user.setPassword("password123");
        user.setRoles(Roles.LENDER);

        Mockito.when(userRepository.findByEmailId("test@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(tokenService.generateToken("abc6781O", "test@gmail.com", Roles.LENDER.name())).thenReturn("token123123asdkas");

        LoginUseCaseRequest request = new LoginUseCaseRequest("test@gmail.com", "password123");
        Optional<LoginUseCaseResponse> response = loginUseCase.exceute(request);

        assertTrue(response.isPresent());
        assertEquals(Roles.LENDER + RestResponse.success().message(), response.get().message());
        assertEquals("token123123asdkas", response.get().token());
    }

    @Test
    void shouldTestPassIfPasswordIsInvalid() {
        UserEntity user = new UserEntity();
        user.setPassword("wrongPass");
        Mockito.when(userRepository.findByEmailId("test@gmail.com")).thenReturn(Optional.of(user));
        LoginUseCaseRequest request = new LoginUseCaseRequest("test@gmail.com", "password123");
        assertThrows(SapatiPayException.class, () -> {
            loginUseCase.exceute(request);
        });
    }

    @Test
    void shouldTestPassIfUserNotFound() {
        Mockito.when(userRepository.findByEmailId("shiva@gmail.com")).thenReturn(Optional.empty());
        LoginUseCaseRequest request = new LoginUseCaseRequest("shiva@gmail.com", "password123");
        assertThrows(SapatiPayException.class, () -> {
            loginUseCase.exceute(request);
        });
    }

    @AfterEach
    void tearDown(){
        userRepository = null;
    }
}