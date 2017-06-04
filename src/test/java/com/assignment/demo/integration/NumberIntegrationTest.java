package com.assignment.demo.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.assignment.demo.dto.NumberDTO;
import com.assignment.demo.repository.NumberRepository;
import com.assignment.entity.NumberEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NumberIntegrationTest {

	@Autowired
    private TestRestTemplate restTemplate;

	@Autowired
	private NumberRepository repository;

	private NumberEntity numberEntity = null;

	private NumberDTO numberDTO = null;

	private List<NumberEntity> numberListEntity = new ArrayList<NumberEntity>();

	private final long id = 999999;

	@Before
	public void setup() {
		repository.delete(repository.findAll());
		for (long i = 1000000; i <= 1000005; i++) {
			numberEntity = new NumberEntity();
			numberEntity.setNumber(i);
			numberEntity.setInsert_date(new Date());
			repository.save(numberEntity);
		}
	}

	@After
	public void clean() {

		for (long i = 1000000; i <= 1000005; i++) {
			repository.delete(i);
		}

		repository.delete(id);
	}

	@Test
	public void testInsertFindDeleteNumberSuccessfully() throws Exception {

		NumberDTO numberDTO = restTemplate.postForObject("/number/" + id, null, NumberDTO.class);
		assert (numberDTO.getNumber().equals(id));
		numberDTO = restTemplate.getForEntity("/number/" + id, NumberDTO.class).getBody();
		assert (numberDTO.getNumber().equals(id));
		restTemplate.delete("/number/" + id);
		assert (numberDTO.getNumber().equals(id));

	}

	@Test
	public void testFindMaxNumberSuccessfully() throws Exception {

		numberDTO = restTemplate.getForEntity("/number/max", NumberDTO.class).getBody();
		assert (numberDTO.getNumber().equals(1000005L));

	}

	@Test
	public void testFindMinNumberSuccessfully() throws Exception {

		numberDTO = restTemplate.getForEntity("/number/min", NumberDTO.class).getBody();
		assert (numberDTO.getNumber().equals(1000000L));

	}
	
	
	@Test
	public void testListAscendingSuccessfully() throws Exception {

		NumberDTO[] numberDTO = restTemplate.getForEntity("/number?ascending=true", NumberDTO[].class).getBody();
		assert (numberDTO[0].getNumber().equals(1000000L));

	}
	
	@Test
	public void testListDescendingSuccessfully() throws Exception {

		NumberDTO[] numberDTO = restTemplate.getForEntity("/number?ascending=false", NumberDTO[].class).getBody();
		assert (numberDTO[0].getNumber().equals(1000005L));

	}

}
