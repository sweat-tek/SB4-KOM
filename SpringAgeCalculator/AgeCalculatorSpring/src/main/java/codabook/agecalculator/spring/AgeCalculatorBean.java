package codabook.agecalculator.spring;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AgeCalculatorBean {
	public int calculateAge(Calendar dateOfBirth) {
		Calendar rightNow = new GregorianCalendar();

		int currentYear = rightNow.get(Calendar.YEAR);
		int currentMonth = rightNow.get(Calendar.MONTH);
		int currentDate = rightNow.get(Calendar.DATE);

		int birthYear = dateOfBirth.get(Calendar.YEAR);
		int birthMonth = dateOfBirth.get(Calendar.MONTH);
		int birthDate = dateOfBirth.get(Calendar.DATE);

		int age = 0;

		boolean isCurrentYearBdayPassed = (currentMonth > birthMonth)
				|| ((currentMonth == birthMonth) && (currentDate >= birthDate));

		if (isCurrentYearBdayPassed) {
			age = currentYear - birthYear;
		} else {
			age = currentYear - 1 - birthYear;
		}

		return age;
	}

}
