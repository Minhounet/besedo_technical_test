package com.qmt.besedo.model.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageAttributeNameTest {

    @Test
    void freeze_param_name() {
        Assertions.assertEquals("id", MessageAttributeName.ID.getName());
        Assertions.assertEquals("email", MessageAttributeName.EMAIL.getName());
        Assertions.assertEquals("title", MessageAttributeName.TITLE.getName());
        Assertions.assertEquals("body", MessageAttributeName.BODY.getName());
    }
}