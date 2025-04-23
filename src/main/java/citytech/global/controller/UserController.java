package citytech.global.controller;

import citytech.global.converter.UserConverter;
import citytech.global.controller.payload.LoginUserRequestPayload;
import citytech.global.controller.payload.RegisterUserRequestPayload;
import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.platform.rest.RestResponse;
import citytech.global.usecases.login.LoginUseCase;
import citytech.global.usecases.login.LoginUseCaseRequest;
import citytech.global.usecases.register.RegisterUseCase;
import citytech.global.usecases.register.RegisterUseCaseRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.io.IOException;


@Controller("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    @Inject
    public UserController(RegisterUseCase registerUseCase, LoginUseCase loginUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }
    @Post("/register")
    public HttpResponse<Object> registerUser(@Body RegisterUserRequestPayload payload) throws IOException, InterruptedException {
            RegisterUseCaseRequest request = UserConverter.toRequest(payload);
            var response = registerUseCase.exceute(request);
            if(response.isPresent()){
                return HttpResponse.ok(RestResponse.success(response.get()));
            }else {
            throw new SapatiPayException(SapatiPayErrorMessage.REGISTRATION_FAILED);
            }
    }
    @Post("/login")
    public HttpResponse<Object> loginUser(@Body LoginUserRequestPayload payload){
            LoginUseCaseRequest request = UserConverter.toRequest(payload);
            var response = loginUseCase.exceute(request);
            if(response.isPresent()) {
                return HttpResponse.ok(RestResponse.success(response.get()));
            }else {
                throw new SapatiPayException(SapatiPayErrorMessage.LOGIN_UNSUCCESSFUL);
            }
    }
}
