package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> searchSalesReport(
			Pageable pageable, String minDate, String maxDate, String name) {

		LocalDate maxDt = setMaxDate(maxDate);
		LocalDate minDt = setMinDate(minDate, maxDt);

		Page<Sale> list = repository.searchSalesReport(pageable, minDt, maxDt, name);
		List<SaleReportDTO> result = list.stream().map(SaleReportDTO::new).toList();
		return new PageImpl<>(result); // Trasforma a lista de DTOS em uma Page<> de DTOS
	}

	public Page<SaleSummaryDTO> searchSalesSummary(
			Pageable pageable, String minDate, String maxDate) {

		LocalDate maxDt = setMaxDate(maxDate);
		LocalDate minDt = setMinDate(minDate, maxDt);

		return repository.searchSalesSummary(pageable, minDt, maxDt);
	}

	private LocalDate setMaxDate(String maxDate) {
        return maxDate.isEmpty() ?
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) :
				LocalDate.parse(maxDate);
	}

	private LocalDate setMinDate(String minDate, LocalDate maxDt) {
		return minDate.isEmpty() ?
				maxDt.minusYears(1) :
				LocalDate.parse(minDate);
	}
}


