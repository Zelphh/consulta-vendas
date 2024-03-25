package com.devsuperior.dsmeta.repositories;


import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

   @Query(value = "SELECT obj " +
           "FROM Sale obj JOIN FETCH obj.seller " +
           "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND " +
           "obj.date >= :minDate AND " +
           "obj.date <= :maxDate",
           countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
   Page<Sale> searchSalesReport(
           Pageable pageable, LocalDate minDate, LocalDate maxDate, String name);

   @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) " +
          "FROM Sale obj " +
          "WHERE obj.date >= :minDate AND " +
          "obj.date <= :maxDate " +
          "GROUP BY obj.seller.name")
   Page<SaleSummaryDTO> searchSalesSummary(
           Pageable pageable, LocalDate minDate, LocalDate maxDate);
}

/* @Query(value = "SELECT obj " +
           "FROM Sale obj JOIN FETCH obj.seller " +
           "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND " +
           "obj.date >= :minDate AND " +
           "obj.date <= :maxDate",
        countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
   Page<Sale> searchSalesReport(
           Pageable pageable, LocalDate minDate, LocalDate maxDate, String name);

 Tentei fazer voltando um DTO mas estava dando errro, não entendi o por que */
