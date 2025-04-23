package citytech.global.usecases.mapapiintegration;

import citytech.global.platform.usecase.UseCaseResponse;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Introspected
@RecordBuilder
@Serdeable
public record MapApiUseCaseResponse(
        String name,
        String country,
        String district
)
        implements UseCaseResponse {

}
