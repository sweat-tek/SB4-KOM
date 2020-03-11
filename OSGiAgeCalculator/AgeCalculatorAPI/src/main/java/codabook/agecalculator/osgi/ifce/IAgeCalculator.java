package codabook.agecalculator.osgi.ifce;

import java.util.Calendar;

public interface IAgeCalculator {

    public int calculateAge(Calendar dateOfBirth);
}
