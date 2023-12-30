import com.cybersource.authsdk.core.ConfigException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.visa.CyberSourceClient;

import java.io.IOException;

public class TestPayment {

    private final CyberSourceClient client = new CyberSourceClient();

    @Test
    void testAuth() throws ConfigException, IOException {
        String response = client.sendAuthorization("authOnly.json");
        System.out.println(response);
        Assertions.assertNotNull(response);
    }

    @Test
    void testAuthAndCaptureTwoRequests() throws ConfigException, IOException {
        String response = client.sendAuthorization("authOnly_2.json");

        var responseAsJson = new Gson().fromJson(response, JsonObject.class);
        var paymentId = responseAsJson.get("id").getAsString();
        Assertions.assertNotNull(paymentId);


        client.sendCapture(paymentId, "capture.json");
        System.out.println(response);
        Assertions.assertNotNull(response);
    }

    @Test
    void testAuthAndCaptureSingleRequest() throws ConfigException, IOException {
        String response = client.sendAuthorization("authAndCapture.json");
        System.out.println(response);
        Assertions.assertNotNull(response);
    }

    @Test
    void testAuthReversal() {
        //auth
        //reversal
    }

    @Test
    void testAuthRefund() {
        //auth
        //reversal
    }

    @Test
    void testRejectAuthByEmail() {
    }
}
