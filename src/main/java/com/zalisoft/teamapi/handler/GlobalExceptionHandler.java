package com.zalisoft.teamapi.handler;

import com.zalisoft.teamapi.dto.BaseResponseDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;

import com.zalisoft.teamapi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public final ResponseEntity<BaseResponseDto> handleException(BusinessException e) {
        HttpStatus httpStatus=e.getHttpStatus();
        return createResponse(httpStatus, e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "",e);
    }

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<BaseResponseDto> handleException(Exception e) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessageEnum.BACK_SYSTEM_ERROR_MSG_001.message(), ResponseMessageEnum.BACK_SYSTEM_ERROR_MSG_001.messageDetail(), e);
    }

    private final ResponseEntity<BaseResponseDto> createResponse(HttpStatus httpStatus, String message, String detailMessage, Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(httpStatus).body(BaseResponseDto.builder().
                statusCode(httpStatus.value()).status(httpStatus.name()).message(message).messageDetail(detailMessage).success(false).build());
    }
}
