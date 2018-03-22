package org.client.com.api;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.client.com.api.model.TokenModel;
import org.client.com.util.resultJson.ResponseResult;

public interface TokenInterface {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /token/token")
    ResponseResult<TokenModel> add(@Param("model") TokenModel model);

    @RequestLine("POST /token/updateToken")
    @Body("token={token}")
    ResponseResult<TokenModel> updateToken(@Param("token") String token);

    @RequestLine("GET /token/token/{token}")
    ResponseResult<TokenModel> getByToken(@Param("token") String token);
}
