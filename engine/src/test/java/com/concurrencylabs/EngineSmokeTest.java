package com.concurrencylabs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EngineSmokeTest {

    @Test
    public void testTransactionInitialization() {
        Transaction txn = new Transaction("T1");
        assertNotNull(txn);
        assertEquals("T1", txn.getTxnId());
    }
}