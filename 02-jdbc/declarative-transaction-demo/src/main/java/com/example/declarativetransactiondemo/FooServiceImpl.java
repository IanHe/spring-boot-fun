package com.example.declarativetransactiondemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FooServiceImpl implements FooService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("insert into foo (bar) values ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("insert into foo (bar) values ('BBB')");
        throw new RollbackException();
    }

    @Override
//    @Transactional(rollbackFor = RollbackException.class)
    public void invokeInsertThenRollback() throws RollbackException {
        /*
            The below method invoke will ignore the @Transactional
            1. The @Transactional is proxied through Spring's AOP
            2. So the Invoked @Transactional method will be ignored
         */
        insertThenRollback();
    }
}
