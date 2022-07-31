package com.sss.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorRes {

    private String errorCode;
    private String message;
    private List<FieldError> errors;

    private ErrorRes(ErrorCode code, List<FieldError> errors) {
        this.errorCode = code.getCode();
        this.message = code.getMessage();
        this.errors = errors;
    }

    private ErrorRes(ErrorCode code, String errorMessage) {
        this.errorCode = code.getCode();
        this.message = errorMessage;
        this.errors = new ArrayList<>();
    }

    private ErrorRes(ErrorCode code) {
        this.errorCode = code.getCode();
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
    }

    public static ErrorRes of(ErrorCode code) {
        return new ErrorRes(code, new ArrayList<>());
    }

    public static ErrorRes of(ErrorCode code, List<FieldError> errors) {
        return new ErrorRes(code, errors);
    }

    public static ErrorRes of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorRes(code, FieldError.of(bindingResult));
    }

    public static ErrorRes of(ErrorCode code, String errorMessage) {
        return new ErrorRes(code, errorMessage);
    }

    public static ErrorRes of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorRes(ErrorCode.COMMON_INVALID_PARAMETER, errors);
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of() {
            return new ArrayList<>();
        }

        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(e -> FieldError.builder()
                            .field(e.getField())
                            .value(getRejectedValue(e))
                            .reason(e.getDefaultMessage())
                            .build())
                    .collect(Collectors.toList());
        }

        private static String getRejectedValue(org.springframework.validation.FieldError error) {
            return error.getRejectedValue() == null ? "" : error.getRejectedValue().toString();
        }
    }
}
