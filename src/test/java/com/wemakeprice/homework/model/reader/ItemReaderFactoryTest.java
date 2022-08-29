package com.wemakeprice.homework.model.reader;

import com.wemakeprice.homework.api.common.RequestType;
import com.wemakeprice.homework.api.dto.TextRequestDto;
import com.wemakeprice.homework.api.model.reader.CrawlingItemReader;
import com.wemakeprice.homework.api.model.reader.ItemReader;
import com.wemakeprice.homework.api.model.reader.ItemReaderFactory;
import com.wemakeprice.homework.api.model.reader.TextItemReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemReaderFactoryTest {

    @Test
    @DisplayName("크롤링 html 태그 제거 타입 Reader 객체 생성")
    public void createCrawlingItemReaderTest(){

        //Given
        TextRequestDto requestDto = TextRequestDto.builder()
                .type(RequestType.NO_TAG)
                .value("test text..")
                .build();
        //when
        ItemReader itemReader = ItemReaderFactory.createItemReader(requestDto);

        //then
        assertThat(itemReader instanceof CrawlingItemReader).isTrue();
    }

    @Test
    @DisplayName("텍스트 값 타입 Reader 객체 생성")
    public void createTextItemReaderTest(){

        //Given
        TextRequestDto requestDto = TextRequestDto.builder()
                .type(RequestType.TEXT)
                .value("test text..")
                .build();
        //when
        ItemReader itemReader = ItemReaderFactory.createItemReader(requestDto);

        //then
        assertThat(itemReader instanceof TextItemReader).isTrue();
    }

    @Test
    @DisplayName("그 외 타입 Reader 객체 생성 실패")
    public void createItemReaderTest(){

        //Given
        TextRequestDto requestDto = TextRequestDto.builder()
                .type(RequestType.DEFAULT)
                .value("test text..")
                .build();
        //when & then

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ItemReader itemReader = ItemReaderFactory.createItemReader(requestDto);
        });
    }
}
