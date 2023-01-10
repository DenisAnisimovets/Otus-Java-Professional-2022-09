package danis;

import danis.annotation.After;
import danis.annotation.Before;
import danis.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {

    private final Class<TestClass> clazz;
    private final List<Method> beforeMethods;
    private final List<Method> testMethods;
    private final List<Method> afterMethods;

    public TestFramework(Class<TestClass> clazz) {
        this.clazz = clazz;
        beforeMethods = new ArrayList<>();
        testMethods = new ArrayList<>();
        afterMethods = new ArrayList<>();
    }

    public void runTests() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        collectAnnotations();
        executeTests();
    }

    private void executeTests() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int passed = 0;
        int failed = 0;
        for (Method testMethod : testMethods) {
            Object testObject = clazz.getConstructor().newInstance();
            for (Method beforeMethod : beforeMethods) {
                beforeMethod.invoke(testObject);
            }
            try {
                testMethod.invoke(testObject);
                passed++;
                System.out.println("Test " + testMethod.getName() + " passed");
            } catch ( Exception r) {
                failed++;
                System.out.println("Test " + testMethod.getName() + " failed: " + r.getMessage());
            }
            for (Method afterMethod : afterMethods) {
                afterMethod.invoke(testObject);
            }
        }
        System.out.println("Passed: " + passed + ", failed: " + failed + ". Total: " + (passed + failed));
    }

    private void collectAnnotations() {
        for (Method method : clazz.getMethods()) {
            if(method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if(method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if(method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }
    }
}
