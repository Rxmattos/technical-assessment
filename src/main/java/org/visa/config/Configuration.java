package org.visa.config;

import java.util.Properties;

public class Configuration {
    public static Properties getMerchantInfo() {
        var props = new Properties();
        props.setProperty("authenticationType", "HTTP_Signature");
        props.setProperty("merchantID", "renatech");
        props.setProperty("runEnvironment", "apitest.cybersource.com");
        props.setProperty("merchantKeyId", "951819b0-032f-483c-90d5-0a1cb6f889d4");
        props.setProperty("merchantsecretKey", "BEFpem6CeGfBtJc9Rr/S4SfFe232sGE4IDHxn4/MAIk=");
        props.setProperty("useMetaKey", "false");
        props.setProperty("enableClientCert", "false");
        props.setProperty("enableLog", "true");
        return props;
    }

}
