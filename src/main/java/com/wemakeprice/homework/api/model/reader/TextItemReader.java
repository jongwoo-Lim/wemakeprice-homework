package com.wemakeprice.homework.api.model.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TextItemReader implements ItemReader {

    private String value;

    @Override
    public String read() {
        return getValue();
    }
}
