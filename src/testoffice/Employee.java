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
public abstract class Employee {   
    
    protected String position = "";
    protected String currentState, tempState;
    protected String[] availableFunctions, freelanceFunctions;
    protected int workHoursPerWeek, workHoursPerDay, workedHoursCurDay, executionTime, summExecTime;
    protected int numOfExtraSkills;
    protected float rate, fixRate, totalEarings;
    protected boolean isBusy, isAbsent;

    public abstract String[] performFunction(String[] arr);

    public void resetWorkedHoursCurDay() {
        workedHoursCurDay = 0;
        if (isAbsent) {
            currentState = tempState;
        }
        isAbsent = false;
    }

    public void updateTimer() {
        if (workedHoursCurDay < workHoursPerDay) {
            workedHoursCurDay++;
        } else {
            if (isAbsent == false) {
                tempState = currentState;
                isAbsent = true;
                currentState = MyOffice.ABSENT;
            }
        }
        if (isBusy == true && isAbsent == false) {
            executionTime--;
            summExecTime += 1;
            if (executionTime <= 0) {
                executionTime = 0;
                isBusy = false;
            }
        }
    }

    public String[] getAvailableFunctions() {
        return availableFunctions;
    }

    public String getInProcessFunction() {
        return currentState;
    }

    public String getPosition() {
        return position;
    }

    public int getWorkHoursPerWeek() {
        return workHoursPerWeek;
    }

    public int getSumExecTime() {
        return summExecTime;
    }

    public float getRate() {
        return rate;
    }

    public float getFixRate() {
        return fixRate;
    }

    public float getTotalEarings() {
        totalEarings = getRate() * getSumExecTime();
        if (getRate() == 0) {
            totalEarings = getFixRate();
        }
        return totalEarings;
    }

    public boolean getIsBusy() {
        return isBusy;

    }

    public boolean getIsAbsent() {
        return isAbsent;
    }

    public String arrayToString(String[] arrString) {
        String concatFunct = "";
        String space;
        for (int i = 0; i < arrString.length; i++) {
            space = "";
            if ((i < arrString.length - 1) && (arrString[i]).compareTo("") != 0) {
                space = ", ";
            }
            concatFunct = concatFunct + arrString[i] + space;
        }
        return concatFunct;
    }    
}
