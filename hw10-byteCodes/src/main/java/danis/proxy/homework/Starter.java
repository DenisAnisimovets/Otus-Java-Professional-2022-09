package danis.proxy.homework;

public class Starter {
    public static void main(String[] args) {
        TestLoggingInterface testLoggingInterface = LoggingProxy.getProxy(new TestLogging());
        testLoggingInterface.calculation();
        testLoggingInterface.calculation(1);
        testLoggingInterface.calculation(1, 1);
    }
}
