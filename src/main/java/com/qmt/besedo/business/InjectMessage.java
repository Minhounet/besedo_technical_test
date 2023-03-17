package com.qmt.besedo.business;

import com.qmt.besedo.model.Message;
import org.springframework.http.ResponseEntity;

public interface InjectMessage {

    ResponseEntity<String> inject(Message message);
}
