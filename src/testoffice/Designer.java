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
public class Designer extends WorkMan {

    public Designer() {
        position = "Designer";
        numOfExtraSkills = (int) Math.round(MyOffice.MAX_EXTRA_SKILLS * Math.random());

        availableFunctions = new String[1 + numOfExtraSkills];
        availableFunctions[0] = MyOffice.ALL_FUNCTIONS[1];
        availableFunctions = genUniqueExtraFunction();

        workHoursPerWeek = MyOffice.MAX_WH_PER_WEEK - MyOffice.MAX_WORK_DAY_PER_WEEK * (int) Math.round(3 * Math.random());
        workHoursPerDay = workHoursPerWeek / MyOffice.MAX_WORK_DAY_PER_WEEK;
        summExecTime = 0;

        rate = 12;
        isBusy = false;
        isAbsent = false;
    }
}
