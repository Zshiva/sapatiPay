package citytech.global.platform.utils.securityutils.jwt;

import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.platform.rest.RestResponse;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

import java.util.Objects;
import java.util.logging.Logger;

@Filter("/**")
public class SecurityFilter implements HttpServerFilter {
    private final String TOKEN = "X-XSRF-TOKEN";
    private final TokenService tokenService;

    private final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Inject
    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        try {
            if (request.getMethod() == HttpMethod.OPTIONS)
                return Flowable.just(HttpResponse.ok());

            var token = request.getHeaders().get(TOKEN);

            if (request.getPath().contains("/user/register")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/user/login")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/rest-api")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/data")) {
                return chain.proceed(request);
            }
            if (Objects.isNull(token) || token.isEmpty()) {
                throw new SapatiPayException(SapatiPayErrorMessage.SECURITY_TOKEN_MISSING);
            }

            RequestContext requestContext = tokenService.getUserContextFromRequest(token);
            logger.info("REQUESTED BY :: " + requestContext.getSubject());

            return Flowable.just(true)
                    .doOnRequest(t -> {
                        ContextHolder.set(requestContext);
                    })
                    .switchMap(aBoolean -> chain.proceed(request))
                    .onErrorReturn(throwable -> {
                        throwable.printStackTrace();
                        logger.info("::: ERROR IN CHAIN PROCESS :::");
                        throw new SapatiPayException(SapatiPayErrorMessage.SECURITY_INTERCEPTOR_EXCEPTION);
                    });
        } catch (Exception e) {
            return Flowable.just(HttpResponse.badRequest(RestResponse.error("EX001", e.getMessage())));
        }
    }
}
