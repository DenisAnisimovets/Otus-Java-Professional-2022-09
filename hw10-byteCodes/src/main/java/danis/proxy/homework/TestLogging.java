package danis.proxy.homework;

public class TestLogging implements TestLoggingInterface{

    @Override
    public void calculation() {
        System.out.println("0:___");
    }

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("1: " + param);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("2: " + param1 + " " + param2);
    }
}
