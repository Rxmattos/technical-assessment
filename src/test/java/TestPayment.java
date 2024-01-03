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
        // An authorization transaction and a capture transaction in two separate calls
    void testAuthAndCaptureTwoRequests() throws ConfigException, IOException {
        String authResponse = client.sendAuthorization("authOnly_2.json");

        var responseAsJson = new Gson().fromJson(authResponse, JsonObject.class);
        var paymentId = responseAsJson.get("id").getAsString();
        Assertions.assertNotNull(paymentId);
        System.out.println(authResponse);


        var captureResponse = client.sendCapture(paymentId, "capture.json");
        System.out.println(captureResponse);
        Assertions.assertNotNull(captureResponse);
    }

    @Test
    //An authorization transaction and a capture transaction in a single call
    void testAuthAndCaptureSingleRequest() throws ConfigException, IOException {
        String response = client.sendAuthorization("authAndCapture.json");
        System.out.println(response);
        Assertions.assertNotNull(response);
    }

    @Test
    //An authorization reversal.
    void testAuthReversal() throws ConfigException, IOException {
        //auth
        String authResponse = client.sendAuthorization("authOnly.json");
        var responseAsJson = new Gson().fromJson(authResponse, JsonObject.class);
        var paymentId = responseAsJson.get("id").getAsString();
        Assertions.assertNotNull(authResponse);
        System.out.println(authResponse);
        ////

        //reversal
        String reversalResponse = client.sendReversal(paymentId, "reversal.json");
        Assertions.assertNotNull(reversalResponse);
        System.out.println(reversalResponse);
        /////
        ////
    }

    @Test
    //A payment credit (refund).
    void testAuthRefund() throws ConfigException, IOException {
        String authResponse = client.sendAuthorization("authOnly.json");
        var responseAsJson = new Gson().fromJson(authResponse, JsonObject.class);
        var paymentId = responseAsJson.get("id").getAsString();
        Assertions.assertNotNull(authResponse);
        System.out.println(authResponse);
        ////

        //Refund
        String reversalResponse = client.sendRefund(paymentId, "refund.json");
        Assertions.assertNotNull(reversalResponse);
        System.out.println(reversalResponse);
        /////
    }

    @Test
    void testRejectAuthByEmail() throws ConfigException, IOException {
        String response = client.sendAuthorization("authOnly_email_fail.json");
        System.out.println(response);
        Assertions.assertNotNull(response);
    }
}
