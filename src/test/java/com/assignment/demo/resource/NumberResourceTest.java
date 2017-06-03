package com.assignment.demo.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.demo.dto.NumberDTO;
import com.assignment.demo.repository.NumberRepository;
import com.assignment.demo.service.NumberService;
import com.assignment.entity.NumberEntity;

@RunWith(SpringRunner.class)
@WebMvcTest(NumberResource.class)
public class NumberResourceTest {

	private NumberResource numberResource;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NumberService numberService;

	@MockBean
	private DiscoveryClient discoveryClient;

	@MockBean
	private NumberRepository repository;

	private NumberEntity numberEntity = null;

	private NumberDTO numberDTO = null;

	private List<NumberEntity> numberListEntity = new ArrayList<NumberEntity>();

	@Before
	public void setup() {

		numberResource = new NumberResource(numberService);
		numberEntity = new NumberEntity();
		numberEntity.setNumber(1L);
		numberEntity.setInsert_date(new Date());
		numberListEntity.add(numberEntity);
		numberDTO = new NumberDTO();
		numberDTO.setNumber(1L);
	}

	@Test
	public void testInsertNumberSuccessfully() throws Exception {

		when(numberService.insertNumber(1L)).thenReturn(numberEntity);

		mockMvc.perform(post("/number/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("number", is(1)));
	}

	@Test
	public void testDeleteNumberSuccessfully() throws Exception {

		when(numberService.deleteNumber(1L)).thenReturn(numberEntity);

		mockMvc.perform(delete("/number/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("number", is(1)));
	}

	@Test
	public void testGetNumberSuccessfully() throws Exception {

		when(numberService.findNumber(1L)).thenReturn(numberEntity);

		mockMvc.perform(get("/number/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("number", is(1)));
	}

	@Test
	public void testGetNumberListAscendingWithNoParametersSuccessfully() throws Exception {

		when(numberService.findAll(true)).thenReturn(numberListEntity);

		mockMvc.perform(get("/number").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("[0].number", is(1)));
	}

	@Test
	public void testGetNumberListDescendingSuccessfully() throws Exception {

		when(numberService.findAll(false)).thenReturn(numberListEntity);

		mockMvc.perform(get("/number?ascending=false").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("[0].number", is(1)));
	}

	@Test
	public void testGetNumberListAscendingSuccessfully() throws Exception {

		when(numberService.findAll(true)).thenReturn(numberListEntity);

		mockMvc.perform(get("/number?ascending=true").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("[0].number", is(1)));
	}

	@Test
	public void testGetMinimumNumberSuccessfully() throws Exception {

		when(numberService.findMaxMin(false)).thenReturn(numberEntity);

		mockMvc.perform(get("/number/min").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("number", is(1)));
	}

	@Test
	public void testGetMaximumNumberSuccessfully() throws Exception {

		when(numberService.findMaxMin(true)).thenReturn(numberEntity);

		mockMvc.perform(get("/number/max").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("number", is(1)));
	}

}
