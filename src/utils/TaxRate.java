package utils;

/**
 *
 * @author Jan
 */
public class TaxRate {
    public static final double TAX_RATE_CANADA = 0.05;
    
    public static final double TAX_RATE_PROVINCIAL_BRITISH_COLUMBIA = 0.07;
    public static final double TAX_RATE_PROVINCIAL_NOVA_SCOTIA = 0.10;
    public static final double TAX_RATE_PROVINCIAL_ONTARIO = 0.08;
    
    public static double TAX_RATE_BRITISH_COLUMBIA = TAX_RATE_CANADA + TAX_RATE_PROVINCIAL_BRITISH_COLUMBIA;
    public static double TAX_RATE_NOVA_SCOTIA = TAX_RATE_CANADA + TAX_RATE_PROVINCIAL_NOVA_SCOTIA;
    public static double TAX_RATE_ONTARIO = TAX_RATE_CANADA + TAX_RATE_PROVINCIAL_ONTARIO;
}
