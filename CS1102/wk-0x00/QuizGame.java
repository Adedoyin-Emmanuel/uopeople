import java.util.Scanner;

public class QuizGame {
    public static void main(String[] args) {
        // We init Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        char[] correctAnswers = {'B', 'C', 'A', 'D', 'B'};
        char[] userAnswers = new char[5];
        
        // We declare an array of questions 
        String[] questions = {
            "1. What is the capital of France?\nA. London\nB. Paris\nC. Rome\nD. Berlin",
            "2. Which planet is known as the Red Planet?\nA. Earth\nB. Venus\nC. Mars\nD. Jupiter",
            "3. Who wrote 'To Kill a Mockingbird'?\nA. Harper Lee\nB. J.K. Rowling\nC. Mark Twain\nD. Ernest Hemingway",
            "4. What is the chemical symbol for Gold?\nA. Ag\nB. Fe\nC. Au\nD. Pb",
            "5. Which element has the atomic number 1?\nA. Helium\nB. Hydrogen\nC. Lithium\nD. Beryllium"
        };
        
        // Loop through each question
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("Enter your answer (A, B, C, D): ");
            char answer = scanner.next().toUpperCase().charAt(0);
            
            // Validate input
            if (answer == 'A' || answer == 'B' || answer == 'C' || answer == 'D') {
                userAnswers[i] = answer;
            } else {
                System.out.println("Invalid input. Please enter A, B, C, or D.");
                i--; // Retry the same question
            }
        }
        
        scanner.close();
        
        int correctCount = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                correctCount++;
            }
        }
        
        double scorePercentage = (correctCount / (double) correctAnswers.length) * 100;
        System.out.println("\nQuiz Finished!");
        System.out.println("Your score: " + correctCount + "/" + correctAnswers.length);
        System.out.printf("Your score percentage: %.2f%%\n", scorePercentage);
    }
}
