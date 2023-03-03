package com.danis.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.danis.domain.Message;

public interface DataStore {

    Mono<Message> saveMessage(Message message);

    Flux<Message> loadMessages(String roomId);

    Flux<Message> loadAllMessages(String roomId);
}
