package com.invex.technical.quiz.model;

import java.time.LocalDate;

import com.invex.technical.quiz.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class EmployeeEntity.
 */
@Entity
@Table(name="employees")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
	
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The first name. */
	@Column(name="first_name" ,nullable = false)
	private String firstName;
	
	/** The second name. */
	@Column(name="second_name" ,nullable = true)
	private String secondName;
	
	/** The last name. */
	@Column(name="last_name" ,nullable = false)
	private String lastName;
	
	/** The maternal last name. */
	@Column(name="maternal_last_name" ,nullable = true)
	private String maternalLastName;
	
	/** The age. */
	@Column(name="age", nullable = false )
	private Integer age;
	
	/** The gender. */
	@Column(name="gender",nullable = false)
	private Gender gender;
	
	/** The date of birth. */
	@Column(name="date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	/** The position. */
	@Column(name="position")
	private String position;


}
