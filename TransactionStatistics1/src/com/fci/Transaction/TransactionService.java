package com.fci.Transaction;

import java.math.BigDecimal;

public class TransactionService {

	/**
	 * Shift array elements to lower index
	 * 
	 * @param elements
	 *            array to be shifted to lower index
	 * @param shiftAmount
	 *            the amount of shift
	 * @param intalizeValue
	 *            the intalization of free array elements
	 */
	public static void shiftArrayToLowerIndex(Object[] elements, int shiftAmount, Object intalizeValue) {

		if (shiftAmount >= 0) {

			for (int i = shiftAmount; i < elements.length; i++)
				elements[i - shiftAmount] = elements[i];

			int endshift = shiftAmount > elements.length ? 0 : (elements.length - shiftAmount);

			for (int i = endshift; i < elements.length; i++)
				elements[i] = intalizeValue;
		}

	}

	/**
	 * get statistics of array numbers
	 * 
	 * @param elements
	 *            array of elements to get its statistics
	 * @param length
	 *            number of elements starting from indx 0 that return its
	 *            statistics.
	 * @return Array of calculated values by the following order
	 *         [sm,count,avg,max,min]
	 */
	public static BigDecimal[] getElementStatistics(BigDecimal[] elements, int length) {

		BigDecimal sum = new BigDecimal(0);
		BigDecimal max = elements[0];
		BigDecimal min =new BigDecimal (Integer.MAX_VALUE);
		int count = 0;
		BigDecimal avg = new BigDecimal(0);

		for (int i = 0; i < length; i++) {

			if (elements[i].intValue() != 0) {

				sum = sum.add(elements[i]);

				if (elements[i].intValue() > max.intValue()) {
					max = elements[i];
				}

				if (elements[i].intValue() < min.intValue()) {
					min = elements[i];
				}

				count++;
			}
		}

		try {
			if (count > 0)
				avg = sum.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new BigDecimal[] { sum, new BigDecimal(count), avg, min ,max};

	}

}
