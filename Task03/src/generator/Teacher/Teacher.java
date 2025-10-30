package generator.Teacher;

import generator.Student.Student;
import generator.Student.StudentAnswerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//教师端
//批改学生做答过的算式
//老师可以自己出题
//可以查看学生的成绩历史
public class Teacher {
    //老师出的题目
    private List<String> teacherProblems = new ArrayList<>();
    
    //保存待批改的学生题目
    private List<StudentAnswerRecord> studentProblems = new ArrayList<>();

    /**
     * 查看学生成绩历史记录
     * @param student 学生对象
     */
    public void viewStudentScoreHistory(Student student) {
        List<Integer> history = student.getScoreHistory();
        if (history.isEmpty()) {
            System.out.println("该学生暂无成绩记录");
        } else {
            System.out.println("学生成绩历史记录：");
            for (int i = 0; i < history.size(); i++) {
                System.out.println("第" + (i + 1) + "次测试得分: " + history.get(i));
            }
        }
    }


    /**
     * 手动出题功能 - 出一道存一道，按Q退出
     * @return 生成的题目数组
     */
    public String[] createCustomProblems(Scanner scanner) {
        List<String> problemsList = new ArrayList<>();
        System.out.println("请逐题输入题目 (格式: 数字 +/- 数字 = 答案)，输入 'Q' 退出：");

        // 清除缓冲区中的换行符
        scanner.nextLine();

        int problemNumber = 1;
        while (true) {
            System.out.print("请输入第" + problemNumber + "道题目:");
            String input = scanner.nextLine().trim();

            // 检查是否退出
            if ("Q".equalsIgnoreCase(input)) {
                break;
            }

            // 验证输入不为空
            if (input.isEmpty()) {
                System.out.println("题目不能为空，请重新输入。");
                continue;
            }

            // 使用工具类验证题目
            if (validateTeacherProblem(input)) {
                problemsList.add(input);
                problemNumber++;
                System.out.println("题目已保存！");
            }
        }

        // 保存到教师题目记录中
        teacherProblems.addAll(problemsList);

        // 转换为数组并返回
        return problemsList.toArray(new String[0]);
    }

    /**
     * 验证教师输入的题目
     * @param problem 题目字符串
     * @return 验证是否通过
     */
    private boolean validateTeacherProblem(String problem) {
        try {
            // 使用 Genertion_Tool 中的验证方法检查表达式
            generator.Genertion_Tool.checkInvalidExpression(problem);
            return true;
        } catch (Exception e) {
            System.out.println("题目验证失败: " + e.getMessage());
            return false;
        }
    }


    /**
     * 获取教师自定义的题目
     * @return 教师题目列表的副本
     */
    public List<String> getTeacherProblems() {
        return new ArrayList<>(teacherProblems);
    }


    /**
     * 批改学生答卷 - 按y或n判断
     * @param scanner 输入扫描器
     */
    public void gradeStudentWork(Scanner scanner, Student student) {
        List<StudentAnswerRecord> studentProblems = student.getAnswerRecords();
        if (studentProblems.isEmpty()) {
            System.out.println("暂无待批改的答题记录");
            return;
        }

        int correctCount = 0;
        System.out.println("开始批改学生答卷，请确认答案是否正确(y/n)：");

        for (int i = 0; i < studentProblems.size(); i++) {
            StudentAnswerRecord record = studentProblems.get(i);
            System.out.println("第" + (i + 1) + "题: " + record.getProblem() +
                    " 学生答案: " + record.getStudentAnswer() +
                    " 正确答案: " + record.getCorrectAnswer());

            String input;
            do {
                System.out.print("答案是否正确？(y/n): ");
                input = scanner.next().toLowerCase();
                if ("y".equals(input)) {
                    correctCount++;
                    System.out.println("✓ 标记为正确");
                } else if ("n".equals(input)) {
                    System.out.println("✗ 标记为错误");
                } else {
                    System.out.println("请输入 y 或 n");
                }
            } while (!("y".equals(input) || "n".equals(input)));
        }

        // 计算并显示成绩
        int total = studentProblems.size();
        double percentage = (double) correctCount / total * 100;
        System.out.println("\n批改完成！");
        System.out.println("得分: " + correctCount + "/" + total);
        System.out.println("正确率: " + String.format("%.2f", percentage) + "%");
    }


    /**
     * 查看教师自己的出题记录
     */
    public void viewOwnProblems() {
        if (teacherProblems.isEmpty()) {
            System.out.println("暂无自定义题目记录");
        } else {
            System.out.println("教师自定义题目记录：");
            for (int i = 0; i < teacherProblems.size(); i++) {
                System.out.println((i + 1) + ". " + teacherProblems.get(i));
            }
        }
    }
}
