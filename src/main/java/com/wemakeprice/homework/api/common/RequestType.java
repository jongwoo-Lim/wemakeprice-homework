package com.wemakeprice.homework.api.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestType {
    NO_TAG("no_tag"), TEXT("text"), DEFAULT("default");

    private String value;
}
