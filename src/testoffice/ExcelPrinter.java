/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testoffice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Nataly
 */
public class ExcelPrinter {

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFCellStyle style;
    private CellRangeAddress region;
    private Row[] employeeRow;
    private Row titleRow, dayRow, hoursRow, freelancerRow;
    private int numCapTable = 4;
    private int numExtraResultsColumn = 4;

    public ExcelPrinter(Calendar c, Employee[] emp) {

        int monthDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("New sheet");
        style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER);

        printTableCap(c, monthDay);
        printEmployees(emp);

        sheet.autoSizeColumn(0);
    }

    private void printTableCap(Calendar c, int monthDay) {
        region = new CellRangeAddress(0, 0, 0, monthDay * MyOffice.MAX_WORK_HOURS_PER_DAY + numExtraResultsColumn);
        sheet.addMergedRegion(region);

        titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue(MyOffice.NAME);
        titleRow.getCell(0).setCellStyle(style);

        dayRow = sheet.createRow(1);
        hoursRow = sheet.createRow(2);

        for (int i = 1; i <= monthDay; i++) {
            for (int j = 1; j <= MyOffice.MAX_WORK_HOURS_PER_DAY; j++) {
                int k = j + MyOffice.MAX_WORK_HOURS_PER_DAY * (i - 1);

                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), i);

                dayRow.createCell(k).setCellValue(i + "." + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR) + " " + MyOffice.WEEK_DAY[c.get(Calendar.DAY_OF_WEEK)]);
                hoursRow.createCell(k).setCellValue(j);

                dayRow.getCell(k).setCellStyle(style);
                hoursRow.getCell(k).setCellStyle(style);

                if (k % MyOffice.MAX_WORK_HOURS_PER_DAY == 0) {
                    region = new CellRangeAddress(1, 1, i * j - (MyOffice.MAX_WORK_HOURS_PER_DAY - 1), k);
                    sheet.addMergedRegion(region);
                }
            }
        }
        dayRow.createCell((int) (dayRow.getLastCellNum())).setCellValue("Hours Worked, hours");
        dayRow.createCell((int) (dayRow.getLastCellNum())).setCellValue("Rate, $/hour");
        dayRow.createCell((int) (dayRow.getLastCellNum())).setCellValue("Fix Rate, $/month");
        dayRow.createCell((int) (dayRow.getLastCellNum())).setCellValue("Total Earnings, $");
    }

    private void printEmployees(Employee[] emp) {
        employeeRow = new Row[emp.length];

        for (int i = 0; i < emp.length; i++) {
            employeeRow[i] = sheet.createRow(numCapTable + i);
            String extraFunctios = extraFunctios = " (" + emp[i].arrayToString(emp[i].getAvailableFunctions()) + ")";
            employeeRow[i].createCell(0).setCellValue(emp[i].getPosition() + extraFunctios);
        }
        freelancerRow = sheet.createRow(numCapTable + employeeRow.length + 1);
        freelancerRow.createCell(0).setCellValue("Freelancers");
    }

    public void printCurDay(int curDay, Employee[] emp) {
        employeeRow[0].createCell(curDay).setCellValue((emp[0].arrayToString(((Director) emp[0]).getNeededFunctions())));
        for (int i = 1; i < emp.length; i++) {
            employeeRow[i].createCell(curDay).setCellValue(emp[i].getInProcessFunction());
        }
        freelancerRow.createCell(curDay).setCellValue((emp[0].arrayToString(((Director) emp[0]).getFreelancersFunctions())));
    }

    public void printResults(Employee[] emp) {
        for (int i = 0; i < emp.length; i++) {
            employeeRow[i].createCell(dayRow.getLastCellNum() - 1).setCellValue(emp[i].getTotalEarings());
            employeeRow[i].createCell(employeeRow[i].getLastCellNum() - 2).setCellValue(emp[i].getFixRate());
            employeeRow[i].createCell(employeeRow[i].getLastCellNum() - 3).setCellValue(emp[i].getRate());
            employeeRow[i].createCell(employeeRow[i].getLastCellNum() - 4).setCellValue(emp[i].getSumExecTime());
        }
    }

    public void printToFile() {

        File folder = new File("results");
        if (!folder.exists()) {
            folder.mkdir();
        }

        try (FileOutputStream out = new FileOutputStream(new File("results/ReportMyOffice.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel file has been created!");
    }
}
