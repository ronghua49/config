import org.junit.Test;

public class testConstantPool {

    @Test
    public void test(){
        String a = "abc";
        String b = new String("abc");
        String c = new String ("abc");
        String d = "abc";
        System.out.println(a==b);
        System.out.println(b==c);
        System.out.println(a==d);
    }


    @Test
    public void test2(){

        String text = "学好javaee，要先学好javase";

        int i = text.indexOf('好', 2);
        System.out.println(i);

    }


}
