
import java.util.Scanner;
 
/**
 * StringLabPractice
 * ------------------
 * A single lab-practice file covering:
 *   1. Creating Strings (literal, new String(), char[])
 *   2. Escape sequences
 *   3. Taking String input
 *   4. String arrays
 *   5. Strings as method parameters
 *   6. Built-in String methods
 *   7. ASCII character codes
 *   8. Checked and unchecked exception handling
 *
 * Every task is written as its own method (never inline in main),
 * with descriptive verb-based names, local variables named by role
 * (input / result / constant), and camelCase naming throughout.
 */
public class StringLabPractice {
 
    // A custom CHECKED exception - must be declared with "throws" and handled.
    // Used when user input fails a business rule (e.g. empty string).
    static class BlankInputException extends Exception {
        public BlankInputException(String message) {
            super(message);
        }
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        printEscapeSequenceDemo();
        demonstrateStringCreation();
 
        // ---- Take String input from the user (checked exception handled) ----
        String userName = readNonBlankLine(scanner, "Enter your full name: ");
        String greeting = buildGreeting(userName);
        System.out.println(greeting);
 
        // ---- String array practice ----
        String[] fruits = readStringArray(scanner, "Enter number of fruits: ");
        printStringArray(fruits);
        System.out.println("Longest fruit name: " + findLongestString(fruits));
 
        // ---- Built-in String methods practice ----
        demonstrateStringMethods(userName);
 
        // ---- ASCII character codes ----
        char sampleChar = readSingleCharacter(scanner, "Enter one character to check its ASCII code: ");
        printAsciiCode(sampleChar);
 
        // ---- Unchecked exception handling (RuntimeException family) ----
        safelyParseInteger(scanner);
        safelyAccessCharacter(userName);
 
        scanner.close();
    }
 
    // =========================================================
    // 1. ESCAPE SEQUENCES
    // =========================================================
    static void printEscapeSequenceDemo() {
        System.out.println("---- Escape Sequence Demo ----");
        System.out.println("Name\tScore\tGrade");     // \t = tab
        System.out.println("Priya\t95\tA");
        System.out.println("She said, \"Java is fun!\""); // \" = literal quote
        System.out.println("Path: C:\\Users\\Priya\\labs"); // \\ = literal backslash
        System.out.println("Line1\nLine2");              // \n = newline
        System.out.println();
    }
 
    // =========================================================
    // 2. CREATING STRINGS (three common ways)
    // =========================================================
    static void demonstrateStringCreation() {
        System.out.println("---- String Creation Demo ----");
 
        // (a) String literal - stored in the String pool
        String literalString = "Java Strings";
 
        // (b) Using "new" - forces a new object on the heap
        String newString = new String("Java Strings");
 
        // (c) Building from a char array
        char[] characterArray = {'J', 'a', 'v', 'a'};
        String fromCharArray = new String(characterArray);
 
        System.out.println("Literal: " + literalString);
        System.out.println("new String(): " + newString);
        System.out.println("From char[]: " + fromCharArray);
 
        // == compares references, .equals() compares content
        System.out.println("literalString == newString : " + (literalString == newString));
        System.out.println("literalString.equals(newString) : " + literalString.equals(newString));
        System.out.println();
    }
 
    // =========================================================
    // 3. TAKING STRING INPUT (with CHECKED exception handling)
    // =========================================================
 
    /**
     * Reads one line of input and rejects blank input using a
     * custom CHECKED exception (BlankInputException).
     */
    static String readNonBlankLine(Scanner scanner, String prompt) {
        String result = null;
        boolean isValidInput = false;
 
        while (!isValidInput) {
            System.out.print(prompt);
            String rawInput = scanner.nextLine();
            try {
                result = validateNotBlank(rawInput);
                isValidInput = true;
            } catch (BlankInputException exception) {
                System.out.println("Invalid input: " + exception.getMessage() + " Try again.");
            }
        }
        return result;
    }
 
    // This method declares a CHECKED exception - caller is forced to handle it.
    static String validateNotBlank(String candidateInput) throws BlankInputException {
        if (candidateInput.trim().isEmpty()) {
            throw new BlankInputException("Input cannot be blank.");
        }
        return candidateInput.trim();
    }
 
    // =========================================================
    // 4. STRING ARRAYS
    // =========================================================
    static String[] readStringArray(Scanner scanner, String prompt) {
        System.out.print(prompt);
        int arraySize = readPositiveInt(scanner);
        String[] inputArray = new String[arraySize];
 
        for (int index = 0; index < arraySize; index++) {
            System.out.print("Fruit #" + (index + 1) + ": ");
            inputArray[index] = scanner.nextLine();
        }
        return inputArray;
    }
 
