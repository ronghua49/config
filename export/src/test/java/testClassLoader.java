public class testClassLoader {

    public static void main(String[] args) {
        System.out.print(fun1());
    }
    //在fun1()值返回之前，执行finally方法
    public static String fun1() {
        try {
            System.out.print("A");
            return fun2();
        } finally {
            System.out.print("B");
        }
    }

    public static String fun2() {
        System.out.print("C");
        return "D";
    }
}
