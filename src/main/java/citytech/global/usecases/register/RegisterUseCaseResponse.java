package citytech.global.usecases.register;

import citytech.global.platform.constants.Status;
import citytech.global.platform.usecase.UseCaseResponse;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@RecordBuilder
@Introspected
@Serdeable
public record RegisterUseCaseResponse(
        String emailId,
        @Enumerated(EnumType.STRING)
        Status accountstatus,
        String message
) implements UseCaseResponse {}
