package com.fci.UnitTest.Transaction;

import org.junit.Test;

import com.fci.Transaction.TransactionService;

import junit.framework.TestCase;
import sun.security.krb5.internal.NetClient;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;


public class TestJunit extends TestCase {
	
   String message = "Hello World";	
   

  /*
   * test true shift amount
   */
  @Test
  public void testArrayShift(){
	  	  
	  BigDecimal[] elements=new BigDecimal[] {new BigDecimal(1),new BigDecimal(2),new BigDecimal(3),new BigDecimal(4),new BigDecimal(5),new BigDecimal(6),new BigDecimal(7),new BigDecimal(8),new BigDecimal(9),new BigDecimal(10),new BigDecimal(11)};
	  int shiftamount=3;
	  BigDecimal[] target=new BigDecimal[] {new BigDecimal(4),new BigDecimal(5),new BigDecimal(6),new BigDecimal(7),new BigDecimal(8),new BigDecimal(9),new BigDecimal(10),new BigDecimal(11),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
	  
	  TransactionService.shiftArrayToLowerIndex(elements, shiftamount, new BigDecimal(0));
	  
	  assertArrayEquals(target, elements);
  }
  
  /*
   * test shift amount geater than array length
   */
  @Test
  public void testArrayShift2(){
	  	  
	  BigDecimal[] elements=new BigDecimal[] {new BigDecimal(1),new BigDecimal(2),new BigDecimal(3),new BigDecimal(4),new BigDecimal(5),new BigDecimal(6),new BigDecimal(7),new BigDecimal(8),new BigDecimal(9),new BigDecimal(10),new BigDecimal(11)};
	  int shiftamount=20;
	  BigDecimal[] target=new BigDecimal[] {new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
	  
	  TransactionService.shiftArrayToLowerIndex(elements, shiftamount, new BigDecimal(0));
	  
	  assertArrayEquals(target, elements);
  }
  
  /*
   * test shift amount < 0
   */
  public void testArrayShift3(){
  	  
	  BigDecimal[] elements=new BigDecimal[] {new BigDecimal(1),new BigDecimal(2),new BigDecimal(3),new BigDecimal(4),new BigDecimal(5),new BigDecimal(6),new BigDecimal(7),new BigDecimal(8),new BigDecimal(9),new BigDecimal(10),new BigDecimal(11)};
	  int shiftamount=-10;
	  BigDecimal[] target=new BigDecimal[] {new BigDecimal(1),new BigDecimal(2),new BigDecimal(3),new BigDecimal(4),new BigDecimal(5),new BigDecimal(6),new BigDecimal(7),new BigDecimal(8),new BigDecimal(9),new BigDecimal(10),new BigDecimal(11)};
	  
	  TransactionService.shiftArrayToLowerIndex(elements, shiftamount, new BigDecimal(0));
	  
	  assertArrayEquals(target, elements);
  }
  /*
   * test statistics calculations
   */
   
  @Test 
  public void testArayValuesStatistics(){
	  
	  BigDecimal[] elements=new BigDecimal[] {new BigDecimal(3),new BigDecimal(8),new BigDecimal(-1),new BigDecimal(0),new BigDecimal(10),new BigDecimal(0),new BigDecimal(1),new BigDecimal(15),new BigDecimal(0),new BigDecimal(5),new BigDecimal(8)};
	  
	  BigDecimal tagetAvg=(new BigDecimal(49)).divide(new BigDecimal(8),2,BigDecimal.ROUND_HALF_UP);
	  
	  BigDecimal[] target= new BigDecimal[] {new BigDecimal(49),new BigDecimal(8),tagetAvg,new BigDecimal(-1),new BigDecimal(15)};
	  
	  
	  
	  BigDecimal[] returnValues=TransactionService.getElementStatistics(elements, 11);
	  
	  assertArrayEquals(returnValues, target);
	  
	  
	  
	   tagetAvg=(new BigDecimal(10)).divide(new BigDecimal(3),2,BigDecimal.ROUND_HALF_UP);
	  
	 target= new BigDecimal[] {new BigDecimal(10),new BigDecimal(3),tagetAvg,new BigDecimal(-1),new BigDecimal(8)};
	  
	 returnValues=TransactionService.getElementStatistics(elements, 4);
	  
	  assertArrayEquals(returnValues, target);
  }
  
  @Test
  public void testPushTransactionWebService(){
	 
			 
	  Date currentDate=new Date();
		
		 // time before 20 seconds
		 Date transactionDate=new Date(currentDate.getTime()-2*1000);
		 
		 String transactionValue="30";
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 String transactionDate_str=formatter.format(transactionDate);
		 
		 int statusCode= WebServiceClient.testTransactionWS(transactionDate_str, transactionValue);
		 
		 assertEquals(Response.Status.OK.getStatusCode(), statusCode);
		 
		 currentDate=new Date();
			
		 // time before 20 seconds
		 transactionDate=new Date(currentDate.getTime()-5*1000);
		 
		 transactionValue="10";
		 
		 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 transactionDate_str=formatter.format(transactionDate);
		 
		 statusCode= WebServiceClient.testTransactionWS(transactionDate_str, transactionValue);
		 
		 assertEquals(Response.Status.OK.getStatusCode(), statusCode);
		 
		 currentDate=new Date();
			
		 // time before 20 seconds
		 transactionDate=new Date(currentDate.getTime()+65*60*1000);
		 
		 transactionValue="50";
		 
		 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 transactionDate_str=formatter.format(transactionDate);
		 
		 statusCode= WebServiceClient.testTransactionWS(transactionDate_str, transactionValue);
		 
		 assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), statusCode);
		 
		 
		 transactionDate=new Date(currentDate.getTime()-120*1000);
		 
		 transactionValue="50";
		 
		 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 transactionDate_str=formatter.format(transactionDate);
		 
		 statusCode= WebServiceClient.testTransactionWS(transactionDate_str, transactionValue);
		 
		 assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), statusCode);
		 
		 
		 transactionDate=new Date(currentDate.getTime()-15*1000);
		 
		 transactionValue="20";
		 
		 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 transactionDate_str=formatter.format(transactionDate);
		 
		 statusCode= WebServiceClient.testTransactionWS(transactionDate_str, transactionValue);
		 
		 assertEquals(Response.Status.OK.getStatusCode(), statusCode);
		 
		 

	 
	 
	 
  }
  
  
 
  
     
}