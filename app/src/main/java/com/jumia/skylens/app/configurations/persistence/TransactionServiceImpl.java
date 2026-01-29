package com.jumia.skylens.app.configurations.persistence;

import com.jumia.skylens.persistence.transaction.TransactionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Transactional
    @Override
    public <T> T get(Supplier<T> supplier) {

        return supplier.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public <T> T getInNewTransaction(Supplier<T> supplier) {

        return supplier.get();
    }

    @Transactional
    @Override
    public void run(Runnable runnable) {

        runnable.run();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void runInNewTransaction(Runnable runnable) {

        runnable.run();
    }
}
