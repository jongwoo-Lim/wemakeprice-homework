package com.wemakeprice.homework.api.dto;

import com.wemakeprice.homework.api.common.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextRequestDto {

    private String value;
    private RequestType type;
}
