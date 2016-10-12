/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testoffice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Nataly
 */
public class TestOffice {

    /**
     * @param args the command line arguments
     */
    private static final int FIRST_DAY = 1;
    private static Calendar calendar;
    private static Office testOffice;
    private static ExcelPrinter ep;
    private static int curMonth, curYear;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        boolean indicator = true;

        do {
            String inputData;
            System.out.println("Please enter enter the month and year for modeling office operation (mm/yyyy):");
            inputData = getString();

            if (inputData.contains("/")) {
                curMonth = Integer.parseInt(inputData.substring(0, inputData.indexOf("/")));
                curYear = Integer.parseInt(inputData.substring(inputData.lastIndexOf("/") + 1));
                if (curMonth >= 1 && curMonth <= 12 && curYear > 0) {
                    indicator = false;
                } else {
                    System.out.println("Incorrect data format");
                    indicator = true;
                }
            }
        } while (indicator);

        testOffice = new Office();

        ep = new ExcelPrinter(getMonthData(FIRST_DAY, curMonth, curYear), testOffice.getEmployeesData());
        modelingOffice(curMonth, curYear);
        ep.printToFile();
    }

    private static void modelingOffice(int month, int year) {
        for (int i = 1; i <= getMonthData(FIRST_DAY, year, year).getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            int curDay = getMonthData(i, month, year).get(Calendar.DAY_OF_WEEK);
            if (curDay > 1 && curDay < 7) {
                for (int j = 1; j <= MyOffice.MAX_WORK_HOURS_PER_DAY; j++) {
                    testOffice.performOffice(j);
                    ep.printCurDay(j + MyOffice.MAX_WORK_HOURS_PER_DAY * (i - 1), testOffice.getEmployeesData());
                }
            }
        }
        ep.printResults(testOffice.getEmployeesData());
    }

    private static Calendar getMonthData(int day, int month, int year) {
        Calendar cal;
        cal = GregorianCalendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day);
        return cal;
    }

    public static String getString() {
        LineNumberReader LNR = new LineNumberReader(new InputStreamReader(System.in));
        String S = "";
        try {
            S = LNR.readLine();
        } catch (IOException ioe) {
            S = "";
        }
        return S;
    }

    public static int getInt() {
        try {
            return Integer.parseInt(getString());
        } catch (Exception ie) {
            return 0;
        }
    }
}
