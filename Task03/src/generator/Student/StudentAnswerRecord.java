package generator.Student;
//学生答题记录
public class StudentAnswerRecord {
    private String problem;        // 题目
    private String correctAnswer;  // 正确答案
    private String studentAnswer;  // 学生答案

    public StudentAnswerRecord(String problem, String correctAnswer, String studentAnswer){
        this.problem = problem;
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }

    public String getProblem(){
        return problem;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public String getStudentAnswer(){
        return studentAnswer;
    }
}
