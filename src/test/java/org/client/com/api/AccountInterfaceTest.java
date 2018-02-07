package org.client.com.api;

import org.client.com.Application;
import org.client.com.model.AccountModel;
import org.client.com.util.resultJson.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountInterfaceTest {

    private final static Logger logger = LoggerFactory.getLogger(AccountInterfaceTest.class);

    @Resource
    private AccountInterface anInterface;

    @Test
    public void test() {
        ResponseResult<AccountModel> result = anInterface.getAccount("这是个测试");
        System.out.println(result.toString());
    }
}