package citytech.global.controller;

import citytech.global.controller.payload.LoginUserApiPayload;
import citytech.global.controller.payload.MapApiRequestPayload;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.platform.rest.RestClient;

import citytech.global.platform.rest.RestResponse;
import citytech.global.usecases.mapapiintegration.MapApiUseCaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.io.IOException;

@Controller("rest-api")
@Consumes(MediaType.ALL)
@Produces(MediaType.APPLICATION_JSON)
public class RestController {
    private final RestClient client;
    @Inject
    public RestController(RestClient client) {
        this.client = client;
    }

    @Post("http-client-test")
    public HttpResponse<Object> httpApiTest(@Body LoginUserApiPayload payload) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(payload);
        var env = Dotenv.load();
        String url = env.get("THIRD_PARTY_API");
        var resp = this.client.post(url, data);
        return HttpResponse.ok(resp.body());
    }
    @Post("/map-integration")
    public HttpResponse<Object> httpMapApiTest(@Header String API_KEY, @Body MapApiRequestPayload mapPayload) throws IOException, InterruptedException {
        double latitude = mapPayload.latitude();
        double longitude = mapPayload.longitude();
        var env = Dotenv.load();
        String url = env.get("GEO_APIFY_URL");
        String apiUrl = String.format(url, latitude, longitude, API_KEY);
            MapApiUseCaseResponse apiResponse = this.client.get(apiUrl);
            return HttpResponse.ok(RestResponse.success(apiResponse));
    }
}
