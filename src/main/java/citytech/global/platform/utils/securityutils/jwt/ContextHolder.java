package citytech.global.platform.utils.securityutils.jwt;

import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.exception.SapatiPayException;

public class ContextHolder {
    private static ThreadLocal<RequestContext> context = new ThreadLocal<>();

    public static RequestContext get() {
        if (context == null) {
            throw new SapatiPayException(SapatiPayErrorMessage.REQUEST_CONTEXT_NOT_SET);
        }
        return context.get();
    }
    public static void set(RequestContext requestContext) {
        if (context == null) {
            context = new ThreadLocal<>();
        }
        context.set(requestContext);
    }
    public static void unset() {
        if (context != null)
            context.remove();
    }
}

