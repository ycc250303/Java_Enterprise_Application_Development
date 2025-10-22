package com.garfieldrestaurant.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 命令行交互工具类，用于处理输入输出。
 */
public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleUtils() {
        // 私有构造函数防止实例化
    }

    /**
     * 打印一个分隔线。
     */
    public static void printSeparator() {
        System.out.println("\n--------------------------------------------------");
    }

    /**
     * 读取用户输入的整数。
     * 
     * @param prompt 提示信息。
     * @return 用户输入的整数。
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // 消耗换行符
                return input;
            } catch (InputMismatchException e) {
                System.out.println("输入无效，请输入一个整数。");
                scanner.nextLine(); // 消耗无效输入
            }
        }
    }

    /**
     * 读取用户输入的字符串。
     * 
     * @param prompt 提示信息。
     * @return 用户输入的字符串。
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}