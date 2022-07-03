package com.sss.common.response;

import com.sss.common.interceptor.HttpRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class ExceptionHandlerController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Res> handleError(HttpServletRequest req) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.info("### HttpStatus: {}", status);

        if (status != null) {
            ErrorRes errorResponse = null;
            String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
            HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
            switch (httpStatus) {
                case UNAUTHORIZED:
                    errorResponse = ErrorRes.of(ErrorCode.COMMON_UNAUTHORIZED);
                    log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, errorResponse.getMessage());
                    break;
                case NOT_FOUND:
                    errorResponse = ErrorRes.of(ErrorCode.COMMON_NOT_FOUND);
                    log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, errorResponse.getMessage());
                    break;
            }
            return ResponseEntity.status(httpStatus).body(Res.fail(errorResponse));
        }
        return null;
    }
}
