package citytech.global.usecases.login;

import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.platform.rest.RestResponse;
import citytech.global.platform.usecase.UseCase;
import citytech.global.platform.utils.securityutils.jwt.TokenService;
import citytech.global.repository.user.UserEntity;
import citytech.global.repository.user.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class LoginUseCase implements UseCase<LoginUseCaseRequest, LoginUseCaseResponse> {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    @Inject
    public LoginUseCase(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }
    @Override
    public Optional<LoginUseCaseResponse> exceute(LoginUseCaseRequest request) {
        String emailId = request.emailId();
        Optional<UserEntity> userDetails = userRepository.findByEmailId(emailId);
        if (userDetails.isPresent()) {
            UserEntity userEntity = userDetails.get();
                if (userEntity.getPassword().equals(request.password())) {
                    String token = tokenService.generateToken(userEntity.getId(), userEntity.getEmailId(), userEntity.getRoles().name());
                    String roleMessage = userEntity.getRoles() != null ? userEntity.getRoles().toString() : SapatiPayErrorMessage.UNKNOWN_ROLE.getMessage();
                    return Optional.of(new LoginUseCaseResponse(roleMessage + RestResponse.success().message(),token));
                } else {
                    throw new SapatiPayException(SapatiPayErrorMessage.LOGIN_UNSUCCESSFUL);
                }
        } else {
            throw new SapatiPayException(SapatiPayErrorMessage.LOGIN_CREDENTIALS_DOESNOT_MATCH);
        }
    }
}
