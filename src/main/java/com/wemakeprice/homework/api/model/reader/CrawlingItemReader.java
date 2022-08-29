package com.wemakeprice.homework.api.model.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingItemReader implements ItemReader {

    private String url;

    @Override
    public String read() {
        final Connection connect = Jsoup.connect(url);
        Document document = null;
        try {
            document = connect.get();
        } catch (IOException e) {
            // 예외처리
        }

        return document.text().replaceAll("\\s", "");
    }
}
