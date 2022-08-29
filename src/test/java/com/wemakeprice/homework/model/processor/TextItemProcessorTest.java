package com.wemakeprice.homework.model.processor;

import com.wemakeprice.homework.api.model.processor.TextItemProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class TextItemProcessorTest {

    @ParameterizedTest
    @MethodSource("testProcessParams")
    public void process(String text, String expectedText){
        String result = new TextItemProcessor().process(text);
        assertThat(result).isEqualTo(expectedText);
    }

    private static Object[] testProcessParams() {
        return new Object[]{
                new Object[]{"fff://!@#AF4@#234한글dvC243FfDds","A2C2D3d3d4F4F4ffffsv"},
                new Object[]{"10482a43249AFvc","A0a1c2F2v344489"},
                new Object[]{"vlAA3peAfaa4lv","A3A4Aaaefllpvv"}
        };
    }

    @Test
    @DisplayName("입력값 영어,숫자 오름차순 정렬 및 교차 출력 테스트")
    public void sortTextAscWithCrossTest(){
        // given
//        String text = "vcxda21A2f3232o4wDFWDSasf";
//        String expectedText = "A1a2a2c2D2D3d3F4ffoSsvWwx";
        String text = "1arsd4sER2adfA";
        String expectedText = "A1a2a4ddEfRrss";
        // when
        String result = sortTextAscWithCross(text);
        System.out.println(result);
        assertThat(result).isEqualTo(expectedText);
    }

    @ParameterizedTest
    @MethodSource("testListParams")
    @DisplayName("영문, 숫자 교차 출력 테스트")
    public void crossingTextTest(List<Integer> integers, List<String> strings, String expectedText){
        // given
//        List<Integer> integers = Arrays.asList(4, 5, 23, 1, 32,2,2,4);
//        List<String> strings = Arrays.asList("a","e","A","f","D","d","a");
//        String expectedText = "A1a2a2D4d4e5f2332";
        // when
        sortList(integers, strings);
        // then
        String result = crossingText(integers, strings);
        assertThat(result).isEqualTo(expectedText);
    }
    private static Object[] testListParams() {
        return new Object[]{
                new Object[]{Arrays.asList(4, 5, 23, 1, 32, 2, 2, 4), Arrays.asList("a","e","A","f","D","d","a","B"), "A1a2a2B4D4d5e23f32"},
                new Object[]{Arrays.asList(4, 5, 23, 1, 32, 2, 2, 4), Arrays.asList("b","b","A","s"), "A1b2b2s4452332"},
                new Object[]{Arrays.asList(7,4,2), Arrays.asList("g","d","G","q","A","q","q"), "A2d4G7gqqq"}
        };
    }

    @Test
    @DisplayName("영문, 숫자 리스트 정렬 테스트")
    public void sortListTest(){
        // given
        List<Integer> integers = Arrays.asList(4, 5, 23, 1, 32);
        List<String> strings = Arrays.asList("a","e","A","f","D","d","a");
        // when
        sortList(integers, strings);
        // then
        integers.forEach(System.out::print);
        System.out.println();
        strings.forEach(System.out::print);
    }

    @Test
    @DisplayName("입력값 영문,숫자로 필터 테스트")
    public void filterAlphabetWithDigitTest(){
        // given
        String text = "avc123VXC한글XZqx123!@#98";
        // when
        String filteredText = filterAlphabetWithDigit(text);

        // then
        boolean result = isAlphabetWithDigit(filteredText);
        System.out.println("filter text: " + filteredText);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해당 값이 영문, 숫자 체크 성공 테스트")
    public void isAlphabetWithDigitTest(){

        // given
        String text = "avc123VXCXZqx12398";
        // when
        boolean result = isAlphabetWithDigit(text);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해당 값이 영문, 숫자 체크 실패 테스트")
    public void isAlphabetWithDigit_fail_Test(){

        // given
        String text = "avc123VXC한글XZqx123!@#98";
        // when
        boolean result = isAlphabetWithDigit(text);

        // then
        assertThat(result).isFalse();
    }


    // ====== 테스트할 메서드 =====
    // ========================
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
     * 숫자, 영문 리스트를 받아 정렬한다
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
