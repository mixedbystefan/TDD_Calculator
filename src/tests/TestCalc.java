package tests;
import calculator.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import calculator.Calculator;

public class TestCalc {
	
	//Arrange
	Calculator calc = new Calculator();
	
	
	
	// Ett sista test med massor av "svårigheter"
	@Test
	public void allOperatorsAdvanced_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "-1+2/(√16-2)*√4--2+10^2-8%3"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("101.0", actual);	
	}
	
	
	
	@Test
	public void logInContext_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "10log100"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("20.0", actual);	
	}
	

	@Test
	public void rootBeforeBracket_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "√(2*2)"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("2.0", actual);	
	}
	
	@Test
	public void rootInContext_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "10√100"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("100.0", actual);	
	}
	
	@Test
	public void onlyBracketAndNoOperator_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "4(2)"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("8.0", actual);	
	}
	
	@Test
	public void onlyBracketsWithOneNegativeNumber_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "4*(-2)"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("-8.0", actual);	
	}
	
	@Test
	public void ZeroMultiplyWithNegative_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "0*-3";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("0.0", actual);	
	}

	
	
	@Test
	public void plusNegativeBracketValue_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "4+(1-2)";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("3.0", actual);	
	}
	
	@Test
	public void MinusInFrontOfRoot_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "2-√4";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("0.0", actual);	
	}
	
	@Test
	public void minusFirstOrLast_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "-2+2/2*2*1+1*2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("2.0", actual);	
	}
	
	
	
	@Test
	public void minusInFrontOfBracket_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "3-(1+1)";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("1.0", actual);	
	}
	
	
	
	@Test
	public void minusFirst_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "-4+5";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("1.0", actual);	
	}
	@Test
	public void plusFirstOrLast_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "+2+2/2*2+";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	
	
	
	@Test
	public void rootFirst_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "√4+2/2*2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	
	public void bracket_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "9(3*1)+3-(4-1)";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("27.0", actual);	
	}
	
	@Test
	public void doubleMinus_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "2--2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	@Test
	public void SimplePriority_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "7*6/2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("21.0", actual);	
	}
	
	@Test
	public void whiteSpace_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "1 *2 / 2 +2 * 3";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("7.0", actual);	
	}
	
	
	@Test
	public void multipleOperators_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5*4+7-3*0+4-10";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("21.0", actual);	
	}
	
	@Test
	public void additionWithMultipleNumbers_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "1+1+1";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("3.0", actual);	
	}
	
	@Test
	public void additionAndSubtraction_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5+1-2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	
	@Test
	public void multiplyAndDivide_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5*5/5";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("5.0", actual);	
	}
	
	@Test
	public void multiplyOperatorsInOrder_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5*5/2-2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("10.5", actual);	
	}
	
	@Test
	public void subtractionWithMultipleNumbers_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "9-3-1";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("5.0", actual);	
	}
	
	@Test
	public void testMultiplyByZero() {
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.multiply(2, 0);
				
		//Assert
		assertEquals(0, actual, 0.111);
	}
	
	
	
	@Test
	public void addition_test() 
	{
		
		//Arrange
		Calculator calc = new Calculator();
		// Act
		String input = "1+2";
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("3.0", actual);
		
		
		
	}
	
	@Test
	public void simpleDivide_test() 
	{
		
		//Arrange
		Calculator calc = new Calculator();
		// Act
		String input = "4/2";
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("2.0", actual);	
		
	}
	
	
	
	// De mest grundläggande testen av uträknings-metoderna 
	
	@Test
	public void add_test() 
	{
		
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.add(1, 2);
		
		//Assert
		assertEquals(3d, actual, 0.111);
		
		
		
	}
	
	@Test
	public void subtract_test() 
	{
	    
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.subtract(2, 1);
		
		//Assert
		assertEquals(1d, actual, 0.111);
		
		
	}
	
	@Test
	public void multiply_test() 
	{
	  
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.multiply(3, 3);
		
		//Assert
		assertEquals(9d, actual, 0.111);
		
		
	}
		
	
	@Test
	public void divide_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.divide(4, 2);
		
		//Assert
		assertEquals(2d, actual, 0.111);
		
		
	}
	
	@Test
	public void modulus_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.divide(60, 19);
		
		//Assert
		assertEquals(3, actual, 0.16);
		
		
	}
	
	@Test
	public void root_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.root(9);
		
		//Assert
		assertEquals(3d, actual, 0.111);
		
		
	}
	
	@Test
	public void exponent_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.exponent(10, 2);
		
		//Assert
		assertEquals(100d, actual, 0.111);
		
		
	}
	
	@Test
	public void logarithm_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.logarithm(100);
		
		//Assert
		assertEquals(2d, actual, 0.111);
		
		
	}
	
	
	
	
	
	

}