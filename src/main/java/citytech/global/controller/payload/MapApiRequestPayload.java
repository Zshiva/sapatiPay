package citytech.global.controller.payload;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
@Introspected
@Serdeable
public record MapApiRequestPayload(
        double latitude,
        double longitude,
        String apiKey
){}
