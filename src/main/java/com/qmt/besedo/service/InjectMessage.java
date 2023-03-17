package com.qmt.besedo.service;

import com.qmt.besedo.model.Message;
import org.springframework.http.ResponseEntity;

public interface InjectMessage {

    ResponseEntity<String> inject(Message message);
}
