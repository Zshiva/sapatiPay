package citytech.global.platform.usecase;

import java.io.IOException;
import java.util.Optional;
@FunctionalInterface
public interface UseCase<I extends UseCaseRequest, O extends  UseCaseResponse> {
    Optional<O> exceute(I request) throws IOException, InterruptedException;
}
