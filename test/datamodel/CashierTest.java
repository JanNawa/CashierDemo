package datamodel;

import java.time.LocalDateTime;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Jan
 */
public class CashierTest {
    
    private Product product;
    
    private Cashier cashier;
    
    public CashierTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        product = new Product(101, 111, "Milk", "Low Fat 2.0 litres", 4.99);
        
        cashier = new Cashier();
        cashier.addProduct(product);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddProduct() {
        assertEquals("Adding 1 product", 1, cashier.getOrderSize());
    }

    @Test
    public void testAdjustQuantity() {
        int index = 0;
        cashier.adjustQuantity(index, 1);
        assertEquals("Adding 1 quantity", 2, (long) cashier.getQuantity().get(index));
    }

    @Test
    public void testDeleteProduct() {
        cashier.deleteProduct(product);
        assertEquals("Delete product", 0, cashier.getOrderSize());
    }

    @Test
    public void testCalcTotal() {
        cashier.calcTotal();
        assertEquals(5.6387, cashier.getTotal(), 0.0001);
    }

    @Test
    @Ignore
    public void testSetOrderId() {
        System.out.println("setOrderId");
        int orderId = 0;
        Cashier instance = new Cashier();
        instance.setOrderId(orderId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetOrderSize() {
        assertEquals("Order Size 1", 1, cashier.getOrderSize());
    }

    @Test
    public void testGetOrder() {
        List<Product> expResult = new ArrayList<>();
        expResult.add(product);
        List<Product> result = cashier.getOrder();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetQuantity() {
        List<Integer> expResult = new ArrayList<>();
        expResult.add(1);
        List<Integer> result = cashier.getQuantity();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetSubtotal() {
        double expResult = 4.99;
        double result = cashier.getSubtotal();
        assertEquals(expResult, result, 0.0001);
    }

    @Test
    public void testGetTax() {
        double expResult = 0.6487;
        double result = cashier.getTax();
        assertEquals(expResult, result, 0.0001);
    }

    @Test
    public void testGetTotal() {
        double expResult = 5.6387;
        double result = cashier.getTotal();
        assertEquals(expResult, result, 0.0001);
    }

    @Test
    public void testToString() {
        String expResult = "\norderId=1000"
                + "\ndateTime=" + LocalDateTime.now()
                + "\norder=[id = 101"
                + "\tname = Milk"
                + "\tdescription = Low Fat 2.0 litres"
                + "\tprice = 4.99]"
                + "\nquantity=[1]"
                + "\nsubtotal=" + 4.99
                + "\ntax=" + 0.6487
                + "\ntotal=" + 5.6387;
        String result = cashier.toString();
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
    }   
}
