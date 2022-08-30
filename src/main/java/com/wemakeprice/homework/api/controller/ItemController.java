package com.wemakeprice.homework.api.controller;

import com.wemakeprice.homework.api.common.RequestType;
import com.wemakeprice.homework.api.dto.ResponseDto;
import com.wemakeprice.homework.api.dto.TextRequestDto;
import com.wemakeprice.homework.api.dto.TextResponseDto;
import com.wemakeprice.homework.api.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditorSupport;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ResponseDto responseDto;

    @GetMapping("/api/{url}/{type}/{outputSize}/quotient-remainder")
    public ResponseEntity<?> quotientAndRemainder(
            @PathVariable String url,
            @PathVariable RequestType type,
            @PathVariable Integer outputSize){

        // 파라미터 유효성 체크
        if(!StringUtils.hasText(url)){
            return responseDto.badRequest("요청 url이 존재하지 않습니다.");
        }
        if(!StringUtils.hasText(type.toString())){
            return responseDto.badRequest("타입 값이 존재하지 않습니다.");
        }
        if(outputSize == null || outputSize <= 0){
            return responseDto.badRequest("묶음 단위가 올바르지 않습니다.");
        }

        log.info("url: {}", url);
        log.info("type: {}", type);
        log.info("ouputSize: {}", outputSize);

        // 해당 타입일 경우 url 유효성 체크
        if(type == RequestType.NO_TAG){
            String decodeUrl = getDecodeUrl(url);
            log.info("Decode url: {}", decodeUrl);

            if(isUrl(decodeUrl)){
                final TextRequestDto requestDto = getTextRequestDto(decodeUrl, type, outputSize);
                final TextResponseDto textResponseDto = itemService.get(requestDto);
                return responseDto.ok(textResponseDto, "성공", HttpStatus.OK);
            }

            // 에러 응답
            return responseDto.badRequest("url 형식이 올바르지 않습니다.");
        }


        TextRequestDto requestDto;
        // text 타입 중 url 형식 값 base64 인코딩된 경우
        if(checkEncodingBase64(url)){
            String decodeUrl = getDecodeUrl(url);
            log.info("Decode url: {}", decodeUrl);
            requestDto = getTextRequestDto(decodeUrl, type, outputSize);
        }else{
            // url형식이 아닌 문자열
            requestDto = getTextRequestDto(url, type, outputSize);
        }

        final TextResponseDto textResponseDto = itemService.get(requestDto);
        return responseDto.ok(textResponseDto, "성공", HttpStatus.OK);
    }

    /**
     * 파라미터 값으로 ENUM 타입 받기 위함
     * @param webdataBinder
     */
    @InitBinder
    private void initBinder(WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(RequestType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(RequestType.fromValue(text));
            }
        });
    }
    /**
     * base64 인코딩 여부
     * @param url
     * @return
     */
    private boolean checkEncodingBase64(String url){
        Pattern pattern = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

    /**
     * url 디코딩
     * @param url
     * @return
     */
    private String getDecodeUrl(String url) {
        byte[] bytes = url.getBytes(StandardCharsets.UTF_8);
        byte[] decode = Base64Utils.decode(bytes);
        return new String(decode, StandardCharsets.UTF_8);
    }

    private TextRequestDto getTextRequestDto(String url, RequestType type, Integer outputSize) {
        return TextRequestDto.builder()
                .value(url)
                .type(type)
                .ouputSize(outputSize)
                .build();
    }

    /**
     * url 유효성 체크
     * @param url
     * @return
     */
    private boolean isUrl(String url) {
        String[] schemes = {"http","https"};
        UrlValidator validator = new UrlValidator(schemes);
        return validator.isValid(url);
    }
}
