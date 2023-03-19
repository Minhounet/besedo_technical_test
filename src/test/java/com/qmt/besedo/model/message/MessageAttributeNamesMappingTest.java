package com.qmt.besedo.model.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageAttributeNamesMappingTest {

    @Test
    void freeze_param_name() {
        Assertions.assertEquals("id", MessageAttributeNamesMapping.ID.getName());
        Assertions.assertEquals("email", MessageAttributeNamesMapping.EMAIL.getName());
        Assertions.assertEquals("title", MessageAttributeNamesMapping.TITLE.getName());
        Assertions.assertEquals("body", MessageAttributeNamesMapping.BODY.getName());
    }
}