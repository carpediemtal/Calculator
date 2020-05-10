package eternal.fire;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Parse parse = new Parse();
        if (args.length != 0) {
            //从命令行读取文件的信息
            for (String s : args) {
                System.out.println(s);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(s)))) {
                    while (true) {
                        String str = reader.readLine();
                        if (str == null) {
                            break;
                        }
                        double ans = parse.getAns(str);
                        System.out.println(ans);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String str = scanner.next();
                if (str.equals("exit")) {
                    break;
                }
                double ans = parse.getAns(str);
                System.out.println(ans);
            }
        }
    }
}
