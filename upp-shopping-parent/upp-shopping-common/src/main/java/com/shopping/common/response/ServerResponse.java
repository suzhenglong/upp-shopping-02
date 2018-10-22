package com.shopping.common.response;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shopping.common.constants.Constants;
import com.shopping.common.utils.TokenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description:通用返回值
 * @author: zhenglongsu@163.com
 * @date: 2018.10.16 10:14
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public ServerResponse() {
    }

    public ServerResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static boolean fail(ServerResponse serverResponse) {
        if (!serverResponse.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            return true;
        }
        return false;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_200, null, null);
    }

    public static <T> ServerResponse<T> createBySuccessWithDefaultMsg() {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_200, msg, null);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_200, msg, data);
    }


    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_500, Constants.HTTP_RES_CODE_500_VALUE, null);
    }


    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(Constants.HTTP_RES_CODE_500, errorMessage, null);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(Integer errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage, null);
    }

    public static Object mapToEntity(ServerResponse serverResponse, Class clazz) {
        if (Constants.HTTP_RES_CODE_200.equals(serverResponse.getCode())) {
            LinkedHashMap hashMap = (LinkedHashMap) serverResponse.getData();
            String json = JSONObject.toJSONString(hashMap);
            return JSONObject.parseObject(json, clazz);
        }
        return null;
    }

    public static List mapToListEntity(ServerResponse serverResponse, Class clazz) {
        if (Constants.HTTP_RES_CODE_200.equals(serverResponse.getCode())) {
            List arrayList = new ArrayList();
            List list = (List) serverResponse.getData();
            for (int i = 0; i < list.size(); i++) {
                LinkedHashMap hashMap = (LinkedHashMap) list.get(i);
                String json = JSONObject.toJSONString(hashMap);
                Object object = JSONObject.parseObject(json, clazz);
                arrayList.add(object);
            }
            return arrayList;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        String memberToken = TokenUtils.getMemberToken();
        jsonObject.put("memberToken", memberToken);
        ServerResponse<JSONObject> bySuccess = ServerResponse.createBySuccess(jsonObject);
        System.out.println(bySuccess);
        System.out.println(bySuccess.getData());
        System.out.println(bySuccess.getData().get("memberToken"));
        System.out.println("============================");
        ServerResponse<String> success = ServerResponse.createBySuccess(memberToken);
        System.out.println(success);
        System.out.println(success.getData());
    }
}
