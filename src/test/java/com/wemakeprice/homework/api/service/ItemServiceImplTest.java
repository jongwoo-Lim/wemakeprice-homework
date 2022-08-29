package com.wemakeprice.homework.api.service;

import com.wemakeprice.homework.api.common.RequestType;
import com.wemakeprice.homework.api.dto.TextRequestDto;
import com.wemakeprice.homework.api.dto.TextResponseDto;
import com.wemakeprice.homework.api.model.item.TextItem;
import com.wemakeprice.homework.api.model.processor.TextItemProcessor;
import com.wemakeprice.homework.api.model.reader.ItemReaderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("서비스 로직 실패 테스트")
    public void get_by_zero_Test() {
        // given
        String value = "vzx134!23fsAFDVx";
        int ouputSize = 0;

        TextRequestDto requestDto = TextRequestDto.builder()
                .value(value)
                .type(RequestType.TEXT)
                .ouputSize(ouputSize)
                .build();

        // when & then
        Assertions.assertThrows(ArithmeticException.class, () -> {
            itemService.get(requestDto);
        });
    }

    @Test
    @DisplayName("서비스 로직 성공 테스트")
    public void getTest() {
        // given
        String value = null;
        int ouputSize = 1;

        TextRequestDto requestDto = TextRequestDto.builder()
                .value(value)
                .type(RequestType.TEXT)
                .ouputSize(ouputSize)
                .build();

        TextItem textItem = new TextItem(ItemReaderFactory.createItemReader(requestDto), new TextItemProcessor());
        textItem.load();
        String process = textItem.process();
        TextResponseDto textResponseDto = textItem.getQuotientAndRemainder(process, ouputSize);

        // when
        TextResponseDto result = itemService.get(requestDto);
        // then
        assertThat(textResponseDto.getQuotient()).isEqualTo(result.getQuotient());
        assertThat(textResponseDto.getRemainder()).isEqualTo(result.getRemainder());
    }
}