package com.qmt.besedo.model.operator;

import com.qmt.besedo.exception.InvalidAttributeException;
import com.qmt.besedo.exception.InvalidOperatorException;
import io.micrometer.common.lang.Nullable;
import io.vavr.control.Try;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSearchOperator implements Converter<String, SearchOperator> {
    @Override
    public SearchOperator convert(@Nullable String operator) {
        if (null == operator) {
            throw new InvalidAttributeException("Operator cannot be null");
        } else {
            return Try.of(() -> SearchOperator.valueOf(operator.toUpperCase()))
                    .getOrElseThrow(() -> new InvalidOperatorException("Invalid operator: " + operator));
        }
    }
}
