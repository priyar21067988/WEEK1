import java.util.Scanner;

/**
 * MovieReviewWordLengthProfiler
 * ------------------------------
 * Splits a movie review into words and classifies each one as
 * Short (1-4 letters), Medium (5-8 letters), or Long (9+ letters),
 * then reports the count for each category.
 *
 * Concepts covered: String splitting, loops, conditional logic,
 * counting/categorization, checked exceptions.
 */
public class MovieReviewWordLengthProfiler {

    // Custom CHECKED exception - an empty review has no words to
    // classify, so callers are required to handle this case.
    static class EmptyReviewException extends Exception {
        public EmptyReviewException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the movie review: ");
        String review = scanner.nextLine();

        try {
            classifyWordLengths(review);
        } catch (EmptyReviewException exception) {
            System.out.println("Cannot classify: " + exception.getMessage());
        }

        scanner.close();
    }

    // =========================================================
    // Splits the review into words, classifies each by length, and
    // prints the final Short/Medium/Long counts. Declares a CHECKED
    // exception for a blank or whitespace-only review.
    // =========================================================
    static void classifyWordLengths(String review) throws EmptyReviewException {
        if (review.trim().isEmpty()) {
            throw new EmptyReviewException("review cannot be blank.");
        }

        String[] words = review.trim().split("\\s+");

        int shortWordCount = 0;
        int mediumWordCount = 0;
        int longWordCount = 0;

        for (String word : words) {
            String category = categorizeWordLength(word);
            switch (category) {
                case "Short":
                    shortWordCount++;
                    break;
                case "Medium":
                    mediumWordCount++;
                    break;
                case "Long":
                    longWordCount++;
                    break;
            }
        }

        printWordLengthReport(shortWordCount, mediumWordCount, longWordCount);
    }

    // Single-purpose classification method
