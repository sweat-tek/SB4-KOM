package agecalculatorspringclient;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import codabook.agecalculator.spring.AgeCalculatorBean;

public class AgeCalculatorSpringClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        AgeCalculatorBean ageCalculatorBean = (AgeCalculatorBean) context.getBean("ageCalculatorBean");

        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your year of birth?");
        int year = scanner.nextInt();

        System.out.println("What is your month of birth (1-12)?");
        int month = scanner.nextInt();

        System.out.println("What is your date of birth (1-31)?");
        int date = scanner.nextInt();

        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.clear();
        dateOfBirth.set(year, month - 1, date);

        int age = ageCalculatorBean.calculateAge(dateOfBirth);

        System.out.println("Your age is " + age);

        scanner.close();
    }

}
