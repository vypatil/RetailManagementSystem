package com.zepto.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
public class RetailItemControllerTest {

	@Mock
	private RetailItemServiceImpl retailItemService;

	@Mock
	RetailItems retailItems;

	@InjectMocks
	private RetailItemController retailItemController;

	@Test
	public void createRetailItemTest() {
		when(retailItemService.createRetailItem(any(RetailItems.class))).thenReturn(retailItems);

        // Act
        RetailItems response = retailItemController.createRetailItem(retailItems);

        // Assert
        assertNotNull(response);
        assertEquals(retailItems.getItemId(), response.getItemId());
        assertEquals(retailItems.getItemName(), response.getItemName());
        assertEquals(retailItems.getItemRate(), response.getItemRate());
        assertEquals(retailItems.getCategory(), response.getCategory());

        // Verify the service method was called once
        verify(retailItemService, times(1)).createRetailItem(any(RetailItems.class));
				
}
}