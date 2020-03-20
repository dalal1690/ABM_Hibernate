package hibernate.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String PATTERN_D2_M2_Y4 = "dd/MM/yyyy";
	public static String PATTERN_M2_Y4 = "MM/yyyy";

	public static String pasarDateAString(String pattern, Date fechaDeVenta) {
		SimpleDateFormat sdfDMY = new SimpleDateFormat(pattern);
		return sdfDMY.format(fechaDeVenta);
	}

}
