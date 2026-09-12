ychecker · JAVA
import java.util.Scanner;
 
/**
 * TypingSpeedAccuracyChecker
 * ---------------------------
 * Compares a user's typed text against the original passage,
 * character by character, and reports:
 *   - how many characters matched
 *   - the accuracy percentage
 *   - the position (1-based) and characters of the first mismatch
 *
 * Concepts covered: String traversal, charAt(), loops, conditional
 * logic, percentage calculation, checked exceptions.
 */
public class TypingSpeedAccuracyChecker {
 
    // Custom CHECKED exception - the task requires equal-length strings,
    // so a length mismatch is a business-rule violation, not a bug.
    static class UnequalLengthException extends Exception {
        public UnequalLengthException(String message) {
            super(message);
        }
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        System.out.print("Enter the original passage: ");
        String originalPassage = scanner.nextLine();
 
        System.out.print("Enter the typed text: ");
        String typedText = scanner.nextLine();
 
        try {
            checkTypingAccuracy(originalPassage, typedText);
        } catch (UnequalLengthException exception) {
            System.out.println("Cannot compare: " + exception.getMessage());
        }
 
        scanner.close();
    }
 
    // =========================================================
    // Compares original vs typed text position by position and
    // prints matched count, accuracy percentage, and the first
    // mismatch location. Declares a CHECKED exception so callers
    // must handle unequal-length input.
    // =========================================================
    static void checkTypingAccuracy(String original, String typed) throws UnequalLengthException {
        if (original.length() != typed.length()) {
            throw new UnequalLengthException("original and typed text must be the same length ("
                    + original.length() + " vs " + typed.length() + ").");
        }
 
        final int totalCharacters = original.length();
        int matchedCharacters = 0;
        int firstMismatchPosition = -1; // -1 means "no mismatch found yet"
 
        for (int index = 0; index < totalCharacters; index++) {
            char originalChar = original.charAt(index);
            char typedChar = typed.charAt(index);
 
            if (originalChar == typedChar) {
                matchedCharacters++;
            } else if (firstMismatchPosition == -1) {
                firstMismatchPosition = index + 1; // convert to 1-based position
            }
        }
 
        double accuracyPercentage = calculateAccuracyPercentage(matchedCharacters, totalCharacters);
        printAccuracyReport(matchedCharacters, totalCharacters, accuracyPercentage,
                firstMismatchPosition, original, typed);
    }
 
    // Small, single-purpose method - keeps the percentage math out of
    // the main comparison logic and out of main().
    static double calculateAccuracyPercentage(int matchedCharacters, int totalCharacters) {
        return ((double) matchedCharacters / totalCharacters) * 100;
    }
 
    // Formats and prints the final report line.
    static void printAccuracyReport(int matchedCharacters, int totalCharacters, double accuracyPercentage,
                                     int firstMismatchPosition, String original, String typed) {
        String matchSummary = "Matched: " + matchedCharacters + "/" + totalCharacters;
        String accuracySummary = String.format("Accuracy: %.2f%%", accuracyPercentage);
 
        String mismatchSummary;
        if (firstMismatchPosition == -1) {
            mismatchSummary = "No Mismatches";
        } else {
            char originalCharAtMismatch = original.charAt(firstMismatchPosition - 1);
            char typedCharAtMismatch = typed.charAt(firstMismatchPosition - 1);
            mismatchSummary = "First Mismatch at position " + firstMismatchPosition
                    + " ('" + originalCharAtMismatch + "' vs '" + typedCharAtMismatch + "')";
        }
 
        System.out.println(matchSummary + " | " + accuracySummary + " | " + mismatchSummary);
    }
}
 
