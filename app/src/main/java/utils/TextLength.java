package utils;

/**
 * Created by Administrator on 2016/7/27.
 */

//描述字符串长度的类
public class TextLength {

    //判断是否是一个汉字
    public static boolean isChineseChar(char c) throws Exception {
        //汉字的字节数大于1
        return String.valueOf(c).getBytes("GBK").length > 1;
    }

    //获得汉字的长度
    public static int getChineseCount(String s) throws Exception {
        char c;
        int chineseCount = 0;
        //判断是否为空
        if (!"".equals("")) {
            //进行统一编码
            s = new String(s.getBytes(), "GBK");
        }
        //for循环
        for (int i = 0; i < s.length(); i++) {
            //获得字符串中的每个字符
            c = s.charAt(i);
            //调用方法进行判断是否是汉字
            if (isChineseChar(c)) {
                //等同于chineseCount=chineseCount+1
                chineseCount++;
            }
        }
        //返回汉字个数
        return chineseCount;
    }

    //获得字母、数字、空格的个数的总和
    public static int getStringInfo(String s) {
        char ch;
        int character = 0, blank = 0, number = 0;
        //for循环
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            //统计字母
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                //等同于character=character+1
                character++;
                //统计空格
            else if (ch == ' ')
                blank++; //等同于blank=blank+1
                //统计数字
            else if (ch >= '0' && ch <= '9')
                //等同于number=number+1;
                number++;
        }
        //返回数字个数
        return character + blank + number;
    }

    //获得数字个数
    public static int getNumberCount(String s) {
        char ch;
        int character = 0, blank = 0, number = 0;
        //for循环
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            //统计字母
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                //等同于character=character+1
                character++;
                //统计空格
            else if (ch == ' ')
                blank++; //等同于blank=blank+1
                //统计数字
            else if (ch >= '0' && ch <= '9')
                //等同于number=number+1;
                number++;
        }
        //返回数字个数
        return number;
    }
}
