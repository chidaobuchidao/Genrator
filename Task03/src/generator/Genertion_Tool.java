package generator;

//这里是一个工具类，用于生成随机数，生成加减法算式，生成混合算式，生成不重复的算式，并输出
//包含测试模块

import java.util.Random;

public class  Genertion_Tool {
    // 编译一次的正则表达式常量
    private static final String DIGIT_REGEX = ".*\\d.*";

    //生成随机数
    public static int getRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("最小值不能大于最大值");
        }
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    //生成加法算式,生成加法
    public static String generateAddition(int min, int max) {
        int a = getRandomNumber(min, max);
        // 确保不会出现负数且不超过范围
        int upperBound = Math.min(max - a, max);
        int lowerBound = min;

        if (lowerBound > upperBound) {
            lowerBound = upperBound; // 处理极端情况
        }

        int b = getRandomNumber(lowerBound, upperBound);
        return a + " + " + b + " = " + (a + b);
    }

    //生成减法算式,生成减法
    public static String generateSubtraction(int min, int max) {
        int a = getRandomNumber(min, max);
        // 确保减数不大于被减数，防止结果为负数
        int b = getRandomNumber(min, a);
        return a + " - " + b + " = " + (a - b);
    }

    //生成混合算式,生成加减法混合
    public static String[] generateMixedProblem(int min, int max, int count) {
        String[] problems = new String[count];
        Random random = new Random(); // 提取到循环外部
        for (int i = 0; i < count; i++) {
            if (random.nextBoolean()) {
                problems[i] = generateAddition(min, max);
            } else {
                problems[i] = generateSubtraction(min, max);
            }
        }
        return problems;
    }


    //显示生成的算式，可以分每生成几个自定义换行
    public static void displayProblems(String[] problems, int lineCount) {
        for (int i = 0; i < problems.length; i++) {
            System.out.print(problems[i] + " ");
            if ((i + 1) % lineCount == 0) {
                System.out.println();
            }
        }
    }

    //测试模块
    /**
     * 反向思维检测非法算式
     * @param expression 待检验的算式字符串
     */
    public static void checkInvalidExpression(String expression) {
        if (!isValidExpression(expression)) {
            System.out.println(expression + "，非法算式，不满足'数字 +/- 数字 = 数字'格式");
        } else {
            // 进一步验证算式的具体数值和计算结果
            validateExpressionDetails(expression);
        }
    }

    /**
     * 判断算式是否合法
     * @param expression 待检验的算式字符串
     * @return true表示合法，false表示非法
     */
    public static boolean isValidExpression(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }

        // 检查是否包含必需的字符
        boolean hasOperator = expression.contains("+") || expression.contains("-");
        boolean hasEquals = expression.contains("=");
        boolean hasDigit = expression.matches(DIGIT_REGEX);

        // 必须同时包含运算符、等号和数字才可能是合法算式
        return hasOperator && hasEquals && hasDigit;
    }

    /**
     * 验证算式详细信息
     * @param expression 算式字符串
     */
    private static void validateExpressionDetails(String expression) {
        try {
            // 分割算式
            String[] parts = expression.split("=");
            if (parts.length != 2) {
                System.out.println(expression + "，非法算式，格式不正确");
                return;
            }

            String leftSide = parts[0].trim();
            String rightSide = parts[1].trim();

            // 检查左侧是否为"数字 +/- 数字"格式
            if (leftSide.contains("+")) {
                String[] operands = leftSide.split("\\+");
                if (operands.length == 2) {
                    validateAndCalculate(operands[0].trim(), operands[1].trim(), rightSide, "+", expression);
                } else {
                    System.out.println(expression + "，非法算式，加法格式不正确");
                }
            } else if (leftSide.contains("-")) {
                String[] operands = leftSide.split("-");
                if (operands.length == 2) {
                    validateAndCalculate(operands[0].trim(), operands[1].trim(), rightSide, "-", expression);
                } else {
                    System.out.println(expression + "，非法算式，减法格式不正确");
                }
            } else {
                System.out.println(expression + "，非法算式，缺少运算符");
            }
        } catch (Exception e) {
            System.out.println(expression + "，非法算式，解析异常");
        }
    }

    /**
     * 验证并计算算式结果
     */
    private static void validateAndCalculate(String op1, String op2, String result, String operator, String fullExpression) {
        try {
            if (!op1.matches("-?\\d+") || !op2.matches("-?\\d+") || !result.matches("-?\\d+")) {
                System.out.println(fullExpression + "，非法算式，包含非数字字符");
                return;
            }

            int num1 = Integer.parseInt(op1);
            int num2 = Integer.parseInt(op2);
            int expectedResult = Integer.parseInt(result);
            int calculatedResult;

            if ("+".equals(operator)) {
                calculatedResult = num1 + num2;
            } else {
                calculatedResult = num1 - num2;
            }

            if (calculatedResult == expectedResult) {
                System.out.println(fullExpression + "，合法算式，计算正确");
            } else {
                System.out.println(fullExpression + "，错误算式，计算结果不匹配");
            }
        } catch (NumberFormatException e) {
            System.out.println(fullExpression + "，非法算式，数字格式错误");
        }
    }


}