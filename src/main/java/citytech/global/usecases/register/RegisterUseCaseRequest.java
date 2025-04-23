package citytech.global.usecases.register;

import citytech.global.platform.constants.Roles;
import citytech.global.platform.constants.Status;
import citytech.global.platform.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@RecordBuilder
@Introspected
@Serdeable
public record RegisterUseCaseRequest(
    String name,
    String emailId,
    String address,
    String citizenShip,
    double availableBalance,
    @Enumerated(EnumType.STRING)
    Roles roles,
    @Enumerated(EnumType.STRING)
    Status accountStatus
    ) implements UseCaseRequest {}
