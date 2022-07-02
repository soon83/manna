package com.sss.common.response;

import com.sss.common.exception.BaseException;
import com.sss.common.interceptor.HttpRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class BaseControllerAdvice {

    private static final List<ErrorCode> SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST = new ArrayList<>();

    /**
     * http status: 500 AND result: FAILURE
     * 시스템 예외 상황. 집중 모니터링 대상
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Res> onException(Exception e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.error("[Exception] eventId = {}, error = {}", eventId, e);
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.fail(errorResponse));
    }

    /**
     * http status: 200 AND result: FAILURE
     * 시스템은 이슈 없고, 비즈니스 로직 처리에서 에러가 발생함
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Res> onBaseException(BaseException e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[BaseException] eventId = {}, cause = {}, errorMsg = {}",
                    eventId, NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[BaseException] eventId = {}, cause = {}, errorMsg = {}",
                    eventId, NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        ErrorRes errorResponse = ErrorRes.of(e.getErrorCode());
        return ResponseEntity.status(HttpStatus.OK).body(Res.fail(errorResponse));
    }

    /**
     * 예상치 않은 Exception 중에서 모니터링 skip 이 가능한 Exception 을 처리할 때
     * ex) ClientAbortException
     */
    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<Res> skipException(Exception e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[SkipException] eventId = {}, cause = {}, errorMsg = {}",
                eventId, NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.OK).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400: request parameter error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Res> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400: JSON parse error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity httpMessageNotReadableException(HttpMessageNotReadableException e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400: enum type 일치하지 않을 때
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400: request parameter bind error
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity bindException(BindException e) {
        String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }
}
