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
public class Director extends Employee {

    private String[] neededFunctions, gotoFreelancerFunctions;
    private int numOfEmployees, numOfNewNeededFunctions;

    public Director(int noe) {
        position = "Director";

        availableFunctions = new String[1];
        availableFunctions[0] = "";

        workHoursPerWeek = MyOffice.MAX_WH_PER_WEEK;
        workHoursPerDay = workHoursPerWeek / MyOffice.MAX_WORK_DAY_PER_WEEK;
        summExecTime = 0;

        rate = 0;
        fixRate = 5000;
        numOfEmployees = noe;
    }

    @Override
    public String[] performFunction(String[] arr) {
        summExecTime += 1;
        numOfNewNeededFunctions = MyOffice.MIN_NEW_FUNCTIONS + (int) Math.round((numOfEmployees - 2) * Math.random());
        neededFunctions = new String[numOfNewNeededFunctions];
        for (int i = 0; i < numOfNewNeededFunctions; i++) {
            neededFunctions[i] = MyOffice.ALL_FUNCTIONS[(int) Math.round((MyOffice.ALL_FUNCTIONS.length - 1) * Math.random())];
        }
        return null;
    }

    public String[] getNeededFunctions() {
        return neededFunctions;
    }

    public void setFreelancersFunctions(String[] arr) {
        gotoFreelancerFunctions = arr;
    }

    public String[] getFreelancersFunctions() {
        return gotoFreelancerFunctions;
    }
}
