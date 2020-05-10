package eternal.fire;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    //二元运算
    private final Pattern pattern = Pattern.compile("(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-*/])(\\d+|\\d+\\.\\d+|[A-Za-z]+)");
    //三元加减运算
    private final Pattern pattern1 = Pattern.compile("(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-])(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-])(\\d+|\\d+\\.\\d+|[A-Za-z]+)");
    //三元纯数字混合运算（乘除在前）
    private final Pattern pattern2 = Pattern.compile("(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-*/])(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-])(\\d+|\\d+\\.\\d+|[A-Za-z]+)");
    //三元纯数字混合运算（乘除在后）
    private final Pattern pattern3 = Pattern.compile("(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-])(\\d+|\\d+\\.\\d+|[A-Za-z]+)([+\\-*/])(\\d+|\\d+\\.\\d+|[A-Za-z]+)");
    //三元纯数字乘除运算
    private final Pattern pattern4 = Pattern.compile("(\\d+|\\d+\\.\\d+|[A-Za-z]+)([*/])(\\d+|\\d+\\.\\d+|[A-Za-z]+)([*/])(\\d+|\\d+\\.\\d+|[A-Za-z]+)");
    //给某个字母赋值，比如 A=3*4+2
    private final Pattern pattern5 = Pattern.compile("([A-Za-z]+)=(.+)");
    //只有一个数字或者只有一个字母
    private final Pattern pattern6 = Pattern.compile("\\d+|\\d+\\.\\d+|[A-Za-z]+");

    Map<String, Double> map = new HashMap<>();

    public double getAns(String str) {
        if (str.endsWith(";")) {
            str = str.substring(0, str.length() - 1);
        }

        map.put("pi", 3.1415926);
        Matcher matcher = pattern.matcher(str);
        Matcher matcher1 = pattern1.matcher(str);
        Matcher matcher2 = pattern2.matcher(str);
        Matcher matcher3 = pattern3.matcher(str);
        Matcher matcher4 = pattern4.matcher(str);
        Matcher matcher5 = pattern5.matcher(str);
        Matcher matcher6 = pattern6.matcher(str);

        //如果是二元纯数字运算
        if (matcher.matches()) {
            double num1 = map.containsKey(matcher.group(1)) ? map.get(matcher.group(1)) : Double.parseDouble(matcher.group(1));
            double num2 = map.containsKey(matcher.group(3)) ? map.get(matcher.group(3)) : Double.parseDouble(matcher.group(3));
            String operator = matcher.group(2);
            switch (operator) {
                case "+":
                    return num1 + num2;
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    return num1 / num2;
            }
        }

        //如果是三元纯数字加减运算
        if (matcher1.matches()) {
            double num1 = map.containsKey(matcher1.group(1)) ? map.get(matcher1.group(1)) : Double.parseDouble(matcher1.group(1));
            double num2 = map.containsKey(matcher1.group(3)) ? map.get(matcher1.group(3)) : Double.parseDouble(matcher1.group(3));
            double num3 = map.containsKey(matcher1.group(5)) ? map.get(matcher1.group(5)) : Double.parseDouble(matcher1.group(5));
            double ans;
            if (matcher1.group(2).equals("+")) {
                ans = num1 + num2;
            } else {
                ans = num1 - num2;
            }
            if (matcher1.group(4).equals("+")) {
                ans += num3;
            } else {
                ans -= num3;
            }
            return ans;
        }


        //如果是三元纯数字混合运算（乘除在前）
        if (matcher2.matches()) {
            double num1 = map.containsKey(matcher2.group(1)) ? map.get(matcher2.group(1)) : Double.parseDouble(matcher2.group(1));
            double num2 = map.containsKey(matcher2.group(3)) ? map.get(matcher2.group(3)) : Double.parseDouble(matcher2.group(3));
            double num3 = map.containsKey(matcher2.group(5)) ? map.get(matcher2.group(5)) : Double.parseDouble(matcher2.group(5));
            double ans;
            if (matcher2.group(2).equals("*")) {
                ans = num1 * num2;
            } else {
                ans = num1 / num2;
            }
            if (matcher2.group(4).equals("+")) {
                ans += num3;
            } else {
                ans -= num3;
            }
            return ans;
        }
        //三元混合（乘除在后）
        if (matcher3.matches()) {
            double num1 = map.containsKey(matcher3.group(1)) ? map.get(matcher3.group(1)) : Double.parseDouble(matcher3.group(1));
            double num2 = map.containsKey(matcher3.group(3)) ? map.get(matcher3.group(3)) : Double.parseDouble(matcher3.group(3));
            double num3 = map.containsKey(matcher3.group(5)) ? map.get(matcher3.group(5)) : Double.parseDouble(matcher3.group(5));
            double ans;
            if (matcher3.group(4).equals("*")) {
                ans = num2 * num3;
            } else {
                ans = num2 / num3;
            }
            if (matcher3.group(2).equals("+")) {
                ans += num1;
            } else {
                ans -= num1;
            }
            return ans;
        }
        //三元乘除
        if (matcher4.matches()) {
            double num1 = map.containsKey(matcher4.group(1)) ? map.get(matcher4.group(1)) : Double.parseDouble(matcher4.group(1));
            double num2 = map.containsKey(matcher4.group(3)) ? map.get(matcher4.group(3)) : Double.parseDouble(matcher4.group(3));
            double num3 = map.containsKey(matcher4.group(5)) ? map.get(matcher4.group(5)) : Double.parseDouble(matcher4.group(5));
            double ans;
            if (matcher4.group(2).equals("*")) {
                ans = num1 * num2;
            } else {
                ans = num1 / num2;
            }
            if (matcher4.group(4).equals("*")) {
                ans *= num3;
            } else {
                ans /= num3;
            }
            return ans;
        }


        //给某个字母赋值
        if (matcher5.matches()) {
            double ans = getAns(matcher5.group(2));
            map.put(matcher5.group(1), ans);
            return ans;
        }

        if (matcher6.matches()) {
            return map.containsKey(matcher6.group(0)) ? map.get(matcher6.group(0)) : Double.parseDouble(matcher6.group(0));
        }

        throw new UnsupportedOperationException("unknown sentence");
    }
}
