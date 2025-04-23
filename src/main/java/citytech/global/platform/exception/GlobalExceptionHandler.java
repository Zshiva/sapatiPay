package citytech.global.platform.exception;

import citytech.global.platform.rest.RestResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {Exception.class, ExceptionHandler.class})
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, Exception exception) {
        return HttpResponse.badRequest().body(RestResponse.error(exception.getMessage()));
    }
}
