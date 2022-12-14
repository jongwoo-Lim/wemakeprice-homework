package com.wemakeprice.homework.api.model.reader;

import com.wemakeprice.homework.api.dto.TextRequestDto;

public class ItemReaderFactory {

    public static ItemReader createItemReader(TextRequestDto textRequestDto){
        switch (textRequestDto.getType()) {
            case NO_TAG: return  new CrawlingItemReader(textRequestDto.getValue());
            case TEXT: return new TextItemReader(textRequestDto.getValue());
            default: throw new IllegalArgumentException("유효하지 않는 타입입니다.");
        }
    }
}
