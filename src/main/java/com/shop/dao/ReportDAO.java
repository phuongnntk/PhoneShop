package com.shop.dao;

import com.shop.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDAO extends JpaRepository<Report, Integer>{
	@Procedure(procedureName = "DOANH_THU_NAM" )	
	Report getReport(@Param("year") int year);
}
