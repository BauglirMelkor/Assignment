package com.assignment.demo.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.demo.dto.NumberDTO;
import com.assignment.demo.service.NumberService;

@RestController
@RequestMapping("/number")
public class NumberResource {

	private final NumberService numberService;

	public NumberResource(NumberService numberService) {
		this.numberService = numberService;
	}

	@PostMapping("/{number}")
	public NumberDTO insertNumber(@PathVariable Long number) {
		return new NumberDTO(numberService.insertNumber(number));
	}

	@DeleteMapping("/{number}")
	public NumberDTO deleteNumber(@PathVariable Long number) {
		return new NumberDTO(numberService.deleteNumber(number));
	}

	@GetMapping("/{number}")
	public NumberDTO findNumber(@PathVariable Long number) {
		return new NumberDTO(numberService.findNumber(number));
	}

	@GetMapping
	public List<NumberDTO> getNumberList(@RequestParam(value = "ascending", required = false) Boolean ascending) {
		List<NumberDTO> numberDTOList = new ArrayList<>();
		if (ascending == null) {
			ascending = true;
		}
		numberService.findAll(ascending).stream().forEach(p -> numberDTOList.add(new NumberDTO(p)));
		return numberDTOList;
	}

	@GetMapping("/max")
	public NumberDTO getMaxNumber() {

		return new NumberDTO(numberService.findMaxMin(true));
	}

	@GetMapping("/min")
	public NumberDTO getMinNumber() {

		return new NumberDTO(numberService.findMaxMin(false));
	}

}
