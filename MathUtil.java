package com.xyjsoft.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/** 
 * 类名: MathUtil <br/> 
 * 类描述: 数学辅助工具类. <br/> 
 * date: 2018-3-5 上午8:53:28 <br/> 
 * 
 * @author 王浩伟 
 * @version 1.0
 * @since JDK 1.7 
 */  
public class MathUtil {
	private final static String SCAL_ERROR="The   scale   must   be   a   positive   integer   or   zero";
	/**
	 * permutation 数组间组合
	 * @param inputList 例子：[
	 *		  					[A1,A2,...],
	 *		  					[B1,B2,...]
	 *		                ]
	 * @return [A1B1,A1B2,...]
	 * */
	public static List<String> permutation(List<List<String>> inputList){
	    List<String> resList = new ArrayList<String>();
	    permutationInt(inputList, resList, 0, 
	            new String[inputList.size()]);
	    return resList;
	}
    /**递归算法*/
	private static void permutationInt(List<List<String>> inputList, List<String> resList,
	        int ind, String[] arr) {
	    if(ind == inputList.size()){
	    	StringBuffer tt = new StringBuffer();
	    	for(String t : arr){
	    		tt.append(t + ",");
	    	}
	    	if(tt.length() > 0){
	    		tt.deleteCharAt(tt.length()-1);
	    	}
	    	resList.add(tt.toString());
	        return;
	    }

	    for(String c: inputList.get(ind)){
	        arr[ind] = c;
	        permutationInt(inputList, resList, ind + 1, arr);
	    } 
	}
	public static void main(String args[]){
		List<List<String>> inputList = new ArrayList<List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("A1");
		list1.add("A2");
		list1.add("A3");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("B1");
		list2.add("B2");
		list2.add("B3");
		
		List<String> list3 = new ArrayList<String>();
		list3.add("C1");
		list3.add("C2");
		list3.add("C3");
		
		inputList.add(list1);
		inputList.add(list2);
		inputList.add(list3);
		
		List<String> listout = permutation(inputList);
		for(String t : listout){
			System.out.println(t);
		}
	}
	
	  
    /**
     * DEF_DIV_SCALE:默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;  
  
    /** 
     * 提供精确的加法运算。 
     *  
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */  
  
    public static double add(Double v1, Double v2) {  
    	
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    }
	/**
	 * 提供精确的加法运算。
	 * @param list 相加
	 * @return 和
	 */
	public static double add(List<Double> list){
		BigDecimal tmp =new BigDecimal("0");
		for (Double a : list) {
			if(a==null||a==0){
				continue;
			}
			tmp = tmp.add(new BigDecimal(Double.toString(a)));
		}
		return tmp.doubleValue();
	}
    /**
     * 三个参数相加。
     *
     * @param v1 被加数
     * @param v2 加数
     * @param v3 加数
     * @return 三个参数的和
     */

