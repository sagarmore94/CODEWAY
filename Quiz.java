import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {
    private ArrayList<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private boolean answered;

    public Quiz(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timer = new Timer();
        this.answered = false;
    }

    public void start() {
        System.out.println("Welcome to the Quiz!");
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion currentQuestion = questions.get(i);
            presentQuestion(currentQuestion);
            waitForAnswer(currentQuestion);
            if (answered) {
                score++;
            }
            timer.cancel(); 
            timer.purge();
            answered = false;
        }
        displayResult();
    }

    private void presentQuestion(QuizQuestion question) {
        System.out.println("Question: " + question.getQuestion());
        ArrayList<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        startTimer();
    }

    private void waitForAnswer(QuizQuestion question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your answer (enter the option number): ");
        int chosenOption = scanner.nextInt();
        if (chosenOption == question.getCorrectAnswerIndex() + 1) {
            System.out.println("Correct!");
            answered = true;
        } else {
            System.out.println("Incorrect!");
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int timeLeft = 10;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    System.out.println("Time Left: " + timeLeft + " seconds");
                    timeLeft--;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 1000);
    }

    private void displayResult() {
        System.out.println("Quiz Finished!");
        System.out.println("Your Score: " + score + "/" + questions.size());
    }

    public static void main(String[] args) {
        
        ArrayList<QuizQuestion> questions = new ArrayList<>();
        ArrayList<String> options1 = new ArrayList<>();
        options1.add("Option A");
        options1.add("Option B");
        options1.add("Option C");
        options1.add("Option D");
        QuizQuestion question1 = new QuizQuestion("What is the capital of France?", options1, 1); // B is the correct answer

        ArrayList<String> options2 = new ArrayList<>();
        options2.add("Option A");
        options2.add("Option B");
        options2.add("Option C");
        options2.add("Option D");
        QuizQuestion question2 = new QuizQuestion("What is the largest planet in our solar system?", options2, 2); // C is the correct answer

        questions.add(question1);
        questions.add(question2);
        
        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
