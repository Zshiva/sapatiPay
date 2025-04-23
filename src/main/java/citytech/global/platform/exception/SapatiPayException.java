package citytech.global.platform.exception;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class SapatiPayException extends RuntimeException{
    private final SapatiPayErrorMessage errorMessage;

    public SapatiPayException(SapatiPayErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
    public SapatiPayErrorMessage getErrorMessage(){
        return errorMessage;
    }
}
