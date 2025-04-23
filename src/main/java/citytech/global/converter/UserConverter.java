package citytech.global.converter;

import citytech.global.platform.constants.Status;
import citytech.global.controller.payload.LoginUserRequestPayload;
import citytech.global.controller.payload.RegisterUserRequestPayload;
import citytech.global.repository.user.UserEntity;
import citytech.global.usecases.login.LoginUseCaseRequest;
import citytech.global.usecases.login.LoginUseCaseRequestBuilder;
import citytech.global.usecases.register.RegisterUseCaseRequest;
import citytech.global.usecases.register.RegisterUseCaseRequestBuilder;

import java.util.UUID;

public class UserConverter {
    public static LoginUseCaseRequest toRequest(LoginUserRequestPayload payload){
     return LoginUseCaseRequestBuilder.builder()
             .emailId(payload.emailId())
             .password(payload.password())
             .build();
    }
    public static RegisterUseCaseRequest toRequest(RegisterUserRequestPayload payload){
        return RegisterUseCaseRequestBuilder.builder()
                .name(payload.name())
                .emailId(payload.emailId())
                .address(payload.address())
                .citizenShip(payload.citizenship())
                .roles(payload.roles())
                .accountStatus(payload.accountStatus())
                .build();
    }
    public static UserEntity toEntity(RegisterUseCaseRequest request, String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString().substring(0,7));
        userEntity.setName(request.name());
        userEntity.setEmailId(request.emailId());
        userEntity.setPassword(password);
        userEntity.setAddress(request.address());
        userEntity.setRoles(request.roles());
        userEntity.setCitizenShip(request.citizenShip());
        userEntity.setAvailableBalance(request.availableBalance());
        userEntity.setStatus(Status.INACTIVE);
        return userEntity;
    }
}
