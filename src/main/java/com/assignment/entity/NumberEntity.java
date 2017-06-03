package com.assignment.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;


public class NumberEntity {
	@Id
    private Long number;
	
	private Date insert_date;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(Date insert_date) {
		this.insert_date = insert_date;
	}
}
