package com.assignment.demo.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.demo.dto.NumberDTO;
import com.assignment.demo.repository.NumberRepository;
import com.assignment.entity.NumberEntity;
import com.assignment.exception.NumberCannotBeFoundException;

@RunWith(SpringRunner.class)
public class NumberServiceTest {

	private NumberService numberService;

	@MockBean
	private NumberRepository repository;

	private NumberEntity numberEntity = null;

	private NumberDTO numberDTO = null;

	private List<NumberEntity> numberListEntity = new ArrayList<NumberEntity>();

	@Before
	public void setup() {

		numberService = new NumberService(repository);
		numberEntity = new NumberEntity();
		numberEntity.setNumber(1L);
		numberEntity.setInsert_date(new Date());
		numberListEntity.add(numberEntity);
		numberDTO = new NumberDTO();
		numberDTO.setNumber(1L);
	}

	@Test
	public void testInsertNumberSuccessfully() throws Exception {

		when(repository.save(any(NumberEntity.class))).thenReturn(numberEntity);
		NumberEntity result = numberService.insertNumber(1L);
		assert (result.getNumber().equals(1L));
	}

	@Test
	public void testDeleteNumberSuccessfully() throws Exception {
		when(repository.findOne(1L)).thenReturn(numberEntity);
		doNothing().when(repository).delete(1L);
		NumberEntity result = numberService.deleteNumber(1L);
		assert (result.getNumber().equals(1L));
	}

	@Test(expected = NumberCannotBeFoundException.class)
	public void testDeleteNumberCannotBeFoundError() throws Exception {

		doNothing().when(repository).delete(1L);
		NumberEntity result = numberService.deleteNumber(1L);

	}

	@Test
	public void testGetNumberSuccessfully() throws Exception {

		when(repository.findOne(1L)).thenReturn(numberEntity);
		NumberEntity result = numberService.findNumber(1L);
		assert (result.getNumber().equals(1L));
	}

	@Test(expected = NumberCannotBeFoundException.class)
	public void testGetNumberCannotBeFoundError() throws Exception {

		NumberEntity result = numberService.findNumber(1L);

	}

	@Test
	public void testGetNumberListAscendingSuccessfully() throws Exception {

		when(repository.findAll(any(Sort.class))).thenReturn(numberListEntity);
		List<NumberEntity> list = numberService.findAll(true);
		assert (list.size() == 1);
		assert (list.get(0).getNumber() == 1);

	}

	@Test
	public void testGetNumberListDescendingSuccessfully() throws Exception {

		when(repository.findAll(any(Sort.class))).thenReturn(numberListEntity);
		List<NumberEntity> list = numberService.findAll(false);
		assert (list.size() == 1);
		assert (list.get(0).getNumber() == 1);
	}

	@Test
	public void testGetMinimumNumberSuccessfully() throws Exception {
		PageImpl<NumberEntity> pageRequest = new PageImpl(numberListEntity);
		when(repository.findAll(any(Pageable.class))).thenReturn(pageRequest);
		NumberEntity numberEntity = numberService.findMaxMin(false);
		assert (numberEntity.getNumber().equals(1L));
	}
	
	@Test(expected = NumberCannotBeFoundException.class)
	public void testGetMinimumNumberCannotBeFound() throws Exception {
		PageImpl<NumberEntity> pageRequest = new PageImpl(numberListEntity);
		when(repository.findAll(any(Pageable.class))).thenReturn(null);
		NumberEntity numberEntity = numberService.findMaxMin(false);
		
	}

	@Test
	public void testGetMaximumNumberSuccessfully() throws Exception {
		PageImpl<NumberEntity> pageRequest = new PageImpl(numberListEntity);
		when(repository.findAll(any(Pageable.class))).thenReturn(pageRequest);
		NumberEntity numberEntity = numberService.findMaxMin(true);
		assert (numberEntity.getNumber().equals(1L));
	}
	
	@Test(expected = NumberCannotBeFoundException.class)
	public void testGetMaximumNumberCannotBeFound() throws Exception {
		PageImpl<NumberEntity> pageRequest = new PageImpl(numberListEntity);
		when(repository.findAll(any(Pageable.class))).thenReturn(null);
		NumberEntity numberEntity = numberService.findMaxMin(true);
		
	}

}
