package com.qmt.besedo.model.operator;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSearchOperator implements Converter<String, SearchOperator> {
    @Override
    public SearchOperator convert(String source) {
        return SearchOperator.valueOf(source.toUpperCase());
    }
}
