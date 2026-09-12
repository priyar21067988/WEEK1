import java.util.Scanner;
 
/**
 * ExamHallSeatDuplicationChecker
 * -------------------------------
 * The Examination Cell needs to verify that no seat number has been
 * assigned to two different students before an exam begins.
 *
 * This program:
 *   - Reads seat numbers into an int array (arrays + loops only, no Collections)
 *   - Compares every seat number against every other one (nested loops)
 *   - Prints each duplicated seat number, or a confirmation if none exist
 *
 * Concepts covered: arrays, nested loops, conditional logic, output
 * formatting, checked exceptions, unchecked exceptions.
 */
public class ExamHallSeatDuplicationChecker {
 
    // Custom CHECKED exception - the compiler forces callers to handle
    // this, used when the requested seat count breaks a business rule.
    static class InvalidSeatCountException extends Exception {
        public InvalidSeatCountException(String message) {
            super(message);
        }
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        int totalSeats = readValidSeatCount(scanner);
        int[] seatNumbers = readSeatNumbers(scanner, totalSeats);
 
        checkDuplicateSeats(seatNumbers);
 
        scanner.close();
    }
 
    // =========================================================
    // Reads how many seat numbers will be entered.
    // Demonstrates a CHECKED exception: InvalidSeatCountException
    // is declared with "throws" and must be caught by the caller.
    // =========================================================
    static int readValidSeatCount(Scanner scanner) {
        int seatCount = -1;
        boolean isValidCount = false;
 
        while (!isValidCount) {
            System.out.print("Enter number of seats to check: ");
            String rawCountInput = scanner.nextLine();
            try {
                int candidateCount = Integer.parseInt(rawCountInput); // may throw unchecked NumberFormatException
                seatCount = validateSeatCount(candidateCount);        // may throw checked InvalidSeatCountException
                isValidCount = true;
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            } catch (InvalidSeatCountException exception) {
                System.out.println("Invalid seat count: " + exception.getMessage());
            }
        }
        return seatCount;
    }
 
    // Declares a CHECKED exception so the caller is required to handle it.
    static int validateSeatCount(int candidateCount) throws InvalidSeatCountException {
        final int MINIMUM_SEAT_COUNT = 2;
        if (candidateCount < MINIMUM_SEAT_COUNT) {
            throw new InvalidSeatCountException("At least " + MINIMUM_SEAT_COUNT + " seat numbers are needed to check for duplicates.");
        }
        return candidateCount;
    }
 
    // =========================================================
    // Reads the seat numbers into an array, guarding against
    // non-numeric input with an UNCHECKED NumberFormatException.
    // =========================================================
    static int[] readSeatNumbers(Scanner scanner, int totalSeats) {
        int[] seatNumbers = new int[totalSeats];
 
        for (int index = 0; index < totalSeats; index++) {
            boolean isValidSeatNumber = false;
            while (!isValidSeatNumber) {
                System.out.print("Enter seat number #" + (index + 1) + ": ");
                String rawSeatInput = scanner.nextLine();
                try {
                    seatNumbers[index] = Integer.parseInt(rawSeatInput);
                    isValidSeatNumber = true;
                } catch (NumberFormatException exception) {
                    System.out.println("That is not a valid seat number. Please enter digits only.");
                }
            }
        }
        return seatNumbers;
    }
 
    // =========================================================
    // Core task: compare every seat number against every other
    // seat number using nested loops (no Collections classes).
    // =========================================================
    static void checkDuplicateSeats(int[] seatNumbers) {
        boolean[] alreadyReported = new boolean[seatNumbers.length];
        boolean duplicateFound = false;
 
        for (int firstIndex = 0; firstIndex < seatNumbers.length; firstIndex++) {
            if (alreadyReported[firstIndex]) {
                continue; // this seat number was already printed as a duplicate
            }
            for (int secondIndex = firstIndex + 1; secondIndex < seatNumbers.length; secondIndex++) {
                if (seatNumbers[firstIndex] == seatNumbers[secondIndex]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[firstIndex]);
                    alreadyReported[firstIndex] = true;
                    alreadyReported[secondIndex] = true;
                    duplicateFound = true;
                }
            }
        }
 
        if (!duplicateFound) {
            System.out.println("No Duplicate Seats Found");
        }
    }
}
 
