package com.haohua.service;    /*
 * @author  Administrator
 * @date 2019/2/13
 */

public class Main {

    public static void main(String[] args) {
        System.out.println(count(100));

    }

    public static  int count(int cap){
        int currCap = 10;
        int count=0;
        while(currCap<=cap){
            currCap =  ((currCap * 3) / 2) + 1;
            count++;
        }
        return count;
    }

}