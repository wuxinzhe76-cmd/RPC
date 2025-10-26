package com.charles.netty.medium;

import com.alibaba.fastjson.JSONObject;
import com.charles.netty.handler.param.ServerRequest;
import com.charles.netty.util.Response;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Medium {
    public static Map<String,BeanMethod> beanMethodMap;
    static {
        beanMethodMap = new HashMap<String,BeanMethod>();
    }

    private static Medium medium = null;
    private Medium() {

    }
    public static Medium newInstance() {
        if (medium == null){
            medium = new Medium();
        }
        return medium;

    }

    //反射的方式处理业务代码
    public Response process(ServerRequest request) {
        Response result = null;
        String command = request.getCommand();
        BeanMethod beanMethod = beanMethodMap.get(command);
        if (beanMethod == null){
            return null;
        }
        try {
            Object bean = beanMethod.getBean();
            Method method = beanMethod.getMethod();
            Class<?> parameterType = method.getParameterTypes()[0];
            Object content = request.getContent();
            Object args = JSONObject.parseObject(JSONObject.toJSONString(content), parameterType);
            result = (Response) method.invoke(bean, args);
            result.setId(request.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
