package com.invex.technical.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.invex.technical.quiz.model.EmployeeEntity;

/**
 * The Interface EmployeeRepository.
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
	
	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<EmployeeEntity> findAll(Pageable pageable);
}
