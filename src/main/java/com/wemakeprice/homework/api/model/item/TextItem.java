package com.wemakeprice.homework.api.model.item;

import com.wemakeprice.homework.api.dto.TextResponseDto;
import com.wemakeprice.homework.api.model.processor.ItemProcessor;
import com.wemakeprice.homework.api.model.reader.ItemReader;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class TextItem {

    private String value;
    private ItemReader itemReader;
    private ItemProcessor itemProcessor;

    public TextItem(ItemReader itemReader, ItemProcessor itemProcessor) {
        this.itemReader = itemReader;
        this.itemProcessor = itemProcessor;
    }

    /**
     * 입력 데이터 읽기
     */
    public void load(){
        this.value = this.itemReader.read();
    }

    /**
     * 읽어온 데이터 가공
     * @return
     */
    public String process(){
        return itemProcessor.process(this.value);
    }

    /**
     * 몫, 나머지 구하기
     * @param text
     * @param outputSize
     * @return
     */
    public TextResponseDto getQuotientAndRemainder(String text, int outputSize){

        if(StringUtils.hasText(text)){
            final int textLen = text.length();

            final int quotient = textLen / outputSize;

            final String strQuotient = text.substring(0, (outputSize * quotient));
            final String strRemainder = text.substring(outputSize * quotient);

            return TextResponseDto.builder()
                    .quotient(strQuotient)
                    .remainder(strRemainder)
                    .build();
        }

        return TextResponseDto.builder()
                .quotient("")
                .remainder("")
                .build();
    }

}
