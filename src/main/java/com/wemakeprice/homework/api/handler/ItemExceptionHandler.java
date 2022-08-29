package com.wemakeprice.homework.api.handler;

import com.wemakeprice.homework.api.controller.ItemController;
import com.wemakeprice.homework.api.dto.ResponseDto;
import com.wemakeprice.homework.api.exception.UrlNotExistedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ItemController.class)
@RequiredArgsConstructor
@Slf4j
public class ItemExceptionHandler {

    private final ResponseDto responseDto;

    @ExceptionHandler(UrlNotExistedException.class)
    public ResponseEntity<?> urlNotExistedException(UrlNotExistedException ex){
        log.info("error message: {}", ex.getMessage());
        return responseDto.badRequest(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex){
        log.info("error message: {}", ex.getMessage());
        return responseDto.badRequest(ex.getMessage());
    }
}
