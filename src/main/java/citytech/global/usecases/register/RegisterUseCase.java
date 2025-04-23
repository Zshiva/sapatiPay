package citytech.global.usecases.register;

import citytech.global.platform.constants.Roles;
import citytech.global.converter.UserConverter;
import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.rest.RestClient;
import citytech.global.platform.utils.helperutils.PasswordGenerator;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.platform.usecase.UseCase;
import citytech.global.repository.user.UserEntity;
import citytech.global.repository.user.UserRepository;
import citytech.global.usecases.email.SendEmailUseCaseRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUseCase implements UseCase<RegisterUseCaseRequest, RegisterUseCaseResponse> {

    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final RestClient client;

    @Inject
    public RegisterUseCase(UserRepository userRepository, RestClient client, PasswordGenerator passwordGenerator) {
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
        this.client = client;
    }
    @Override
    public Optional<RegisterUseCaseResponse> exceute(RegisterUseCaseRequest request) throws IOException, InterruptedException {
        validateRegistration(request);
        String password = passwordGenerator.generateRandomPassword(8);
        UserEntity user = UserConverter.toEntity(request,password);
        UserEntity savedUserEntity = this.userRepository.save(user);
        sendEmail(savedUserEntity, password);
        RegisterUseCaseResponseBuilder.builder().build();
        return Optional.of(new RegisterUseCaseResponse(savedUserEntity.getEmailId(),savedUserEntity.getStatus(),"User Registered Successfully"));
    }
    private void validateRegistration(RegisterUseCaseRequest userRegisterRequest) {
        String emailId = userRegisterRequest.emailId();
        if (!isValidEmail(emailId)) {
            throw new SapatiPayException(SapatiPayErrorMessage.EMAIL_ID_INVALID);
        }
        if (Roles.ADMIN.equals(userRegisterRequest.roles())) {
            if (userRepository.existsByRoles(Roles.ADMIN)) {
                throw new SapatiPayException(SapatiPayErrorMessage.ADMIN_ALREADY_EXISTS);
            }
        }
        if (Roles.ADMIN.equals(userRegisterRequest.roles()) || (Roles.BORROWER.equals(userRegisterRequest.roles()))) {
            if (userRegisterRequest.availableBalance() != 0.0) {
                throw new SapatiPayException(SapatiPayErrorMessage.ZERO_BALANCE);
            }
        } else {
            if (userRegisterRequest.availableBalance() < 500.0) {
                throw new SapatiPayException(SapatiPayErrorMessage.MINIMUM_500_BALANCE);
            }
        }
        Optional<UserEntity> emailIdExists = userRepository.findByEmailId(emailId);
            if (emailIdExists.isPresent()) {
                throw new SapatiPayException(SapatiPayErrorMessage.EMAIL_ID_ALREADY_EXISTS);
            }
    }
    private boolean isValidEmail(String emailId) {
        Dotenv env = Dotenv.load();
        String emailRegex = env.get("EMAIL_REGEX");
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }
    private void sendEmail(UserEntity user, String password) throws IOException, InterruptedException {
        SendEmailUseCaseRequest request = new SendEmailUseCaseRequest(user.getEmailId(),"Password for Signup",password);
        ObjectMapper objectMapper = new ObjectMapper();
        var resp = objectMapper.writeValueAsString(request);
        Dotenv env = Dotenv.load();
        String url = env.get("HOST")+"/email/send";
        this.client.post(url, resp);
    }
}
