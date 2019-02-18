package com.haohua.service;    /*
 * @author  Administrator
 * @date 2019/2/8
 */


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import java.io.*;

public class FileService {

    //斐波那契数列，走阶梯
    public static void main(String[] args) {
        int f1 = 1;
        int f2 = 2;
        int f3 = 4;
        int result = 0;
        //有一段楼梯台阶有15级台阶，以小明的脚力一步最多只能跨3级，请问小明登上这段楼梯有多少种不同的走法?()
        //关于阶梯的函数，对于n>=4.f(n)=f(n-1)+f(n-2)+f(n-3);每走完一步剩下的走法总和。转换为循环
        for(int i = 4;i<=15;i++){
            result = f1+f2+f3;
            f1 = f2;
            f2 = f3;
            f3 = result;
        }
        System.out.println(result);
    }

    {
        System.out.println("C");
    }

    @Test
    public void test(){
        //
        int countx = 0;
        int x = 65530;
        while (x!=0)
        {
            countx++;
            x = x & (x - 1);
        }
        System.out.println(countx);
    }


}
