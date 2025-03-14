package com.zepto.controller;

import java.util.List;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepto.entity.RetailItems;
import com.zepto.exception.ItemNotFoundException;
import com.zepto.service.RetailItemServiceImpl;

@Slf4j
@RestController
@RequestMapping("/api/retail-items")
public class RetailItemController {

	@Autowired
	private RetailItemServiceImpl retailItemService;

	@GetMapping("/test")
	public String TestMethod() {
		String s = "Wellcome to RetailManagement";
		return s;
	}

	@PostMapping("/save")
	public RetailItems createRetailItem(@RequestBody RetailItems retailItems) {
		return retailItemService.createRetailItem(retailItems);
	}

	@GetMapping("/getAll")
	public List<RetailItems> getAllRetailItems() {
//		log.info("Entering getAllRetailItems ");
		List<RetailItems> retailItems = retailItemService.getAllRetailItems();
		return retailItems;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getItemById(@PathVariable Integer id) {
//		log.info("Entering getItemById Method ");
		Optional<RetailItems> retailItem = retailItemService.getRetailItemById(id);
		if (!retailItem.isPresent()) {
			throw new ItemNotFoundException("Item not Found with this id : " + id);
		}
//		log.info("Exiting getItemById Method ");
		return ResponseEntity.ok(retailItem.get());

	}

	@DeleteMapping("/{id}")
	public String deleteRetailItem(@PathVariable int id) {
		System.out.println("Item Deleted successfully" + id);
		retailItemService.deleteEntryByID(id);
		return "Item Deleted successfully:" + id;
	}

	// fetching items by category with name
	@GetMapping("/category/{category}/item/{itemId}")
	public RetailItems getItemUsingCategoryAndId(@PathVariable String category, @PathVariable Integer itemId) {

		Optional<RetailItems> retailItem = retailItemService.getItemUsingCategoryAndId(category, itemId);

		if (retailItem.isEmpty()) {

			throw new RuntimeException("Item not Found for category: " + category + ", Item Id: " + itemId);

		}
		return retailItem.get();
	}

	// fetching items by category vise
	@GetMapping("/category/{category}")
	public List<RetailItems> getAllItemsByCategory(@PathVariable String category) {

		List<RetailItems> listOfItems = retailItemService.getAllItemsByCategory(category);

		if (listOfItems.isEmpty()) {
			throw new RuntimeException("No items in this category: " + category);
		}
		return listOfItems;
	}

	@GetMapping("/above-rate/{itemRate}")
	public List<RetailItems> getItemsAboveRate(@PathVariable("itemRate") Double itemRate) {
//		log.info("Entering getItemsAboveRate Method ");
		List<RetailItems> listOfItems = retailItemService.getItemsAboveRate(itemRate);
//		log.info("Exiting getItemsAboveRate Method ");
		return listOfItems;
	}

	@GetMapping("/getItem-category-priceRange")
	public ResponseEntity<?> getItemsByCategoryAndPriceRange(@RequestParam String category,
			@RequestParam Double minRate, @RequestParam Double maxRate) {

		List<RetailItems> items = retailItemService.getItemsByCategoryAndPriceRange(category, minRate, maxRate);
		if (!items.isEmpty()) {
			return ResponseEntity.ok(items);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No item found for these category: " + category

					+ " in the price range ");
		}
	}

}
