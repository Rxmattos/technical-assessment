package org.visa.model;

public class OrderInformation {
    public BillTo billTo;
    public AmountDetails amountDetails;

    public OrderInformation(BillTo billTo, AmountDetails amountDetails) {
        this.billTo = billTo;
        this.amountDetails = amountDetails;
    }
}