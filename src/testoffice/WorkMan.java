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
public class WorkMan extends Employee{
    
     @Override
    public String[] performFunction(String[] needArr) {
        for (int i = 0; i < needArr.length; i++) {
            for (int j = 0; j < availableFunctions.length; j++) {
                if (isBusy == false) {
                    if (isAbsent == false) {
                        if (needArr[i].compareTo(availableFunctions[j]) == 0) {
                            isBusy = true;
                            executionTime = 1 + (int) Math.round(Math.random());
                            currentState = availableFunctions[j] + " x" + String.valueOf(executionTime);
                            needArr[i] = "";
                            j = availableFunctions.length;
                            i = needArr.length;
                        } else {
                            currentState = MyOffice.FREE;
                            isBusy = false;
                        }
                    }
                }
            }
        }
        return needArr;
    }

    protected String[] genUniqueExtraFunction() {
        for (int i = 1; i < availableFunctions.length; i++) {
            String extraFunction = null;
            do {
                extraFunction = MyOffice.ALL_FUNCTIONS[(int) Math.round((MyOffice.ALL_FUNCTIONS.length - 1) * Math.random())];
            } while (checkArr(extraFunction));
            availableFunctions[i] = extraFunction;
        }
        return availableFunctions;
    }

    protected boolean checkArr(String s) {
        for (int i = 0; i < availableFunctions.length; i++) {
            if (availableFunctions[i] != null && availableFunctions[i].equals(s)) {
                return true;
            }
        }
        return false;
    }    
}
