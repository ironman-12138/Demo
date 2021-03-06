package org.example.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回编码")
    private String code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "返回对象")
    private T data;

    public Result() {
    }

    public static Result toClient(String errCode) {
        return new Result(errCode, ResultCode.getErrMsg(errCode), null);
    }

    public static <T> Result<T> toClient(String errCode, T data) {
        return new Result<T>(errCode, ResultCode.getErrMsg(errCode), data);
    }

    public static <T> Result<T> toClient(String errCode, String errMsg, T data) {
        return new Result<T>(errCode, errMsg, data);
    }

    public static Result successToClient() {
        return new Result(ResultCode.SUCCESS.getErrCode(), ResultCode.SUCCESS.getErrMsg(), null);
    }

    public static <T> Result<T> successToClient(T data) {
        return new Result<T>(ResultCode.SUCCESS.getErrCode(), ResultCode.SUCCESS.getErrMsg(), data);
    }

    public static Result errorToClient(ResultCode baseErrorCode) {
        return new Result(baseErrorCode.getErrCode(), baseErrorCode.getErrMsg(), null);
    }

    public static Result errorToClient(String errCode) {
        return new Result(errCode, ResultCode.getErrMsg(errCode), null);
    }

    public static Result errorToClient(String errCode, String errMsg) {
        return new Result(errCode, errMsg, null);
    }

    public static Result errorMsgToClient(String errMsg) {
        return new Result(ResultCode.ERROR.getErrCode(), errMsg, null);
    }

    public static boolean isSuccess(String errCode) {
        return ResultCode.SUCCESS.getErrCode().equals(errCode);
    }
}
