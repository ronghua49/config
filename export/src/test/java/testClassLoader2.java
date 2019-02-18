public class testClassLoader2 {
    //classLoader 加载类不会导致类的初始化，clazz.forName 会初始化类
    public static void main(String[] args) throws Exception{
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        Class clazz=classLoader.loadClass("A");
        System.out.print("Test");
        clazz.forName("A");
    }
}

class A{
    static {
        System.out.print("A");
    }
}
