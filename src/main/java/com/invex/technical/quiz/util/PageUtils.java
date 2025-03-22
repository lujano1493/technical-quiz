package com.invex.technical.quiz.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * The Class PageUtils.
 */
public class PageUtils {
	
	
	
	/**
	 * Creates the page request.
	 *
	 * @param orderBy the order by
	 * @param page the page
	 * @param size the size
	 * @return the page request
	 */
	public static PageRequest  createPageRequest(String orderBy ,Integer page,Integer size ) {
		Sort sort = SortUtil.buildSort(orderBy);
		 return  PageRequest.of(page - 1, size, sort); // Page index starts at 0
	}

}

