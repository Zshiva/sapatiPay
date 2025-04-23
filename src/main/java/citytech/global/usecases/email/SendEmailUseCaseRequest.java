package citytech.global.usecases.email;

public record SendEmailUseCaseRequest(
        String to,
        String subject,
        String password
) {
}
