package com.jumia.skylens.persistence.transaction;

import java.util.function.Supplier;

public interface TransactionService {

    <T> T get(Supplier<T> supplier);

    <T> T getInNewTransaction(Supplier<T> supplier);

    void run(Runnable runnable);

    void runInNewTransaction(Runnable runnable);
}
