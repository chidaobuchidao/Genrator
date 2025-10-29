import java.util.*;

public class task2 {
    // 生成加法算式
    public static String generateAddition() {
        int a = new Random().nextInt(99) + 1;
        int b = new Random().nextInt(100 - a) + 1;
        return a + " + " + b + " = " + (a + b);
    }

    // 生成减法算式
    public static String generateSubtraction() {
        int a = new Random().nextInt(99) + 1;
        int b = new Random().nextInt(a - 1) + 1;
        return a + " - " + b + " = " + (a - b);
    }

    // 生成混合算式
    public static String generateMixedProblem() {
        if (new Random().nextBoolean()) {
            return generateAddition();
        } else {
            return generateSubtraction();
        }
    }

    // 生成50道不重复的算式
    public static List<String> generateProblems(int count) {
        Set<String> problems = new HashSet<>();
        while (problems.size() < count) {
            problems.add(generateMixedProblem());
        }
        return new ArrayList<>(problems);
    }

    // 输出生成的算式
    public static void displayProblems(List<String> problems) {
        for (int i = 0; i < problems.size(); i++) {
            System.out.printf("%-15s",problems.get(i));
            // 每五个算式后换行
            if ((i + 1) % 5 == 0) {
                System.out.println();
            } else {
                System.out.print(" ");
            }
        }
        if (problems.size() % 5 != 0) {
            System.out.println();
        }
    }

    public static void testCases() {
        Random random = new Random();
        int totalTests = random.nextInt(20) + 1; // 总测试次数在1~20之间

        System.out.println("正在随机测试 " + totalTests + " 组数据...");

        // 正确数据测试
        assert generateAddition() != null;
        assert generateSubtraction() != null;

        // 非法数据测试：检查数值是否在0~100范围内
        int numericTests = random.nextInt(totalTests) + 1; // 数值测试次数在1~totalTests之间
        for (int i = 0; i < numericTests; i++) {
            try {
                int a = random.nextInt(200) - 50; // 可能为负数或大于100
                int b = random.nextInt(200) - 50; // 可能为负数或大于100
                int result = a + b;

                // 判断结果是否在0~100之间
                if (result >= 0 && result <= 100) {
                    System.out.println(a + " + " + b + " = " + result + "，合法数据，结果在0~100范围内");
                } else {
                    System.out.println(a + " + " + b + " = " + result + "，错误数据，结果超出0~100范围");
                }
            } catch (Exception e) {
                System.out.println("数值测试异常: " + e.getMessage());
            }
        }

        // 测试null和字母相加的情况
        int stringTests = totalTests - numericTests; // 字符串测试次数 = 总次数 - 数值测试次数
        String[] invalidData = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"," "
        };
        for (int i = 0; i < stringTests; i++) {
            try {
                String operand1 = invalidData[random.nextInt(invalidData.length)];
                String operand2 = invalidData[random.nextInt(invalidData.length)];

                // 检查是否存在null或字母
                if (operand1.equals(" ") || operand2.equals(" ")) {
                    System.out.println(operand1 + " + " + operand2 + " = " + (operand1 + operand2) + "，非法数据，存在空字符相加");
                } else if (operand1.matches("[a-zA-Z]+") || operand2.matches("[a-zA-Z]+")) {
                    System.out.println(operand1 + " + " + operand2 + " = " + (operand1 + operand2) + "，非法数据，存在字母相加");
                } else {
                    // 如果都是数字，检查结果是否在0~100范围内
                    try {
                        int num1 = Integer.parseInt(operand1);
                        int num2 = Integer.parseInt(operand2);
                        int result = num1 + num2;

                        if (result >= 0 && result <= 100) {
                            System.out.println(operand1 + " + " + operand2 + " = " + result + "，合法数据，结果在0~100范围内");
                        } else {
                            System.out.println(operand1 + " + " + operand2 + " = " + result + "，错误数据，结果超出0~100范围");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(operand1 + " + " + operand2 + " = " + (operand1 + operand2) + "，非法数据，无法转换为数字进行计算");
                    }
                }
            } catch (Exception e) {
                System.out.println("字符串测试异常: " + e.getMessage());
            }
        }

        System.out.println("所有测试完成！");
    }


    // 主函数
    // 分模式进行运行，正常使用输入Y进行算式输出，输入T进行测试
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("请输入模式 Y(生成)/T(测试)/Q(退出)：");
            String mode = scanner.next();
            if (mode.equalsIgnoreCase("Y")) {
                System.out.println("请输入要生成的算式数量：");
                int count = scanner.nextInt();
                List<String> problems = generateProblems(count);
                displayProblems(problems);
                System.out.println("算式已生成！");
            }
            else if (mode.equalsIgnoreCase("T")) {
                testCases();
            }
            else if (mode.equalsIgnoreCase("Q")) {
                System.out.println("已退出程序！");
                break;
            }
            else {
                System.out.println("输入错误，请重新输入！");
            }
        }
    }
}
