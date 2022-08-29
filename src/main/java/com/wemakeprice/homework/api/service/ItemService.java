package com.wemakeprice.homework.api.service;

import com.wemakeprice.homework.api.dto.TextRequestDto;
import com.wemakeprice.homework.api.dto.TextResponseDto;

public interface ItemService {

    TextResponseDto get(TextRequestDto textRequestDto);
}
