package com.sijinghua.easyrpc.serialization;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class RpcResponse implements Serializable {
    // success or fail
    private String status;

    // return value of Object
    private Object retValue;
    private Map<String, String> headers = new HashMap<>();

    // return exception object if fail
    private Exception exception;
}
