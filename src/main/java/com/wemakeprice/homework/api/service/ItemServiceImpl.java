package com.wemakeprice.homework.api.service;

import com.wemakeprice.homework.api.dto.TextRequestDto;
import com.wemakeprice.homework.api.dto.TextResponseDto;
import com.wemakeprice.homework.api.model.item.TextItem;
import com.wemakeprice.homework.api.model.processor.TextItemProcessor;
import com.wemakeprice.homework.api.model.reader.ItemReader;
import com.wemakeprice.homework.api.model.reader.ItemReaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService{


    @Override
    public TextResponseDto get(TextRequestDto textRequestDto) {
        ItemReader itemReader = ItemReaderFactory.createItemReader(textRequestDto);
        final TextItem item = new TextItem(itemReader, new TextItemProcessor());

        // 데이터 읽기
        item.load();
        // 데이터 가공
        log.info("item value: {}", item.getValue());
        final String processedText = item.process();

        log.info("process Text: {}", processedText);
        log.info("process Text Len: {}", processedText.length());

        // 몫,나머지 결과
        return item.getQuotientAndRemainder(processedText, textRequestDto.getOuputSize());
    }


}
