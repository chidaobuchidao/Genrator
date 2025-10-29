package generator;

import java.util.Arrays;
import java.util.List;

// 这里是一个测试集函数，包含测试数据
// 测试数据为特定的数据，其中包含正常的算式和错误算式
public class TestDataGenerator {

    /**
     * 预定义的正常算式测试数据
     */
    private static final String[] VALID_EXPRESSIONS = {
        "1 + 2 = 3",
        "10 + 20 = 30",
        "50 + 50 = 100",
        "0 + 0 = 0",
        "99 + 1 = 100",
        "50 - 20 = 30",
        "100 - 50 = 50",
        "10 - 5 = 5",
        "0 - 0 = 0",
        "75 - 25 = 50"
    };

    /**
     * 预定义的错误算式测试数据
     */
    private static final String[] INVALID_EXPRESSIONS = {
        "a + b = c",           // 包含字母
        "1 + 2",               // 缺少等号
        "1  2 = 3",            // 缺少运算符
        "* 2 = 3",             // 非法运算符
        "/ 2 = 3",             // 非法运算符
        "1.5 + 2.5 = 4.0",     // 包含小数
        "1 + = 3",             // 缺少操作数
        "+ 2 = 3",             // 缺少左操作数
        "1 + 2 = ",            // 缺少右操作数
        "1 * 2 = 2",           // 错误的运算符
        "1 % 2 = 1",           // 非法运算符
        "1 x 2 = 2",           // 非法运算符
        "",                    // 空字符串
        "abc",                 // 完全无效
        "1++2=3",              // 双运算符
        "1 + 2 = 3  ",         // 多余空格
        "  1 + 2 = 3",         // 前导空格
        "1 + 2 = = 3",          // 双等号
        "你 + 2 = 你2"           // 中文
    };

    /**
     * 获取预定义的正常算式测试数据
     * @return 正常算式列表
     */
    public static List<String> getValidExpressions() {
        return Arrays.asList(VALID_EXPRESSIONS);
    }

    /**
     * 获取预定义的错误算式测试数据
     * @return 错误算式列表
     */
    public static List<String> getInvalidExpressions() {
        return Arrays.asList(INVALID_EXPRESSIONS);
    }

    /**
     * 获取所有测试数据（正常和错误混合）
     * @return 混合算式列表
     */
    public static List<String> getAllTestExpressions() {
        List<String> allExpressions = Arrays.asList(VALID_EXPRESSIONS);
        allExpressions.addAll(Arrays.asList(INVALID_EXPRESSIONS));
        return allExpressions;
    }
}