    static void printStringArray(String[] stringArray) {
        System.out.println("---- String Array Contents ----");
        for (String item : stringArray) {
            System.out.println("- " + item);
        }
    }
 
    // =========================================================
    // 5. STRINGS AS METHOD PARAMETERS (+ returning a new String)
    // =========================================================
    static String buildGreeting(String name) {
        // name is passed by reference, but Strings are immutable,
        // so this method cannot alter the caller's variable -
        // it must return a brand-new String instead.
        return "Hello, " + name.trim() + "! Welcome to the String lab.";
    }
 
    static String findLongestString(String[] stringArray) {
        String longestSoFar = "";
        for (String candidate : stringArray) {
            if (candidate.length() > longestSoFar.length()) {
                longestSoFar = candidate;
            }
        }
        return longestSoFar;
    }
 
    // =========================================================
    // 6. STRING CLASS BUILT-IN METHODS
    // =========================================================
    static void demonstrateStringMethods(String sourceString) {
        System.out.println("---- Built-in String Methods Demo ----");
 
        int length = sourceString.length();
        String upperCase = sourceString.toUpperCase();
        String lowerCase = sourceString.toLowerCase();
        String trimmed = sourceString.trim();
        char firstChar = sourceString.charAt(0);
        int indexOfSpace = sourceString.indexOf(' ');
        boolean containsSpace = sourceString.contains(" ");
        String[] splitWords = sourceString.trim().split("\\s+");
 
        System.out.println("Length: " + length);
        System.out.println("Uppercase: " + upperCase);
        System.out.println("Lowercase: " + lowerCase);
        System.out.println("Trimmed: '" + trimmed + "'");
        System.out.println("First character: " + firstChar);
        System.out.println("Index of first space: " + indexOfSpace);
        System.out.println("Contains space? " + containsSpace);
        System.out.println("Word count (split): " + splitWords.length);
 
        if (sourceString.length() >= 3) {
            String firstThreeLetters = sourceString.substring(0, 3);
            System.out.println("First 3 characters: " + firstThreeLetters);
        }
        System.out.println();
    }
 
    // =========================================================
    // 7. ASCII CHARACTER CODES
    // =========================================================
    static char readSingleCharacter(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        while (line.isEmpty()) {
            System.out.print("Please enter exactly one character: ");
            line = scanner.nextLine();
        }
        return line.charAt(0);
    }
 
    static void printAsciiCode(char character) {
        int asciiCode = (int) character;         // char -> int gives ASCII value
        char roundTrip = (char) asciiCode;        // int -> char converts back
        System.out.println("---- ASCII Code Demo ----");
        System.out.println("Character: " + character);
        System.out.println("ASCII code: " + asciiCode);
        System.out.println("Converted back to character: " + roundTrip);
        System.out.println();
    }
 
    // =========================================================
    // 8. UNCHECKED EXCEPTIONS (RuntimeException family)
    // =========================================================
 
    /**
     * Demonstrates catching NumberFormatException, an UNCHECKED
     * exception - the compiler does not force you to catch it,
     * but good practice says you should anyway.
     */
    static void safelyParseInteger(Scanner scanner) {
        System.out.println("---- Unchecked Exception Demo: NumberFormatException ----");
        System.out.print("Enter a whole number (try entering letters to see the error handled): ");
        String rawNumber = scanner.nextLine();
        try {
            int parsedNumber = Integer.parseInt(rawNumber);
            System.out.println("Parsed successfully: " + parsedNumber);
        } catch (NumberFormatException exception) {
            System.out.println("That wasn't a valid whole number. Defaulting to 0.");
        } finally {
            System.out.println("Number parsing attempt finished.\n");
        }
    }
 
    /**
     * Demonstrates catching StringIndexOutOfBoundsException, another
     * UNCHECKED exception, when accessing a character beyond the
     * String's length.
     */
    static void safelyAccessCharacter(String sourceString) {
        System.out.println("---- Unchecked Exception Demo: StringIndexOutOfBoundsException ----");
        int riskyIndex = sourceString.length() + 5; // deliberately out of range
        try {
            char result = sourceString.charAt(riskyIndex);
            System.out.println("Character at index " + riskyIndex + ": " + result);
        } catch (StringIndexOutOfBoundsException exception) {
            System.out.println("Index " + riskyIndex + " is out of bounds for a string of length "
                    + sourceString.length() + ".");
        }
    }
 
    // Small helper used by readStringArray() - keeps size-reading logic
    // in one place and defends against non-numeric / non-positive input.
    static int readPositiveInt(Scanner scanner) {
        int value = -1;
        while (value <= 0) {
            String rawInput = scanner.nextLine();
            try {
                value = Integer.parseInt(rawInput);
                if (value <= 0) {
                    System.out.print("Please enter a number greater than 0: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Not a valid number, try again: ");
            }
        }
        return value;
    }
}
