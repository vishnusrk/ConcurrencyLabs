package com.concurrencylabs;

public class Transaction {
    private String txnId;

    public Transaction(String id) {
        this.txnId = id;
    }

    public String getTxnId() {
        return this.txnId;
    }
}
