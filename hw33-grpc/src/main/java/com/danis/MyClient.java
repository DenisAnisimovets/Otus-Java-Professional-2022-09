package com.danis;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyClient {
    private static final int maxValue = 50;
    private static final int firstValueInRequest = 1;
    private static final int lastValueInRequest = 30;
    private static final AtomicInteger valueFromServer = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = runStub();

        readAndIncrement();

        channel.shutdown();
    }

    private static void readAndIncrement() throws InterruptedException {
        for (int currentValue = 0; currentValue < maxValue; currentValue++) {
            int localVal = valueFromServer.getAndSet(0);

            System.out.println("currentValue before add increment = " + currentValue);
            System.out.println("valueFromServer = " + localVal);

            currentValue = currentValue + localVal + 1;
            if (valueFromServer.get() > 0) valueFromServer.set(0);
            System.out.println("currentValue after add increment = " + currentValue);
            System.out.println();
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }
    }

    private static ManagedChannel runStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8088)
                .usePlaintext()
                .build();

        ExchangeMessageRequest request = ExchangeMessageRequest.newBuilder()
                .setFirstValue(firstValueInRequest)
                .setLastValue(lastValueInRequest)
                .build();

        ExchangeServiceGrpc.ExchangeServiceStub stub = ExchangeServiceGrpc.newStub(channel);

        stub.send(request, new StreamObserver<>() {
            @Override
            public void onNext(ExchangeMessageResponse value) {
                System.out.println("Received value form server= " + value.getValue());
                valueFromServer.set(value.getValue());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("DoneЪ");
            }
        });

        return channel;
    }
}
