package danis;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try {
            TestFramework testFramework = new TestFramework((Class<TestClass>) Class.forName("danis.TestClass"));
            testFramework.runTests();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
