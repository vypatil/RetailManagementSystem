package service;

import com.zepto.dao.RetailItemDao;
import com.zepto.entity.RetailItems;
import com.zepto.service.RetailItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RetailItemServiceTest {

    @Mock
    private RetailItemDao retailItemDao;

    @InjectMocks
    RetailItemServiceImpl retailItemService;

    private RetailItems testRetailItems;
    ;
    private final Integer iteamId = 1;
    private final String category = "Electronics";
    private final String iteamName = "Smartphone";
    private final Double iteamRate = 99.99d;

    @Before
    public void setup() {
        testRetailItems = new RetailItems();
        testRetailItems.setItemId(iteamId);
        testRetailItems.setCategory(category);
        testRetailItems.setItemName(iteamName);
        testRetailItems.setItemRate(iteamRate);

    }

    @Test
    public void testCreateRetailItem() {

        when(retailItemDao.save(testRetailItems)).thenReturn(testRetailItems);
        RetailItems result = retailItemService.createRetailItem(testRetailItems);

        assertNotNull(testRetailItems);
        assertEquals(testRetailItems, result);
        assertEquals(iteamName, result.getItemName());
        verify(retailItemDao, times(1)).save(testRetailItems);
    }

    @Test
    public void testGetAllRetailItems() {

        List<RetailItems> expectedRetailItems = Arrays.asList(testRetailItems);
        when(retailItemDao.findAll()).thenReturn(expectedRetailItems);

        List<RetailItems> result = retailItemService.getAllRetailItems();
        assertEquals(1, result.size());
        assertEquals(iteamName, result.get(0).getItemName());
        assertSame(expectedRetailItems, result);
        verify(retailItemDao, times(1)).findAll();
    }

    @Test
    public void testGetRetailItemById() {

        when(retailItemDao.findById(iteamId)).thenReturn(Optional.of(testRetailItems));
        Optional<RetailItems> result = retailItemService.getRetailItemById(iteamId);

        assertTrue(result.isPresent());
        assertEquals(testRetailItems, result.get());
        assertEquals(iteamName, result.get().getItemName());

    }

    @Test
    public void testDeleteRetailItemById() {
        retailItemService.deleteEntryByID(iteamId);
        verify(retailItemDao, times(1)).deleteById(iteamId);
    }

    @Test
    public void testGetItemsByCategory() {

        List<RetailItems> expectedRetailItems = Arrays.asList(testRetailItems);
        when(retailItemDao.findAll()).thenReturn(expectedRetailItems);

        Optional<RetailItems> result = retailItemService.getItemUsingCategoryAndId("Electronics", iteamId);
        assertTrue(result.isPresent());
        assertEquals(testRetailItems, result.get());
        assertEquals(iteamName, result.get().getItemName());
    }

    @Test
    public void testGetItemsAboveRange() {

        Double rate = 100.0d;
        List<RetailItems> expectedRetailItems = Arrays.asList(testRetailItems);
        when(retailItemDao.findItemsAboveRate(iteamRate)).thenReturn(expectedRetailItems);
        List<RetailItems> result = retailItemService.getItemsAboveRate(iteamRate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(iteamName, result.get(0).getItemName());
//        assertTrue(result.get(0).getItemRate() > rate);
    }

}
