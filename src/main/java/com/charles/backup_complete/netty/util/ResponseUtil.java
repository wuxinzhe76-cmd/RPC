package com.charles.netty.util;

public class ResponseUtil {
    public static Response createResponseResult(){
        return new Response();
    }
    public static Response createFailResult(String code,String message){
        Response result = new Response();
        result.setCode(code);
        result.setMessage(message);
        return result;

    }
    public static Response createSuccessResult(Object content){
        Response response = new Response();
        response.setResult(content);
        return response;
    }
}
