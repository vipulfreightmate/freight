package com.freight;



import java.util.Collections;
import java.util.Scanner;

/**
 * Consignment Note Number Generator: This class is responsible to generate
 * uniqueId from the input file which must be json and need to be placed at
 * specific location
 * 
 * @author vvohra
 *
 */
public class ConnoteGenerator {

	public static int RANGE_START = 19000;
	public static int RANGE_END = 20000;
	final static String PREFIX = "FMCC";
	static int DIGIT_COUNT = 10;
	public static int LAST_USED_INDEX = 19604;
	private static Scanner scanner;

	public static void main(String[] args) {

		System.out.println("Welcome ConnoteGenerator! Press <Enter> after providing details one by one.");
		try {

		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Account Number: ");
		scanner = new Scanner(System.in);
		String accountNumber = scanner.nextLine().trim();

		System.out.println("Digit: ");
		DIGIT_COUNT = Integer.parseInt(scanner.nextLine().trim());

		System.out.println("last Used Index: ");
		LAST_USED_INDEX = Integer.parseInt(scanner.nextLine().trim());

		System.out.println("Range Start: ");
		RANGE_START = Integer.parseInt(scanner.nextLine().trim());

		System.out.println("Range End: ");
		RANGE_END = Integer.parseInt(scanner.nextLine().trim());

		if (accountNumber != null && !accountNumber.equals("")) {

			String consignmenIndex = calculateIndex();
			if (consignmenIndex != null) {
				int checkSum = calculateChecksum(consignmenIndex);
				System.out.println("------------------------------");
				System.out.println("Connote number: " + PREFIX + accountNumber + consignmenIndex + checkSum);
				System.out.println("------------------------------");
			}

		}
		main(null);
	}

	/**
	 * Method to calculate index considering last used index and digit count.
	 * The index is padded with 0's to make the index "Digits" characters long
	 * 
	 * @return
	 */
	public static String calculateIndex() {
		int newIndex = LAST_USED_INDEX + 1;

		if (newIndex > RANGE_END || newIndex < RANGE_START)
			System.out.println("Ops ! The index is not in Range");
		else {
			int padLength = String.valueOf(newIndex).length();
			if (DIGIT_COUNT < padLength) {
				System.out.println("Please provide correct Digit count");
			} else {
				return String.join("", Collections.nCopies(DIGIT_COUNT - padLength, "0")) + newIndex;
			}
		}
		return null;
	}

	/** Method to calculate checkSum as per the logic given
	 * @param index: string value of consolidated index (with padding)
	 * @return
	 */
	public static int calculateChecksum(String index) {
		int firstElementGap = 1;
		int secondElementGap = 2;
		int firstDigit = 0;
		int secondDigit = 0;

		// Traverse the string i.e indexNumber
		for (int i = index.length(); (i >= 0 && firstElementGap < i); i--) {

			// 1. Adding every second number in the index from the right
			// starting at the first element
			firstDigit = firstDigit + Character.getNumericValue(index.charAt(i - firstElementGap));
			firstElementGap = firstElementGap + 1;

			// 3. Adding every second number in the index from the right
			// starting at the second element
			secondDigit = secondDigit + Character.getNumericValue(index.charAt(i - secondElementGap));
			secondElementGap = secondElementGap + 1;
		}

		// 2. Multiply firstDigit number by 3
		firstDigit = firstDigit * 3;

		// 4. Multiply secondDigit number by 7
		secondDigit = secondDigit * 7;

		// 5.Add the results of step 1 and step 2
		int num = firstDigit + secondDigit;
		int multipleOfTen = num;

		if (num % 10 != 0) {
			multipleOfTen = num + 1;
		}
		// 6. Get the difference between that number and the next multiple of 10
		int checksum = multipleOfTen - num;
		return checksum;
	}

}
