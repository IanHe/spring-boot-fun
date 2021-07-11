package com.example.transactionpropagationdemo;

public interface FooService {
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback();
}