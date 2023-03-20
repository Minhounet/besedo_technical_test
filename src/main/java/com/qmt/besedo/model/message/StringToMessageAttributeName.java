package com.qmt.besedo.model.message;

import com.qmt.besedo.exception.InvalidAttributeException;
import io.micrometer.common.lang.Nullable;
import io.vavr.control.Try;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMessageAttributeName implements Converter<String, MessageAttributeName> {
    @Override
    public MessageAttributeName convert(@Nullable String attribute) {
        if (null == attribute) {
            throw new InvalidAttributeException("Attribute cannot be null");
        } else {
            return Try.of(() -> MessageAttributeName.valueOf(attribute.toUpperCase()))
                    .getOrElseThrow(() -> new InvalidAttributeException("Invalid attribute: " + attribute));
        }
    }
}
