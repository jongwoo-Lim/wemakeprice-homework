package com.wemakeprice.homework.api.controller;

import com.wemakeprice.homework.api.common.RequestType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("존재하지 않는 url 페이지 조회시 발생하는 에러 테스트")
    public void quotientAndRemainder_NotExist_url() throws Exception{
        //Given
        final String url = "https://www.ffdsaf.com";
        final String encodingUrl = encodingBase64(url);
        final RequestType type = RequestType.NO_TAG;
        final int size = 5;
        //When & Then
        mockMvc.perform(get("/api/"+encodingUrl+"/"+type+"/"+size+"/quotient-remainder"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").value("해당 url은 존재하지 않습니다."))

        ;
    }

    @Test
    @DisplayName("url 파라미터 인코딩 되지 않은 경우 테스트")
    public void quotientAndRemainder_Wrong_url() throws Exception{
        //Given
        final String url = "https://www.google.com";
        final RequestType type = RequestType.TEXT;
        final int size = 5;
        //When & Then
        mockMvc.perform(get("/api/"+url+"/"+type+"/"+size+"/quotient-remainder"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("타입 파라미터 값이 잘못된 경우 테스트")
    public void quotientAndRemainder_Wrong_type() throws Exception{
        //Given
        final String url = "https://www.google.com";
        final String encodingUrl = encodingBase64(url);
        final RequestType type = null;
        final int size = 5;
        //When & Then
        mockMvc.perform(get("/api/"+encodingUrl+"/"+type+"/"+size+"/quotient-remainder"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").value("유효하지 않는 타입입니다."))
        ;
    }

    @Test
    @DisplayName("출력 묶음 단위 파라미터 값이 잘못된 경우 테스트")
    public void quotientAndRemainder_Wrong_OuputSize() throws Exception{
        //Given
        final String url = "https://www.google.com";
        final String encodingUrl = encodingBase64(url);
        final RequestType noTag = RequestType.TEXT;
        final int size = 0;
        //When & Then
        mockMvc.perform(get("/api/"+encodingUrl+"/"+noTag+"/"+size+"/quotient-remainder"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").value("묶음 단위가 올바르지 않습니다."))
        ;
    }

    @Test
    @DisplayName("입력 url에 해당하는 페이지 조회후 몫,나머지 구하기 성공 테스트")
    public void quotientAndRemainder_noTag() throws Exception{
        //Given
        final String url = "https://www.google.com";
        final String encodingUrl = encodingBase64(url);
        final RequestType noTag = RequestType.NO_TAG;
        final int size = 5;
        //When & Then
        mockMvc.perform(get("/api/"+encodingUrl+"/"+noTag+"/"+size+"/quotient-remainder"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("data.quotient").value("aeeeeGGGGGggggilllllmoooo"))
                .andExpect(jsonPath("data.remainder").value("oooo"))
        ;
    }

    @Test
    @DisplayName("입력 텍스트에 대한 몫,나머지 구하기 성공 테스트")
    public void quotientAndRemainder_text() throws Exception{
        //Given
        final String url = "https://www.google.com";
        final String encodingUrl = encodingBase64(url);
        final RequestType noTag = RequestType.TEXT;
        final int size = 5;
        //When & Then
        mockMvc.perform(get("/api/"+encodingUrl+"/"+noTag+"/"+size+"/quotient-remainder"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("data.quotient").value("cegghlmooopsttw"))
                .andExpect(jsonPath("data.remainder").value("ww"))
        ;
    }

    private String encodingBase64(String url){
        final byte[] bytes = url.getBytes(StandardCharsets.UTF_8);
        return new String(Base64Utils.encode(bytes));
    }
}