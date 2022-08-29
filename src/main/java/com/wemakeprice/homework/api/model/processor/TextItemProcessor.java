package com.wemakeprice.homework.api.model.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class TextItemProcessor implements ItemProcessor{

    @Override
    public String process(String text) {
        // 입력값이 영문,숫자인 지 체크
        if(isAlphabetWithDigit(text)){
            // 오름차순 정렬 및 교차
            return sortTextAscWithCross(text);
        }

        // 영문,숫자 필터
        String filteredText = filterAlphabetWithDigit(text);
        // 오름차순 정렬 및 교차
        return sortTextAscWithCross(filteredText);
    }

    /**
     * 영문, 숫자인지 체크
     * @param text
     * @return
     */
    private boolean isAlphabetWithDigit(String text){
        String pattern = "^[a-zA-Z0-9]*$";
        return Pattern.matches(pattern, text);
    }

    // 영문, 숫자 필터
    private String filterAlphabetWithDigit(String text){
        String regex = "[^a-zA-Z0-9]";
        return text.replaceAll(regex, "");
    }

    // 오름차순 정렬 및 교차
    private String sortTextAscWithCross(String text){
        final char[] chars = text.toCharArray();
        List<Integer> integers = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for(char c: chars){
            if(Character.isDigit(c)){
                integers.add(Character.getNumericValue(c));
            }else{
                strings.add(String.valueOf(c));
            }
        }

        sortList(integers, strings);
        return crossingText(integers, strings);
    }

    /**
     * 숫자, 영문 리스트를 받아 정렬하는 함수
     * @param integers
     * @param strings
     */
    private void sortList(List<Integer> integers, List<String> strings) {
        // 숫자 오름차순
        Collections.sort(integers);
        // 영문 오름차순 (대문자 후 소문자 정렬)
        Collections.sort(strings, (o1, o2) -> {
            if(o1.equalsIgnoreCase(o2)){
                if(o1.toUpperCase().equals(o1)){
                    return -1;
                }else{
                    return 1;
                }
            }else{
                return o1.compareToIgnoreCase(o2);
            }
        });
    }

    /**
     * 정렬된 숫자, 영문 교차 출력 함수
     * @param integers
     * @param strings
     * @return
     */
    private String crossingText(List<Integer> integers, List<String> strings){
        StringBuilder sb = new StringBuilder();
        final int intSize = integers.size();
        final int strSize = strings.size();

        final boolean moreStrSize = strSize > intSize;
        final int minSize = Math.min(intSize, strSize);

        // 문자열이 더 긴 경우
        if(moreStrSize){
            for(int i=0 ;i < strings.size(); i++){
                sb.append(strings.get(i));
                if(i >= minSize){
                    continue;
                }
                sb.append(integers.get(i));
            }
        }else{
            for(int i=0 ;i < integers.size(); i++){
                if(i < minSize){
                    sb.append(strings.get(i));
                }
                sb.append(integers.get(i));
            }
        }

        return sb.toString();
    }
}
