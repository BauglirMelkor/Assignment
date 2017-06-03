package com.assignment.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.assignment.entity.NumberEntity;

public interface NumberRepository extends MongoRepository<NumberEntity, Long> {

	Page<NumberEntity> findAll(Pageable pageable);

}