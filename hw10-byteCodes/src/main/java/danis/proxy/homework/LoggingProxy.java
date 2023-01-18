package danis.proxy.homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggingProxy {
    private LoggingProxy() {
    }

    public static TestLoggingInterface getProxy(TestLoggingInterface testLoggingInterface) {
        return (TestLoggingInterface) Proxy.newProxyInstance(
                LoggingProxy.class.getClassLoader(),
                new Class[]{TestLoggingInterface.class},
                new LoggingInvocationHandler(testLoggingInterface) {
                }
        );
    }

    static class LoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingInterface;
        private final Set<Method> annotatedMethods;

        LoggingInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.testLoggingInterface = testLoggingInterface;
            annotatedMethods = Arrays.stream(TestLogging.class.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(method -> {
                        try {
                            return TestLoggingInterface.class.getDeclaredMethod(
                                    method.getName(), method.getParameterTypes()
                            );
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        return method;
                    })
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if(annotatedMethods.contains(method)) {
                StringBuilder params = new StringBuilder();
                if(args != null) {
                    if(args.length == 1) {
                        params.append(", param: ");
                    } else if(args.length > 1) {
                        params.append(", params: ");
                    }
                    for (int i = 0; i < args.length; i++) {
                        if(i > 0) {
                            params.append(", ");
                        }
                        params.append(args[i]);
                    }
                }
                System.out.println("Logged method: " + method.getName() + params);
            }
            return method.invoke(testLoggingInterface, args);
        }

        @Override
        public String toString() {
            return "{LoggingInvocationHandler" +
                    "testLoggingInterface=" + testLoggingInterface +
                    '}';
        }
    }
}
