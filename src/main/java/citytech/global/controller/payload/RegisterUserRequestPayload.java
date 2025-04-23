package citytech.global.controller.payload;

import citytech.global.platform.constants.Roles;
import citytech.global.platform.constants.Status;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
@Introspected
@Serdeable
public record RegisterUserRequestPayload(
        String name,
        String emailId,
        String address,
        String citizenship,
        double availableBalance,
        Roles roles,
        Status accountStatus
) {
}
