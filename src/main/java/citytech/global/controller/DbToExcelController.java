package citytech.global.controller;

import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.rest.RestResponse;
import citytech.global.usecases.excel.DbToExcelUseCase;
import citytech.global.usecases.excel.DbToExcelUseCaseRequest;
import citytech.global.usecases.excel.DbToExcelUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Optional;
@Controller("/data")
public class DbToExcelController {
    private final DbToExcelUseCase dbToExcelUseCase;
    @Inject
    public DbToExcelController(DbToExcelUseCase dbToExcelUseCase) {
        this.dbToExcelUseCase = dbToExcelUseCase;
    }
    @Get("/export")
    public HttpResponse<Object> exportUsers() throws IOException {
            DbToExcelUseCaseRequest request = new DbToExcelUseCaseRequest();
            Optional<DbToExcelUseCaseResponse> response = dbToExcelUseCase.exceute(request);
            if (response.isPresent()) {
                return HttpResponse.ok(RestResponse.success(response.get()));
            } else {
                return HttpResponse.badRequest(RestResponse.error(SapatiPayErrorMessage.SOMETHING_WENT_WRONG.getMessage()));
            }
        }
}

