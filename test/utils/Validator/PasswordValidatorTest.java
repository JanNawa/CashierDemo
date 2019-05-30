package utils.Validator;

import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Jan
 */

@RunWith(Parameterized.class)
public class PasswordValidatorTest {
    
    private PasswordValidator passwordValidator;
    
    private String password;
    private boolean expResult;
    
    public PasswordValidatorTest(String password, boolean expResult) {
        this.password = password;
        this.expResult = expResult;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        passwordValidator = new PasswordValidator();
    }
    
    @After
    public void tearDown() {
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {"Good_Password-1", true}, // Good Test
                {"bad", false}, // Bad Test
                {"pass_Wd1", true} // Boundary Test
        });
    }

    @Test
    public void testIsValidate() {
        assertEquals(expResult, passwordValidator.isValidate(password));
    }

    @Test
    public void testCheckLength() {
        assertEquals(expResult, passwordValidator.checkLength(password));
    }

    @Test
    public void testCheckSpecialCharacter() {
        assertEquals(expResult, passwordValidator.checkSpecialCharacter(password));
    }

    @Test
    public void testCheckUpperAndLowercase() {
        assertEquals(expResult, passwordValidator.checkUpperAndLowercase(password));
    }   
}
