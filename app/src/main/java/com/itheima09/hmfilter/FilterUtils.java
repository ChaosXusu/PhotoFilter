package com.itheima09.hmfilter;

import android.graphics.Color;

/**
 * Created by sanpi on 2016/6/13.
 */

public class FilterUtils {
    public static void blackFilter(int[] pixels) {
        //遍历所有的像素点
        for (int i = 0; i < pixels.length; i++) {
            int argb = pixels[i];//一个像素点的值 #ccff0000
            //获取三原色
            int alpha = Color.alpha(argb);
            int red = Color.red(argb);
            int green = Color.green(argb);
            int blue = Color.blue(argb);
            //更改三原色 新的三原色 = (r + g + b)/3
            int avg = (red + green + blue) / 3;
            red = green = blue = avg;
            //将三原色合成为像素点值
            pixels[i] = Color.argb(alpha, red, green, blue);
        }
    }

    public static void negativeFilter(int[] pixels, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = j + i * width;//像素点的下标值
                int argb = pixels[index];//一个像素点
                //获取三原色
                int alpha = Color.alpha(argb);
                int red = Color.red(argb);
                int green = Color.green(argb);
                int blue = Color.blue(argb);
                //修改三原色 新的三原色 = 255 - 新的三原色
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;
                //合成三原色
                pixels[index] = Color.argb(alpha, red, green, blue);
            }

        }


    }

    public static void sharpenFiler(int[] pixels) {
        //计算对比度 C*= (100.0 + degree)/100.0  ,degree 取[-100,100] 一般取52
        double c = Math.pow((100.0 + 52.0) / 100.0, 2);
        //遍历数组
        for (int i = 0; i < pixels.length; i++) {
            int argb = pixels[i];//每一个像素点
            //取出三原色
            int alpha = Color.alpha(argb);
            int red = Color.red(argb);
            int green = Color.green(argb);
            int blue = Color.blue(argb);
            //修改三原色  新的三原色 = (((旧的三原色)/255.0-0.5)*C + 0.5)*255.0;
            red = (int) (((red / 255.0 - 0.5) * c + 0.5) * 255);
            green = (int) (((green / 255.0 - 0.5) * c + 0.5) * 255);
            blue = (int) (((blue / 255.0 - 0.5) * c + 0.5) * 255);
            //修正三原色的值
            if (red < 0) {
                red = 0;
            } else if (red > 255) {
                red = 255;
            }
            if (green < 0) {
                green = 0;
            } else if (green > 255) {
                green = 255;
            }
            if (blue < 0) {
                blue = 0;
            } else if (blue > 255) {
                blue = 255;
            }
            //合成三原色
            pixels[i] = Color.argb(alpha, red, green, blue);
        }


    }
}
