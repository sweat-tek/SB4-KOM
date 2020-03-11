package codabook.agecalculator.osgi.client;

import codabook.agecalculator.osgi.ifce.IAgeCalculator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

public class AgeCalculatorOSGiClient {

    ComponentContext context;
    ServiceReference reference;
    IAgeCalculator ageCalculator;

    public void activate(ComponentContext context) {

        System.out.println("What is your year of birth?");
        int year = 1980;

        System.out.println("What is your month of birth (1-12)?");
        int month = 10;

        System.out.println("What is your date of birth (1-31)?");
        int date = 30;

        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.clear();
        dateOfBirth.set(year, month - 1, date);

        if (reference != null) {
            ageCalculator = (IAgeCalculator) context.locateService(
                    "IAgeCalculator", reference);

            int age = ageCalculator.calculateAge(dateOfBirth);

            System.out.println("Your age is " + age);

        }

    }

    public void gotService(ServiceReference reference) {
        System.out.println("Bind Service");
        this.reference = reference;
    }

    public void lostService(ServiceReference reference) {
        System.out.println("unbind Service");
        this.reference = null;
    }

}
