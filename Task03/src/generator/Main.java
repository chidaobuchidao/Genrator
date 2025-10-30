package generator;

import generator.Student.Student;
import generator.Teacher.Teacher;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Teacher teacher = new Teacher();
    private static Student student = new Student();
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("========欢迎使用算式生成器========");
                System.out.println("请选择模式：学生模式(1)===老师模式(2)===自检测试(3)===退出(Q/q)");
                String input = scanner.next();

                // 检查是否退出
                if ("Q".equalsIgnoreCase(input)) {
                    System.out.println("程序已退出。");
                    break;
                }

                int mode = Integer.parseInt(input);
                switch (mode) {
                    case 1:
                        studentMode(scanner);
                        break;
                    case 2:
                        handleTeacherMode(scanner);
                        break;
                    case 3:
                        runSelfTest();
                        break;
                     default:
                        System.out.println("无效模式，请重新输入");
                }
            }
        }
    }

    // 学生模式入口
    public static void studentMode(Scanner scanner) {
        System.out.println("========学生端========");
        String[] generatedProblems = generateProblems(scanner);
        student.resetCurrentExam();

        for (int i = 0; i < generatedProblems.length; i++) {
            String[] parts = generatedProblems[i].split("=");
            if (parts.length < 2) {
                System.out.println("算式格式错误");
                continue;
            }
            System.out.print((i + 1) + ". " + parts[0].trim() + " = ");
            String input = scanner.next();
            student.doExercise(parts[0].trim(), parts[1].trim(), input);
        }

        student.showFinalScore();
    }

    // 教师模式的入口
    private static void handleTeacherMode(Scanner scanner) {
        while(true){
            System.out.println("========教师端========");
            System.out.println("请选择功能:批改学生题目(1)===查看得分记录(2)===手动出题(3)===退出(Q)");
            String input = scanner.next();

            // 检查是否退出
            if ("Q".equalsIgnoreCase(String.valueOf(input))) {
                break;
            }

            int teacherMode = Integer.parseInt(input);
            switch (teacherMode) {
                case 1:
                    // 批改学生题目
                    teacher.gradeStudentWork(scanner,student);
                    break;
                case 2:
                    // 查看得分记录
                    teacher.viewStudentScoreHistory(student);
                    System.out.println("查看得分记录功能尚未实现");
                    break;
                case 3:
                    // 手动出题
                    teacher.createCustomProblems(scanner);
                    break;
                default:
                    System.out.println("无效输入，请重新输入");
            }
        }
    }

    // 生成题目
    private static String[] generateProblems(Scanner scanner) {
        int count = getIntInput(scanner, "请输入要生成的算式数量：");
        if (count <= 0) {
            throw new IllegalArgumentException("题目数量必须大于0");
        }

        //获取老师手动出的题目
        List<String> teacherProblems = teacher.getTeacherProblems();
        String[] finalProblems = new String[count];
        int teacherProblemCount = Math.min(count, teacherProblems.size());

        // 优先使用教师题目
        for (int i = 0; i < teacherProblemCount; i++) {
            finalProblems[i] = teacherProblems.get(i);
        }

        // 如果教师题目不够，则生成随机题目
        if (teacherProblemCount < count){
            System.out.println("请输入算式范围(min-max)：");
            String range;
            int min, max;
            while (true) {
                range = scanner.next();
                String[] rangeArray = range.split("-");
                if (rangeArray.length < 2) {
                    System.out.println("范围格式错误，请输入 min-max 格式，例如：0-100");
                    continue;
                }
                try {
                    min = Integer.parseInt(rangeArray[0]);
                    max = Integer.parseInt(rangeArray[1]);
                    if (min > max) {
                        System.out.println("最小值不能大于最大值，请重新输入：");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("范围值必须是数字，请重新输入：");
                }
            }
            // 生成补充题目
            String[] generatedProblems = Genertion_Tool.generateMixedProblem(
                    min, max, count - teacherProblemCount);

            // 添加到最终题目数组中
            for (int i = 0; i < generatedProblems.length; i++) {
                finalProblems[teacherProblemCount + i] = generatedProblems[i];
            }
        }
        return finalProblems;
    }

    // 获取整数输入，防止非法输入导致崩溃
    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("输入无效，请输入一个整数。");
                scanner.nextLine(); // 清除无效输入
            }
        }
    }

    // 自检方法：测试是否能正确判断算式
    private static void runSelfTest() {
        System.out.println("========程序自测功能========");

        // 获取测试数据
        List<String> validExpressions = TestDataGenerator.getValidExpressions();
        List<String> invalidExpressions = TestDataGenerator.getInvalidExpressions();

        System.out.println("测试正确算式:");
        int validPassed = 0;
        for (String expression : validExpressions) {
            if (testExpression(expression, true)) {
                validPassed++;
            }
        }

        System.out.println("\n测试错误算式:");
        int invalidPassed = 0;
        for (String expression : invalidExpressions) {
            if (testExpression(expression, false)) {
                invalidPassed++;
            }
        }

        System.out.println("\n=======测试结果=======");
        System.out.println("正确算式测试: " + validPassed + "/" + validExpressions.size() + " 通过");
        System.out.println("错误算式测试: " + invalidPassed + "/" + invalidExpressions.size() + " 通过");
        System.out.println("总通过率: " + (validPassed + invalidPassed) + "/" +
                (validExpressions.size() + invalidExpressions.size()));
    }

    private static boolean testExpression(String expression, boolean shouldBeValid) {
        try {
            if (shouldBeValid) {
                // 对于应该有效的表达式，检查是否能通过验证
                Genertion_Tool.checkInvalidExpression(expression);
                System.out.println(expression + " - ✓ 正确识别为有效算式");
                return true;
            } else {
                // 对于应该无效的表达式，应该被捕获为非法
                Genertion_Tool.checkInvalidExpression(expression);
                System.out.println(expression + " - ✗ 错误：应为无效算式但被判定为有效");
                return false;
            }
        } catch (Exception e) {
            if (shouldBeValid) {
                System.out.println(expression + " - ✗ 错误：应为有效算式但被判定为无效 - " + e.getMessage());
                return false;
            } else {
                System.out.println(expression + " - ✓ 正确识别为无效算式");
                return true;
            }
        }
    }

}
