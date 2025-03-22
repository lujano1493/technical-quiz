package com.invex.technical.quiz.util;

import java.time.LocalDate;
import java.time.Period;


/**
 * The Class Utils.
 */
public class Utils {

	
	   /**
   	 * Calculate age.
   	 *
   	 * @param birthDate the birth date
   	 * @return the int
   	 */
   	public static int calculateAge(LocalDate birthDate) {
	        LocalDate currentDate = LocalDate.now();
	        return Period.between(birthDate, currentDate).getYears();
	    }
}
