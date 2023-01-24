package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.DateTimeProvider;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorSecond implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        long seconds = dateTimeProvider.getSeconds();
        if(seconds % 2 == 0) {
            throw new RuntimeException("Произошла какая-то ошибка!");
        }
        return message;
    }
}
