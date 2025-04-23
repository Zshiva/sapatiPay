package citytech.global.usecases.excel;

import citytech.global.platform.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
@Introspected
@Serdeable
public record DbToExcelUseCaseRequest() implements UseCaseRequest {
}
