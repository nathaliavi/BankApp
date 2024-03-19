package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Utils {
	
	static NumberFormat formatValues = new DecimalFormat("R$ #,##0.00");

	public static String doubleToString(Double value) {
		return formatValues.format(value);
	}
}
