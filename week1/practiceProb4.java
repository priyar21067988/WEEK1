import java.util.Scanner;

/**
 * WarehouseInventoryBalancer
 * ---------------------------
 * Compares total quantities held in Section A and Section B of a
 * warehouse, reports whether they are balanced, and finds the
 * single highest quantity item across both sections.
 *
 * Concepts covered: arrays, loops, sum accumulation, conditional
 * comparison, tracking a maximum with its index, checked exceptions.
 */
public class WarehouseInventoryBalancer {

    // Custom CHECKED exception - the task requires equal-length arrays,
    // so a length mismatch is a declared business-rule violation.
    static class UnequalSectionSizeException extends Exception {
        public UnequalSectionSizeException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] sectionA = readSectionQuantities(scanner, "Section A");
        int[] sectionB = readSectionQuantities(scanner, "Section B");

        try {
            analyzeInventory(sectionA, sectionB);
        } catch (UnequalSectionSizeException exception) {
            System.out.println("Cannot analyze: " + exception.getMessage());
        }

        scanner.close();
    }

    // =========================================================
    // Reads item quantities for one section, guarding against
    // non-numeric input with an UNCHECKED NumberFormatException.
    // =========================================================
    static int[] readSectionQuantities(Scanner scanner, String sectionLabel) {
        System.out.print("Enter number of items in " + sectionLabel + ": ");
        int itemCount = readPositiveInt(scanner);
        int[] quantities = new int[itemCount];

        for (int index = 0; index < itemCount; index++) {
            boolean isValidQuantity = false;
            while (!isValidQuantity) {
                System.out.print(sectionLabel + " - Item " + (index + 1) + " quantity: ");
                String rawQuantity = scanner.nextLine();
                try {
                    quantities[index] = Integer.parseInt(rawQuantity);
                    isValidQuantity = true;
                } catch (NumberFormatException exception) {
                    System.out.println("Please enter a valid whole number.");
                }
            }
        }
        return quantities;
    }

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

    // =========================================================
    // Computes both section totals, compares them, and finds the
    // single highest quantity across both arrays. Declares a
    // CHECKED exception so callers must handle unequal-length input.
    // =========================================================
    static void analyzeInventory(int[] sectionA, int[] sectionB) throws UnequalSectionSizeException {
        if (sectionA.length != sectionB.length) {
            throw new UnequalSectionSizeException("Section A and Section B must be the same length ("
                    + sectionA.length + " vs " + sectionB.length + ").");
        }

        int totalA = calculateSectionTotal(sectionA);
        int totalB = calculateSectionTotal(sectionB);
        String balanceStatus = (totalA == totalB) ? "Balanced" : "Not Balanced";

        printInventoryReport(totalA, totalB, balanceStatus, sectionA, sectionB);
    }

    // Single-purpose accumulation method.
    static int calculateSectionTotal(int[] quantities) {
        int total = 0;
        for (int quantity : quantities) {
            total += quantity;
        }
        return total;
    }

    // Scans both arrays to find the highest quantity and its
    // section/index, then prints the full report in one line.
    static void printInventoryReport(int totalA, int totalB, String balanceStatus,
                                      int[] sectionA, int[] sectionB) {
        int highestQuantity = sectionA[0];
        String highestSectionLabel = "Section A";
        int highestItemIndex = 0;

        for (int index = 0; index < sectionA.length; index++) {
            if (sectionA[index] > highestQuantity) {
                highestQuantity = sectionA[index];
                highestSectionLabel = "Section A";
                highestItemIndex = index;
            }
        }
        for (int index = 0; index < sectionB.length; index++) {
            if (sectionB[index] > highestQuantity) {
                highestQuantity = sectionB[index];
                highestSectionLabel = "Section B";
                highestItemIndex = index;
            }
        }

        System.out.println("Section A Total: " + totalA
                + " | Section B Total: " + totalB
                + " | Status: " + balanceStatus
                + " | Highest Quantity: " + highestQuantity
                + " (" + highestSectionLabel + ", Item " + (highestItemIndex + 1) + ")");
    }
}
