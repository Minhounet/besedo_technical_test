package com.qmt.besedo.service;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.response.Response;
import org.springframework.http.ResponseEntity;

public interface InjectMessage {

    ResponseEntity<Response> inject(Message message);
}
