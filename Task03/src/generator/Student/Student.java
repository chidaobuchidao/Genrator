package generator.Student;

import java.util.ArrayList;
import java.util.List;

//学生端
public class Student {
    //学生分数历史记录(教师端可以访问)
    private List<Integer> scoreHistory;
    //学生答题记录
    private List<StudentAnswerRecord> answerRecords;

    //当前得分
    private int currentScore;

    //总题数
    private int totalQuestions;

    public Student() {
        this.scoreHistory = new ArrayList<>();
        this.answerRecords = new ArrayList<>();
        this.currentScore = 0;
        this.totalQuestions = 0;
    }

    //学生做题模块，一次显示一个算式，用户输入结果，判断是否正确，然后进行计分，并保存
    public void doExercise(String problem, String correctAnswer, String studentAnswer) {
        totalQuestions++;
        //保存答题记录
        answerRecords.add(new StudentAnswerRecord(problem, correctAnswer, studentAnswer));
        if (correctAnswer.trim().equals(studentAnswer.trim())) {
            currentScore++;
            System.out.println(problem + " = " + studentAnswer + " ✓ 正确");
        } else {
            System.out.println(problem + " = " + studentAnswer + " ✗ 错误 (正确答案: " + correctAnswer + ")");
        }
    }

    //计算并显示最终成绩
    public void showFinalScore() {
        if (totalQuestions > 0) {
            double percentage = (double) currentScore / totalQuestions * 100;
            System.out.println("答题完成！");
            System.out.println("得分: " + currentScore + "/" + totalQuestions);
            System.out.println("正确率: " + String.format("%.2f", percentage) + "%");
            System.out.println();
            saveScore(currentScore);
        }
    }

    //保存分数到历史记录
    private void saveScore(int score) {
        scoreHistory.add(score);
    }

    //获取历史成绩，方便老师访问
    public List<Integer> getScoreHistory() {
        return new ArrayList<>(scoreHistory);
    }

    public List<StudentAnswerRecord> getAnswerRecords() {
        return new ArrayList<>(answerRecords);
    }

    //获取当前得分
    public int getCurrentScore() {
        return currentScore;
    }

    //重置当前考试状态
    public void resetCurrentExam() {
        this.currentScore = 0;
        this.totalQuestions = 0;
    }
}
