
package citytech.global.usecases.register;

import citytech.global.platform.constants.Roles;
import citytech.global.platform.constants.Status;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.repository.user.UserEntity;
import citytech.global.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class RegisterUseCaseTest {
    private UserRepository userRepository;
    private RegisterUseCase registerUseCase;

    @BeforeEach
    void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    void shouldTestPassIfSignUpDetailsAreValid() throws IOException, InterruptedException {
        RegisterUseCaseRequest registerUser = RegisterUseCaseRequestBuilder.builder()
                .name("Shiva")
                .emailId("zshiva5@gmail.com")
                .address("Godawari")
                .citizenShip("src/nepal")
                .availableBalance(1000)
                .roles(Roles.LENDER)
                .accountStatus(Status.INACTIVE)
                .build();
        UserEntity userEntity = new UserEntity();
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userEntity);
            this.registerUseCase.exceute(registerUser);
    }
    @Test
    void shouldPassTestIfEmailIsInValid() {
            RegisterUseCaseRequest registerUser = RegisterUseCaseRequestBuilder.builder()
                    .name("Shiva")
                    .emailId("zshivagmail.com")
                    .address("Godawari")
                    .citizenShip("src/nepal")
                    .availableBalance(1000)
                    .roles(Roles.LENDER)
                    .accountStatus(Status.INACTIVE)
                    .build();
        Assertions.assertThrows(SapatiPayException.class, ()-> Mockito.when(registerUseCase.exceute(registerUser)));
    }
    @Test
    void shouldPassTestIfPasswordIsInValid() {
        RegisterUseCaseRequest registerUser = RegisterUseCaseRequestBuilder.builder()
                .name("Shiva")
                .emailId("zshivagmail.com")
                .address("Godawari")
                .citizenShip("src/nepal")
                .availableBalance(0)
                .roles(Roles.BORROWER)
                .accountStatus(Status.INACTIVE)
                .build();
        Assertions.assertThrows(SapatiPayException.class, ()->Mockito.when(registerUseCase.exceute(registerUser)));
    }
    @Test
    void shouldThrowErrorIfBorrowerHasNonZeroBalance() {
        RegisterUseCaseRequest request = RegisterUseCaseRequestBuilder.builder()
                .name("Shiva")
                .emailId("zshiva@gmail.com")
                .address("Godawari")
                .citizenShip("src/nepal")
                .availableBalance(0)
                .roles(Roles.BORROWER)
                .accountStatus(Status.INACTIVE)
                .build();
        Assertions.assertThrows(SapatiPayException.class, () -> this.registerUseCase.exceute(request));
    }

    @AfterEach
    void tearDown(){
        userRepository = null;
    }

}