    public static double add(Double v1, Double v2,Double v3) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        return b1.add(b2).add(b3).doubleValue();
    }
    public static Double maxMath(Double a) {

		if (a != null) {
			return a;
		}
		return 0d;
	}
    /**
     * 四个参数相加。
     *
     * @param v1 被加数
     * @param v2 加数
     * @param v3 加数
     * @param v4 加数
     * @return 四个参数的和
     */

    public static double add(Double v1, Double v2,Double v3,Double v4) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
    	if(v4 == null){
    		v4=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        return b1.add(b2).add(b3).add(b4).doubleValue();
    }
    public static double add(double v1, double v2,double v3,double v4,double v5, double v6,double v7,double v8,double v9, double v10,double v11,double v12) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        BigDecimal b7 = new BigDecimal(Double.toString(v7));
        BigDecimal b8 = new BigDecimal(Double.toString(v8));
        BigDecimal b9 = new BigDecimal(Double.toString(v9));
        BigDecimal b10 = new BigDecimal(Double.toString(v10));
        BigDecimal b11 = new BigDecimal(Double.toString(v11));
        BigDecimal b12 = new BigDecimal(Double.toString(v12));
        return b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11).add(b12).doubleValue();
    }
    public static double add(Double v1, Double v2,Double v3,Double v4,Double v5, Double v6) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
    	if(v4 == null){
    		v4=0d;	
    	}
    	if(v5 == null){
    		v5=0d;	
    	}
    	if(v6 == null){
    		v6=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        return b1.add(b2).add(b3).add(b4).add(b5).add(b6).doubleValue();
    }
    /**
     * 提供精确的减法运算。 
     *  
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */  
  
    public static double sub(Double v1, Double v2) {  
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.subtract(b2).doubleValue();  
    }  
    /**
     * v1-v2-v3
     *
     * @param v1 被减数
     * @param v2 减数
     * @param v3 减数
     * @return 三个参数的差
     */

    public static double sub(Double v1, Double v2,Double v3) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        return b1.subtract(b2).subtract(b3).doubleValue();
    }
    /**
     * v1-v2-v3-
     *
     * @param v1 被减数
     * @param v2 减数
     * @param v3 减数
     * @param v4 减数
     * @return 四个参数的差
     */

    public static double sub(Double v1, Double v2,Double v3,Double v4) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
    	if(v4 == null){
    		v4=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        return b1.subtract(b2).subtract(b3).subtract(b4).doubleValue();
    }
    public static double sub(Double v1, Double v2,Double v3,Double v4,Double v5, Double v6) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
    	if(v4 == null){
    		v4=0d;	
    	}
    	if(v5 == null){
    		v5=0d;	
    	}
    	if(v6 == null){
    		v6=0d;	
    	}
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        return b1.add(b2).add(b3).add(b4).add(b5).add(b6).doubleValue();
    }
    /** 
     * 提供精确的乘法运算。 
     *  
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */  
  
    public static double mul(Double v1, Double v2) {  
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.multiply(b2).doubleValue();  
    }

    /**
     * 精确地 v1*v2*v3。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @param v3 乘数
     * @return 三个参数的积
     */

    public static double mul(Double v1, Double v2,Double v3) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
    	
        return mul(mul(v1,v2),v3);
    }
    /**
     * 精确的v1*v2*v3*v4。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @param v3 乘数
     * @param v4 乘数
     * @return 四个参数的积
     */

    public static double mul(Double v1, Double v2,Double v3,Double v4) {
    	if(v1 == null){
    		v1=0d;	
    	}
    	if(v2 == null){
    		v2=0d;	
    	}
    	if(v3 == null){
    		v3=0d;	
    	}
    	if(v4 == null){
    		v4=0d;	
    	}
        return mul(mul(mul(v1,v2),v3),v4);
    }


	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 提供（相对）精确的除法运算。
	 *
	 * @param v1 被除数
	 * @param v2 除数
	 * @param v3 除数
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2,double v3) {
		return div(v1,v2,v3,DEF_DIV_SCALE);
	}
	/**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。 
     *  
     * @param v1 被除数
     * @param v2 除数
     * @param v3 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商
     */  
  
    public static double div(double v1, double v2,double v3, int scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(SCAL_ERROR);
        }  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal divide = b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
		BigDecimal divide1 = divide.divide(b3, scale, BigDecimal.ROUND_HALF_UP);
		return divide1.doubleValue();
		//return b1.divide(b2, BigDecimal.ROUND_HALF_UP).divide(b3, scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	/**
	 * 提供（相对）精确的除法运算。
	 *
	 * @param v1 被除数
	 * @param v2 除数
	 * @param v3 除数
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2,double v3,double v4) {
		return div(v1,v2,v3,v4,DEF_DIV_SCALE);
	}
	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 *
	 * @param v1 被除数
	 * @param v2 除数
	 * @param v3 除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2,double v3,double v4, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(SCAL_ERROR);
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		BigDecimal divide = b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
		BigDecimal divide1 = divide.divide(b3, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
		BigDecimal divide2 = divide1.divide(b4, scale, BigDecimal.ROUND_HALF_UP);
		return divide2.doubleValue();
	}

	/**
     * 提供精确的小数位四舍五入处理。 
     *  
     * @param v 需要四舍五入的数字
     * @param scale  小数点后保留几位
     * @return 四舍五入后的结果
     */  
  
    public static double round(double v, int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException(  
                    "The   scale   must   be   a   positive   integer   or   zero");  
        }  
        BigDecimal b = new BigDecimal(Double.toString(v));  
        BigDecimal one = new BigDecimal("1");  
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
    /**
     * <b>类名:MathUtil</b><br>
     * <b>方法名:formatDouble</b><br>
     * <b>详细描述</b>:保留double类型的 小数后几位(金额)
     * <br>
     * <br>
     * @param from
     * @param scale
     * @param ifRound 是否四舍五入 
     * @return
     * String
     * @exception 
     * @since  1.0.0
    */
    public static String formatDouble(double from,int scale,Boolean ifRound){
    	double num;
    	if(ifRound){
    		num=round(from, scale);
    	}else{
    		num=from;
    	}
    	
    	if(scale<0){
    		throw new RuntimeException("保留位数必须大于0");
    	}
    	String format="###,##0.";
    	if(scale==0){
    		format="#";
    	}
    	for(int i=0;i<scale;i++){
    		format=format+"0";
    	}
    	DecimalFormat  df  =new DecimalFormat(format);  
    	String str = df.format(num);
    	return str;
    } 
}
