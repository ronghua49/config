public class testClassLoader3 {
//子类调用父类的静态变量，只会加载父类，不加载子类。
/*    public static void main(String[] args) {
        System.out.print(B.c);
    }
}

class A1 {
    public static String c = "C";
    static {
        System.out.print("A");
    }
}

class B extends A1{
    static {
        System.out.print("B");
    }*/

//调用类的静态变量不会加载该类
//    public static void main(String[] args) {
//        System.out.print(B.c);
//    }
//}
//
//class A2 {
//    static {
//        System.out.print("A");
//    }
//}
//
//class B extends A2{
//    static {
//        System.out.print("B");
//    }
//    public final static String c = "C";
//}
//对一个new 的变量的域进行静态常亮化。访问该变量时，会导致该类的加载和初始化
//若常亮是编译期的常量，则不会导致类的加载和初始化
    public static void main(String[] args) {
        System.out.println(Test2.a);
    }
}
class Test2{
    public static final String a= new String("JD");
    public static final String a1= "JD";
    static {
        System.out.print("OK");
    }
}
