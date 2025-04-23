package citytech.global.platform.rest;

import citytech.global.platform.exception.SapatiPayErrorMessage;
import citytech.global.platform.exception.SapatiPayException;
import citytech.global.usecases.mapapiintegration.MapApiUseCaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Singleton
public class RestClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    Logger logger = Logger.getLogger(RestClient.class.getName());

    @Inject
    public RestClient() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newHttpClient();
    }
    public MapApiUseCaseResponse get(String url) throws IOException, InterruptedException {
            HttpResponse<String> mapApiResponse = this.httpClient.send(this.prepareApiClientRequest(url), HttpResponse.BodyHandlers.ofString());
            if (mapApiResponse.statusCode() == 200) {
                LinkedHashMap<String, String> properties = getLocationDetails(mapApiResponse);
                String name = properties.get("name");
                String country = properties.get("country");
                String district = properties.get("district");
                return new MapApiUseCaseResponse(name,country,district);
            } else {
                logger.log(Level.WARNING, "Invalid response from API. Status code: %d", mapApiResponse.statusCode());
                throw new SapatiPayException(SapatiPayErrorMessage.INVALID_RESPONSE);
            }
        }
    public io.micronaut.http.HttpResponse<Object> post(String url, String requestBody) throws IOException, InterruptedException {
        HttpResponse<String> response = this.httpClient.send(this.prepareClientRequest(url, requestBody), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            String responseBody = response.body();
            logger.log(Level.FINE, "Http client response body >>> %s", responseBody);
            return io.micronaut.http.HttpResponse.ok(responseBody);
        } else {
            throw new SapatiPayException(SapatiPayErrorMessage.SOMETHING_WENT_WRONG);
        }
    }
    private HttpRequest prepareClientRequest(String url, String body) {
        logger.log(Level.INFO, "URL AFTER CALL::");
        return HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .timeout(Duration.ofSeconds(30))
                .build();
    }
    private HttpRequest prepareApiClientRequest(String url) {
        logger.log(Level.INFO, "URL AFTER CALL::");
        return HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-type", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(3600))
                .build();
    }
    private LinkedHashMap<String,String> getLocationDetails(HttpResponse<String> mapApiResponse) throws JsonProcessingException {
        String responseBody = mapApiResponse.body();
        logger.log(Level.INFO, "Http client response body >>> %s",responseBody);
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
        ArrayList<LinkedHashMap<String, Object>> features = (ArrayList<LinkedHashMap<String, Object>>) responseMap.get("features");
        LinkedHashMap<String, Object> feature = features.get(0);
        LinkedHashMap<String, String> properties = (LinkedHashMap<String, String>) feature.get("properties");
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        result.put("name",properties.get("name"));
        result.put("country",properties.get("country"));
        result.put("district",properties.get("county"));
        return result;
    }
}
