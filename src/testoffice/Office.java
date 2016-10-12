/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testoffice;

/**
 *
 * @author Nataly
 */
public class Office {

    private int numOfEmployees;
    private Employee[] employees;

    public Office() {
        numOfEmployees = MyOffice.MIN_EMPLOYEES + (int) Math.round((MyOffice.MAX_EMPLOYEES - MyOffice.MIN_EMPLOYEES) * Math.random());
        employees = new Employee[numOfEmployees];

        employees[0] = new Director(numOfEmployees);
        employees[1] = new Manager();
        employees[2] = new Accountant();

        for (int i = 3; i < numOfEmployees; i++) {
            employees[i] = genRandomEmployee();
        }
    }

    public Employee[] performOffice(int curHour) {

        employees[0].performFunction(null);

        String[] neededFunctions = new String[((Director) employees[0]).getNeededFunctions().length];
        System.arraycopy(((Director) employees[0]).getNeededFunctions(), 0, neededFunctions, 0, ((Director) employees[0]).getNeededFunctions().length);

        for (int i = 1; i < employees.length; i++) {
            if (curHour == 1) {
                employees[i].resetWorkedHoursCurDay();
            }
            employees[i].updateTimer();
            neededFunctions = employees[i].performFunction(neededFunctions);
        }

        ((Director) employees[0]).setFreelancersFunctions(neededFunctions);

        return employees;
    }

    private Employee genRandomEmployee() {
        int randomPosition = 1 + (int) Math.round((MyOffice.NUM_POSITION - 1) * Math.random());
        Employee emp;

        switch (randomPosition) {
            case MyOffice.MANAGER:
                emp = new Manager();
                break;
            case MyOffice.ACCOUNTANT:
                emp = new Accountant();
                break;
            case MyOffice.PROGRAMMER:
                emp = new Programmer();
                break;
            case MyOffice.DESIGNER:
                emp = new Designer();
                break;
            default:
                emp = new Tester();
                break;
        }
        return emp;
    }

    public Employee[] getEmployeesData() {
        return employees;
    }
}
