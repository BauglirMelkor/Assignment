package com.assignment.demo.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.assignment.entity.NumberEntity;

public class NumberDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8231585779841000654L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	private Long number;

	private String insert_date;

	public NumberDTO() {

	}

	public NumberDTO(Long number, Date insert_date) {
		this.number = number;
		this.insert_date = sdf.format(insert_date);
	}

	public NumberDTO(NumberEntity numberEntity) {
		this.number = numberEntity.getNumber();
		this.insert_date = sdf.format(numberEntity.getInsert_date());
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}

}
