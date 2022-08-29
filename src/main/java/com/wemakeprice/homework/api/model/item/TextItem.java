package com.wemakeprice.homework.api.model.item;

import com.wemakeprice.homework.api.model.processor.ItemProcessor;
import com.wemakeprice.homework.api.model.reader.ItemReader;
import lombok.Getter;

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

}
