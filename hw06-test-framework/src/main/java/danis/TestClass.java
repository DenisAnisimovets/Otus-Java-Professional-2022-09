package danis;

import danis.annotation.After;
import danis.annotation.Before;
import danis.annotation.Test;

public class TestClass {
    @Before
    public void before() {
        System.out.println("before");
    }

    @Before
    public void before1 () {
        System.out.println("before1");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    @Test
    public void Test1() throws NoSuchMethodException {
        System.out.println("test1");
        throw new NoSuchMethodException();
    }

    @Test
    public void  Test2() throws NoSuchMethodException {
        System.out.println("test2");
        throw new NoSuchMethodException();
    }

    @Test
    public void  Test3() {
        System.out.println("test3");
    }
}
