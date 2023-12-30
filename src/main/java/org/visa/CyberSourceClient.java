package org.visa;

import com.cybersource.authsdk.core.ConfigException;
import com.cybersource.authsdk.core.MerchantConfig;
import com.cybersource.authsdk.core.TokenGeneratorFactory;
import com.cybersource.authsdk.payloaddigest.PayloadDigest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.visa.config.Configuration;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CyberSourceClient {

    private static final String BASE_URL = "https://apitest.cybersource.com/pts/v2";

    private final RestTemplate rest = new RestTemplate();

    public String sendAuthorization(String paymentJson) throws ConfigException, IOException {
        var resource = "/payments";
        var path = Paths.get("src/main/resources/" + paymentJson);
        String payment = String.join("", Files.readAllLines(path));

        var uri = buildURI(resource);

        var headers = buildHeader(payment, resource);
        var request = new HttpEntity<>(payment, headers);

        var response = rest.postForEntity(URI.create(uri), request, String.class);

        System.out.println(request.getBody());

        return response.getBody();

    }

    public String sendCapture(String paymentId, String paymentJson) throws IOException, ConfigException {
        var resource = String.format("/payments/%s/captures", paymentId);
        var path = Paths.get("src/main/resources/" + paymentJson);
        String payment = String.join("", Files.readAllLines(path));

        var uri = buildURI(resource);

        var headers = buildHeader(payment, resource);
        var request = new HttpEntity<>(payment, headers);

        var response = rest.postForEntity(URI.create(uri), request, String.class);

        System.out.println(request.getBody());

        return response.getBody();
    }

    public static void sendAuthorizationWithCapture() {
    }

    private static String buildURI(String resource) {
        if (resource == null)
            return BASE_URL;

        return BASE_URL.concat(resource);
    }

    private HttpHeaders buildHeader(String payment, String resource) throws ConfigException {
        var merchantConfig = new MerchantConfig(Configuration.getMerchantInfo());
        var signature = getToken(merchantConfig, payment, resource);
        var digest = new PayloadDigest(merchantConfig).getDigest();

        var headers = new HttpHeaders();
        headers.add("v-c-merchant-id", "renatech");
        headers.add("Date", getDate());
        headers.add("Host", "apitest.cybersource.com");
        headers.add("Digest", digest);
        headers.add("Signature", signature);
        headers.add("Content-Type", "application/json");
        return headers;
    }


    public static String getDate() {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
    }

    public String getToken(MerchantConfig config, String body, String resource) throws ConfigException {
        config.setRequestType("POST");
        config.setRequestData(body);
        config.setRequestTarget("/pts/v2" + resource);
        var token = TokenGeneratorFactory.getAuthToken(config, body);
        return token.getToken();
    }

}