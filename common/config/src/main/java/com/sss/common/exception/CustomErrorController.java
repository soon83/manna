package com.sss.common.exception;

import com.sss.common.interceptor.HttpRequestInterceptor;
import com.sss.common.response.ErrorCode;
import com.sss.common.response.ErrorRes;
import com.sss.common.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Res> handleError(HttpServletRequest req) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.info("### HttpStatus: {}", status);

        if (status != null) {
            Integer httpStatus = Integer.valueOf(status.toString());
            if (httpStatus == HttpStatus.FORBIDDEN.value()) {

            } else if (httpStatus == HttpStatus.NOT_FOUND.value()) {
                String eventId = MDC.get(HttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
                log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId, ErrorCode.COMMON_NOT_FOUND.getMessage());
                ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.fail(errorResponse));
            }
        }
        return null;
    }
}
