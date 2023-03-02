package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        try {
            Object obj = configClass.getDeclaredConstructor().newInstance();
            List<Method> list = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                    .toList();

            for (Method method : list) {
                AppComponent component = method.getAnnotation(AppComponent.class);
                Object invokedMethod = callMethod(obj, method);
                boolean isNotFound = true;
                try {
                    getAppComponent(component.name());
                } catch (RuntimeException e) {
                    isNotFound = false;
                    appComponents.add(invokedMethod);
                    appComponentsByName.put(component.name(), invokedMethod);

                }

                if(isNotFound) {
                    throw new RuntimeException(method.getName() + " is present");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    private Object callMethod(Object obj, Method method) {
        method.setAccessible(true);
        Parameter[] parameters = method.getParameters();
        Object[] paramList = new Object[parameters.length];

        for (int i = 0; i < paramList.length; i++) {
            paramList[i] = getAppComponent(parameters[i].getType());
        }

        try {
            return method.invoke(obj, paramList);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if(!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> filteredList = appComponents.stream()
                .filter(p -> componentClass.isAssignableFrom(p.getClass())).toList();
        if(filteredList.size() != 1) {
            throw new RuntimeException("Incorrect component count.");
        }

        return (C) filteredList.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if(component == null) {
            throw new RuntimeException("Component not found.");
        }

        return (C) component;
    }
}
