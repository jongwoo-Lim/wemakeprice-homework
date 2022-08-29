package com.wemakeprice.homework.api.model.reader;

import com.wemakeprice.homework.api.dto.TextRequestDto;

public class ItemReaderFactory {

    public static ItemReader createItemReader(TextRequestDto textRequestDto){
        return switch (textRequestDto.getType()) {
            case NO_TAG -> new CrawlingItemReader(textRequestDto.getValue());
            case TEXT -> new TextItemReader(textRequestDto.getValue());
            default -> throw new IllegalArgumentException("invalid type..");
        };
    }
}
