package com.sss.response;

import com.sss.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Res<T> {

    private Result result;
    private boolean success;
    private T data;
    private String errorCode;
    private String message;

    public static <T> Res<T> success() {
        return (Res<T>) Res.builder()
                .result(Result.SUCCESS)
                .success(true)
                .build();
    }

    public static <T> Res<T> success(T data) {
        return (Res<T>) Res.builder()
                .result(Result.SUCCESS)
                .success(true)
                .data(data)
                .build();
    }

    public static <T> Res<T> fail(T data) {
        return (Res<T>) Res.builder()
                .result(Result.FAILURE)
                .success(false)
                .data(data)
                .build();
    }

    public static Res fail(ErrorCode errorCode) {
        return Res.builder()
                .result(Result.FAILURE)
                .success(false)
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static Res fail(String message, String errorCode) {
        return Res.builder()
                .result(Result.FAILURE)
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .build();
    }

    public enum Result {
        SUCCESS,
        FAILURE;
    }
}
