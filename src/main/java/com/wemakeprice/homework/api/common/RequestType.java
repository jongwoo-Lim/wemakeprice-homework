package com.wemakeprice.homework.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequestType {
    NO_TAG("no_tag"), TEXT("text"), DEFAULT("default");

    private String value;

    public static RequestType fromValue(String value) {
        for (RequestType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return DEFAULT;
    }
}
