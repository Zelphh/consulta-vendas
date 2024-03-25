package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(
			Pageable pageable,
			@RequestParam(defaultValue = "") String minDate,
			@RequestParam(defaultValue = "") String maxDate,
			@RequestParam(defaultValue = "") String name) {
		Page<SaleReportDTO> list = service.searchSalesReport(pageable, minDate, maxDate, name);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SaleSummaryDTO>> getSummary(
			Pageable pageable,
			@RequestParam(defaultValue = "") String minDate,
			@RequestParam(defaultValue = "") String maxDate) {
		return ResponseEntity.ok(service.searchSalesSummary(pageable, minDate, maxDate));
	}
}
