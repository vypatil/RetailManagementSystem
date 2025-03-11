package com.zepto.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.zepto.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.zepto.entity.RetailItems;
import com.zepto.service.RetailItemServiceImpl;

import java.util.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RetailItemControllerTest {

    @Mock
    private RetailItemServiceImpl retailItemService;

    @InjectMocks
    private RetailItemController retailItemController;

    @Test
    public void testTestMethod() {
        String result = retailItemController.TestMethod();
        assertEquals("Wellcome to RetailManagement", result);
    }

    @Test
    public void testCreateRetailItem() {
        RetailItems retailItems = new RetailItems(1, "Grocery", "Cofee", 20.0);
        when(retailItemService.createRetailItem(any())).thenReturn(retailItems);

        RetailItems result = retailItemController.createRetailItem(retailItems);

        assertNotNull(result);
        assertEquals(1, result.getItemId());
        assertEquals("Grocery", result.getCategory());
        assertEquals("Cofee", result.getItemName());
        assertEquals(20.0, result.getItemRate());
    }

    @Test
    public void testGetAllRetailsItems() {
        List<RetailItems> mockItems = Arrays.asList(new RetailItems(1, "Grocery", "Cofee", 20.0),
													new RetailItems(2, "Grocery", "Masala", 27.0));

        when(retailItemService.getAllRetailItems()).thenReturn(mockItems);
        List<RetailItems> result = retailItemController.getAllRetailItems();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cofee", result.get(0).getItemName());
        assertEquals("Masala", result.get(1).getItemName());

    }

    @Test
    public void testGetItemById() {
        ResponseEntity<RetailItems> response = new ResponseEntity<>(new RetailItems(1, "Grocery", "Cofee", 20.0), HttpStatus.OK);

        RetailItems actualObj = response.getBody();
        assertNotNull(actualObj);

        RetailItems expectedObj = new RetailItems(1, "Grocery", "Cofee", 20.0);
        assertEquals(expectedObj, actualObj);
    }
	@Test
	public void testGetItemById_NotFound() {
        when(retailItemService.getRetailItemById(1)).thenReturn(Optional.empty());
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            retailItemController.getItemById(1);
        });
        assertEquals("Item not Found with this id : 1", exception.getMessage());
    }
    @Test
    public void testDeleteRetailItem() {
        doNothing().when(retailItemService).deleteEntryByID(1);

        String result = retailItemController.deleteRetailItem(1);
        assertNotNull(result);
        assertEquals("Item Deleted successfully:1", result);

    }

    @Test
    public void testGetItemUsingCategoryAndId() {
//        log.info("Entering getItemUsingCategoryAndId method");

        RetailItems mockItem = new RetailItems(1, "Grocery", "Cofee", 20.0);
        when(retailItemService.getItemUsingCategoryAndId("Grocery", 1)).thenReturn(Optional.of(mockItem));
        RetailItems result = retailItemController.getItemUsingCategoryAndId("Grocery", 1);

        assertNotNull(result);
        assertEquals("Cofee", result.getItemName());
        assertEquals(20.0, result.getItemRate());
        assertEquals("Grocery", result.getCategory());
//        log.info("Exiting getItemUsingCategoryAndId method");
    }

    @Test
    public void testGetAllItemsByCategory() {

        List<RetailItems> mockItems = Arrays.asList(new RetailItems(1, "Grocery", "Cofee", 20.0),
													new RetailItems(2, "Grocery", "Masala", 27.0));

        when(retailItemService.getAllRetailItems()).thenReturn(mockItems);
        List<RetailItems> result = retailItemController.getAllRetailItems();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cofee", result.get(0).getItemName());
        assertEquals("Masala", result.get(1).getItemName());
    }

    @Test
    public void testGetAllItemsByCategoryNotFound() {
        when(retailItemService.getAllItemsByCategory("Grocery")).thenReturn(Collections.emptyList());

        RuntimeException exceptionObj = assertThrows(RuntimeException.class, () -> {
            retailItemController.getAllItemsByCategory("Grocery");
        });
        assertEquals("No items in this category: Grocery", exceptionObj.getMessage());
        verify(retailItemService,times(1)).getAllItemsByCategory("Grocery");
    }

    @Test
    public void testGetItemsAboveRate() {

        List<RetailItems> mockItems = Arrays.asList(new RetailItems(1, "Grocery", "Cofee", 20.0),
													new RetailItems(2, "Grocery", "Masala", 27.0));
        when(retailItemService.getItemsAboveRate(10.0)).thenReturn(mockItems);
        List<RetailItems> result = retailItemController.getItemsAboveRate(10.0);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cofee", result.get(0).getItemName());
        assertEquals("Masala", result.get(1).getItemName());

    }

	@Test
    public void testGetItemsByCategoryAndPriceRange() {
        List<RetailItems> mockItems = Arrays.asList(new RetailItems(1, "Grocery","cofee", 20.0),
													new RetailItems(2, "Grocery","Masala", 27.0));

		when(retailItemService.getItemsByCategoryAndPriceRange("Grocery",10.0,2000.0)).thenReturn(Arrays.asList());
		ResponseEntity notFoundResult = retailItemController.getItemsByCategoryAndPriceRange("Grocery",10.0,2000.0);
		assertNotNull(notFoundResult);
		assertEquals(404,notFoundResult.getStatusCodeValue());
		assertEquals("No item found for these category: Grocery in the price range", notFoundResult.getBody().toString().trim());

    }
}
