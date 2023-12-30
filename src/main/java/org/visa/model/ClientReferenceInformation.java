package org.visa.model;

public class ClientReferenceInformation {
    public String code;
    public Partner partner;

    public ClientReferenceInformation(String code, Partner partner) {
        this.code = code;
        this.partner = partner;
    }
}
