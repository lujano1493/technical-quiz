package com.invex.technical.quiz.enums;

import com.invex.technical.quiz.constant.Constant;
/**
 * The Enum Gender.
 */
public enum Gender {

	/** The male. */
	male(Constant.GENDER_MALE_NAME),/** The female. */
female(Constant.GENDER_FEMALE_NAME);

	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new gender.
	 *
	 * @param name the name
	 */
	Gender(String name) {
		this.setName(name);
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
