package com.assignment.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.assignment.demo.repository.NumberRepository;
import com.assignment.entity.NumberEntity;
import com.assignment.exception.NumberAlreadyExistsException;
import com.assignment.exception.NumberCannotBeFoundException;

@Service
public class NumberService {
	
	private final NumberRepository repository;
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public NumberService(NumberRepository repository,MongoTemplate mongoTemplate){
		this.repository=repository;
		this.mongoTemplate=mongoTemplate;
	}
	
	
	public NumberEntity insertNumber(Long number){
		if(repository.findOne(number)!=null){
			throw new NumberAlreadyExistsException("number already exists");
		}
		NumberEntity numberEntity=new NumberEntity();
		numberEntity.setNumber(number);
		numberEntity.setInsert_date(new Date());
		return repository.save(numberEntity);
		
	}


	public NumberEntity deleteNumber(Long number) {
		NumberEntity numberEntity=repository.findOne(number);
		if(numberEntity==null){
			throw new NumberCannotBeFoundException("Number Cannot Be Found");
		}
		repository.delete(numberEntity);
		return numberEntity;
	}
	
	public NumberEntity findNumber(Long number) {
		NumberEntity numberEntity=repository.findOne(number);
		if(numberEntity==null){
			throw new NumberCannotBeFoundException("Number Cannot Be Found");
		}
		return numberEntity;
	}


	public List<NumberEntity> findAll(Boolean ascending) {
		Sort sort=null;
		if(ascending==true){
		sort=new Sort(Sort.Direction.ASC, "number");
		}else{
		sort=new Sort(Sort.Direction.DESC, "number");
		}
		
		return repository.findAll(sort);
	}
	
	
	public NumberEntity findMaxMin(boolean max) {
		Sort sort=null;
		if(max==true){
		sort=new Sort(Sort.Direction.DESC, "number");
		}else{
		sort=new Sort(Sort.Direction.ASC, "number");
		}
		
		PageRequest pageRequest=new PageRequest(0,1,sort);		
		return repository.findAll(pageRequest).getContent().get(0);
	}



	
}
