package com.zepto.dao;

import com.zepto.entity.RetailItems;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailItemDao extends JpaRepository<RetailItems, Integer> {

	List<RetailItems> findByCategoryIgnoreCase(String category);

//	@Query("SELECT * FROM RetailItems r WHERE r.itemRate >= :itemRate")
//	List<RetailItems> findItemsAboveRate(@Param("itemRate") Double itemRate);

	@Query("SELECT r FROM RetailItems r WHERE r.itemRate >= :itemRate")
	List<RetailItems> findItemsAboveRate(@Param("itemRate") Double itemRate);

	
	  @Query("SELECT r FROM RetailItems r WHERE r.category = :category AND r.itemRate BETWEEN :minRate AND :maxRate") 
	  List<RetailItems> getItemsByCategoryAndPriceRange(@Param("category") String category,@Param("minRate") 
	  Double minRate, @Param("maxRate") Double maxRate) ;
	 

}
