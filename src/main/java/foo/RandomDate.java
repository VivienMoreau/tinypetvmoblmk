package foo;

import java.util.GregorianCalendar;

public class RandomDate {

    public static void main(String[] args) {
 
    }

    public static String randDate() {
    	
    	GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2010, 2021);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        
        String monthValue; 
        int month = gc.get(gc.MONTH) + 1;
        
        if (month < 10) {
        	monthValue = "0" + month;
        } else {
        	monthValue = "" + month;
        }
        
        String dayValue; 
        int day = gc.get(gc.DAY_OF_MONTH);
        
        if (day < 10) {
        	dayValue = "0" + day;
        } else {
        	dayValue = "" + day;
        }
        System.out.println(gc.get(gc.YEAR) + "-" + monthValue + "-" + dayValue);
        
        return gc.get(gc.YEAR) + "-" + monthValue + "-" + dayValue;

    }
    
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    
}