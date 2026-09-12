
import java.util.Scanner;
 
/**
 * TrafficSignalStreakAnalyzer
 * ----------------------------
 * Scans a day's signal log ('R', 'Y', 'G' readings, one per minute)
 * and finds the longest streak of consecutive identical readings,
 * so engineers know which signal might be stuck.
 *
 * Concepts covered: String traversal, charAt(), loops, tracking a
 * running maximum, checked exceptions, unchecked exceptions.
 */
public class TrafficSignalStreakAnalyzer {
 
    // Custom CHECKED exception - an empty log breaks the task's
    // assumptions, so callers are required to handle it.
    static class EmptySignalLogException extends Exception {
        public EmptySignalLogException(String message) {
            super(message);
        }
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        System.out.print("Enter the signal log (letters R, Y, G only): ");
        String signalLog = scanner.nextLine();
 
        try {
            findLongestStreak(signalLog);
        } catch (EmptySignalLogException exception) {
            System.out.println("Cannot analyze: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid signal log: " + exception.getMessage());
        }
 
        scanner.close();
    }
 
    // =========================================================
    // Finds and prints the longest streak of consecutive identical
    // characters. Declares a CHECKED exception for an empty log, and
    // relies on validateSignalCharacter() to raise an UNCHECKED
    // IllegalArgumentException for any character other than R/Y/G.
    // =========================================================
    static void findLongestStreak(String signalLog) throws EmptySignalLogException {
        if (signalLog.isEmpty()) {
            throw new EmptySignalLogException("signal log must contain at least one reading.");
        }
 
        validateSignalCharacter(signalLog.charAt(0)); // throws unchecked IllegalArgumentException if invalid
 
        char longestStreakColor = signalLog.charAt(0);
        int longestStreakLength = 1;
 
        char currentStreakColor = signalLog.charAt(0);
        int currentStreakLength = 1;
 
        for (int index = 1; index < signalLog.length(); index++) {
            char currentReading = signalLog.charAt(index);
            validateSignalCharacter(currentReading); // throws unchecked IllegalArgumentException if invalid
 
            if (currentReading == currentStreakColor) {
                currentStreakLength++;
            } else {
                currentStreakColor = currentReading;
                currentStreakLength = 1;
            }
 
            if (currentStreakLength > longestStreakLength) {
                longestStreakLength = currentStreakLength;
                longestStreakColor = currentStreakColor;
            }
        }
 
        printLongestStreakReport(longestStreakColor, longestStreakLength);
    }
 
    // Ensures every character in the log is one of the three valid
    // signal colors. Throws an UNCHECKED exception (no "throws"
    // declaration needed) since invalid data indicates a caller bug.
    static void validateSignalCharacter(char signalCharacter) {
        if (signalCharacter != 'R' && signalCharacter != 'Y' && signalCharacter != 'G') {
            throw new IllegalArgumentException("'" + signalCharacter + "' is not one of R, Y, G.");
        }
    }
 
    // Single-purpose output method, kept separate from the scanning logic.
    static void printLongestStreakReport(char streakColor, int streakLength) {
        System.out.println("Longest Streak: '" + streakColor + "' repeated " + streakLength + " times");
    }
}
 
