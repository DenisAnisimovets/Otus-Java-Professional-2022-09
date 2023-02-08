package com.danis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Numbers {
    private static final Logger logger = LoggerFactory.getLogger(Numbers.class);
    private String waitingThread;
    private HashMap<String, Integer> steps = new HashMap<>();
    private static final String FIRST_THREAD = "first_thread";
    private static final String SECOND_THREAD = "second_thread";

    public Numbers(String waitingThread) {
        this.waitingThread = waitingThread;
    }

    public static void main(String[] args) {
        Numbers numbers = new Numbers(SECOND_THREAD);
        new Thread(() -> numbers.printNumbers(FIRST_THREAD)).start();
        new Thread(() -> numbers.printNumbers(SECOND_THREAD)).start();
    }

    private synchronized void printNumbers(String threadName) {
        while (!Thread.currentThread().isInterrupted()) {
            while (waitingThread.equals(threadName)) {
                try {
                    wait();
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int currentStep = steps.getOrDefault(threadName, 0);
            int currentNumber = compute(currentStep);
            waitingThread = threadName;
            steps.put(threadName, currentStep + 1);
            logger.info(Thread.currentThread().getName() + " " + currentNumber);
            notifyAll();
        }
    }

    private int compute(int currentStep) {
        currentStep = currentStep % 18;
        if(currentStep <= 9) {
            return currentStep + 1;
        } else {
            return 19 - currentStep;
        }
    }
}


