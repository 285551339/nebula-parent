package com.xy.nebula.commons.utils.random;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Description TODO
 * @Author chenxudong
 * @Date 2019/10/16 19:30
 */
public class NumUtil {

    /**
     * 获取固定长度的随机数，在前面补0
     * @param strLength 长度
     * @return 随机数
     */
    public static String getFixLengthString(int strLength) {
        Random rm = new Random();
        //获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        //将获得的获得随机数转化为字符串
        String fixLengthString = String.valueOf(pross);
        //返回固定的长度的随机数
        return fixLengthString.substring(1, strLength + 1);
    }

    /**
     * 生成指定范围内随机整数
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        int result = random.nextInt(max) % (max-min+1) + min;
        return result;
    }

    /**
     * 生成范围内随机数，同时随机数总和固定（平均数在min和max之间，满足正态分布）
     * @param total
     * @param count
     * @param max
     * @param min
     * @return
     */
    public static int[] getScopeRandomNumber(int total, int count, int max, int min) {
        if (total == 0 || count == 0 || max == 0) {
            return null;
        }
        int average = total/count;
        //平均数必须在min和max之间
        BigDecimal averageBigDecimal = div(new BigDecimal(total), new BigDecimal(count), 1);
        if (averageBigDecimal.compareTo(new BigDecimal(max)) > 0 || averageBigDecimal.compareTo(new BigDecimal(min)) < 0) {
            return null;
        }
        int[] result = new int[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            if (random.nextInt(max - min + 1) + min > average) {
                // 在平均线上减钱
                int temp = min + xRandom(min, average);
                result[i] = temp;
                total -= temp;
            } else {
                // 在平均线上加钱
                int temp = max - xRandom(average, max);
                result[i] = temp;
                total -= temp;
            }
        }
        // 如果剩余金额大于0，则尝试加到已分配的金额中，如果加不进去，则尝试下一个。
        while (total > 0) {
            for (int i = 0; i < count; i++) {
                if (total > 0 && result[i] < max) {
                    result[i]++;
                    total--;
                }
            }
        }
        // 如果剩余金额为负数，从已分配的金额中抽取
        while (total < 0) {
            for (int i = 0; i < count; i++) {
                if (total < 0 && result[i] > min) {
                    result[i]--;
                    total++;
                }
            }
        }
        return result;
    }

    /**
     * 生成范围内随机数，同时随机数总和固定（平均数在min和max之间，满足正态分布）
     * @param total
     * @param count
     * @param max
     * @param min
     * @return string数组
     */
    public static String[] getScopeRandomNumberString(int total, int count, int max, int min) {
        int[] scopeRandomNumberArr = getScopeRandomNumber(total, count, max, min);
        if (scopeRandomNumberArr != null) {
            String[] numberStringArr = new String[scopeRandomNumberArr.length];
            for (int i = 0; i < scopeRandomNumberArr.length; i++) {
                numberStringArr[i] = String.valueOf(scopeRandomNumberArr[i]);
            }
            return numberStringArr;
        }
        return null;
    }

    /**
     * 生成[1,max - min]的随机数
     * @param max
     * @param min
     * @return
     */
    private static int xRandom(int max, int min) {
        Random random = new Random();
        double powDouble = Math.pow(new Double(max - min), 2);
        int powInt = new Double(powDouble).intValue();
        int randomNum = random.nextInt(powInt + 1);
        return new Double(Math.sqrt(randomNum)).intValue();
    }

    /**
     * 加
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal add(BigDecimal v1, BigDecimal v2, int scale) {
        return v1.add(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * 乘
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 除
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static BigDecimal div(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);//四舍五入,保留scale位小数
    }

    /**
     * 除
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);//四舍五入,保留scale位小数
    }

    public static void main(String[] args) {
        int[] bonus = getScopeRandomNumber(10100, 100, 999, 1);
        int total = 0;
        for (int i : bonus) {
            total += i;
            System.out.println(i);
        }
        System.out.println("total:" + total);
        System.out.println("count:" + bonus.length);
    }

    /**
     * @Title:
     * @Description: 根据单位归整
     * @param number
     * @param unit
     * @return
     * @throws
     * @author qiaomengnan
     * @date 2020/04/23 02:59:04
     */
    public static Integer getQuantity(Integer number, Integer unit) {
        if(number == null || unit == null) {
            return 0;
        }
        return number % unit != 0 ? (number/unit) + 1 : number / unit;
    }

}
