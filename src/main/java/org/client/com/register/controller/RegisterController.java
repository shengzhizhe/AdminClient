package org.client.com.register.controller;

import feign.FeignException;
import org.apache.shiro.SecurityUtils;
import org.client.com.api.AccountInterface;
import org.client.com.api.model.AccountModel;
import org.client.com.register.model.RegisterModel;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private AccountInterface anInterface;

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<RegisterModel> register(@Valid @RequestBody RegisterModel model,
                                                  BindingResult bindingResult) {
        try {
            SecurityUtils.getSubject().getSession().setAttribute("message", "");
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(false, bindingResult.getFieldError().getDefaultMessage(), 402);
            }
//两次输入的密码是否一至
            if (!model.isPass()) {
                return new ResponseResult<>(false, "两次输入的密码不一致", 402);
            }

            AccountModel accountModel = new AccountModel();
            accountModel.setAccount(model.getAccount());
            accountModel.setAcctype(1);
            accountModel.setPassword(model.getPassword());
            accountModel.setTimes(System.currentTimeMillis());

            ResponseResult result = anInterface.register(accountModel);
            if (result.isSuccess())
                return new ResponseResult<>(true, null, 200);
            else {
                SecurityUtils.getSubject().getSession().setAttribute("message",
                        result.getCode() == 501 ? "该账户已注册" : result.getMessage());
                return new ResponseResult<>(false, result.getCode() == 501 ? "该账户已注册" : result.getMessage(), 402);
            }
        } catch (FeignException f) {
            return new ResponseResult<>(false, "服务链接超时", 500);
        }
    }

}
