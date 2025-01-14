package com.zepto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepto.dao.RetailItemDao;
import com.zepto.entity.RetailItems;

import java.util.List;
import java.util.Optional;

@Service
public class RetailItemServiceImpl {

	@Autowired
	private RetailItemDao retailItemDao;

	// Method to save a new retail item
	public RetailItems createRetailItem(RetailItems retailItems) {
		return retailItemDao.save(retailItems);
	}

	// Method to get all retail items
	public List<RetailItems> getAllRetailItems() {
		return retailItemDao.findAll();
	}

	// Method to get a retail item by ID
	public Optional<RetailItems> getRetailItemById(Integer id) {
		return retailItemDao.findById(id);
	}

	public void deleteEntryByID(int id) {
		retailItemDao.deleteById(id);
	}

	public Optional<RetailItems> getItemUsingCategoryAndId(String category, Integer itemId) {
		List<RetailItems> allItems = getAllRetailItems();

		return allItems.stream()
				.filter(item -> category.equalsIgnoreCase(item.getCategory()) && itemId.equals(item.getItemId()))
				.findFirst();
	}

	public List<RetailItems> getAllItemsByCategory(String category) {

		return retailItemDao.findByCategoryIgnoreCase(category);

	}

	public List<RetailItems> getItemsAboveRate(Double itemRate) {
		return retailItemDao.findItemsAboveRate(itemRate); // Use itemRate instead of price
	}

	public List<RetailItems> getItemsByCategoryAndPriceRange(String category, Double minRate, Double maxRate) {
		return retailItemDao.getItemsByCategoryAndPriceRange(category,minRate,maxRate);
	}

	

}
