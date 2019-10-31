package com.qfedu.vo;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String message;
    private Object data;

    public static Result setOk(Object data) {
        Result r = new Result ();
        r.setCode (200);
        r.setMessage ("OK");
        r.setData (data);
        return r;
    }

    public static Result setError() {
        Result r = new Result ();
        r.setCode (400);
        r.setMessage ("ERROR");
        r.setData (null);
        return r;
    }

    public static Result setResult(boolean isSuccess, Object obj) {
        if (isSuccess) {
            return setOk (obj);
        } else {
            return setError ();
        }
    }

}