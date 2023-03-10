package com.danis;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class ExchangeServiceImpl extends ExchangeServiceGrpc.ExchangeServiceImplBase {
    @Override
    public void send(ExchangeMessageRequest request, StreamObserver<ExchangeMessageResponse> responseObserver) {
        System.out.println("request.getValue() = " + request.getFirstValue());
        System.out.println("request.getValue() = " + request.getLastValue());

        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        for (int counter = firstValue; counter < lastValue; counter++) {
            try {
                ExchangeMessageResponse response = ExchangeMessageResponse.newBuilder()
                        .setValue(firstValue + counter)
                        .build();

                System.out.println("Send response = " + response);
                responseObserver.onNext(response);

                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        responseObserver.onCompleted();
    }
}
