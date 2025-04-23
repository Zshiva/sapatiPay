package citytech.global.controller.payload;

public record SendEmailRequestPayload(
        String to,
        String from,
        String subject,
        String text
) {
}
