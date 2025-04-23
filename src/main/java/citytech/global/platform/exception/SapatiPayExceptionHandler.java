package citytech.global.platform.exception;

import citytech.global.platform.rest.RestResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
@Requires(classes = {SapatiPayException.class,ExceptionHandler.class})
public class SapatiPayExceptionHandler implements ExceptionHandler<SapatiPayException, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, SapatiPayException exception) {
        String message = exception.getErrorMessage().getMessage();
        String code = exception.getErrorMessage().getCode();
        return HttpResponse.badRequest().body(RestResponse.error(code,message));
    }
}
