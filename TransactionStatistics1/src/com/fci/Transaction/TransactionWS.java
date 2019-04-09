package com.fci.Transaction;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;



import java.util.Date;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
  

@Path("/transaction")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Singleton
public class TransactionWS {  
 // test commit 222
	private static BigDecimal[] allValidTrasactions=new BigDecimal[3661];
	
	private static Date lastSynchronizeTime=new Date();
	
	static{
		
		for(int i=0;i<allValidTrasactions.length;i++)
			allValidTrasactions[i]=new BigDecimal(0);
		
	}
	
	public TransactionWS(){
		
	}
	
 	
    @GET
    @Path("/pushTransaction")
    public Response pushTransaction(@QueryParam("trdate") String trDate_str, @QueryParam("trvalue") String trValue_str) {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	 Date trDate=null;
    	 BigDecimal trValue=null; 
    	 int shiftAmount=0;
    	 long diffSeconds=0;
    	 int index=-1;
    	 BigDecimal sum=new BigDecimal(0);
		try {
			
			Date currentDate=new Date();
			shiftAmount=(int)((currentDate.getTime() - lastSynchronizeTime.getTime())/1000);
			
						
			TransactionService.shiftArray(allValidTrasactions, shiftAmount, new BigDecimal(0));
			
			Response.status(Response.Status.NOT_FOUND).build();
			
			trDate = formatter.parse(trDate_str);
			
			diffSeconds=(trDate.getTime() - currentDate.getTime() )/1000;
			
			
			
			index=(int)diffSeconds+60;
			
			trValue=new BigDecimal(trValue_str);
			
			if(index < allValidTrasactions.length && index >-1)
				allValidTrasactions[index]=trValue;
						
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return Response.ok().build();
    	
    }
    
    @GET
    @Path("/transactionStatistics")
    public String getBook() {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	//String trdate_str="2019-4-6 05:40:00";
    	 Date trdate=null;
    	 BigDecimal trvalue=null; 
    	 int shifAmount=-2;
    	 long diffSeconds=-50;
    	 int index=-33;
    	 BigDecimal sum=new BigDecimal(0);
    	 
    	 String dates="";
		try {
			
			Date currentDate=new Date();
			shifAmount=(int)((currentDate.getTime() - lastSynchronizeTime.getTime())/1000);
			
			for(int i=shifAmount;i < allValidTrasactions.length;i++)
				allValidTrasactions[i-shifAmount]=allValidTrasactions[i];
			
			
			int endshift=allValidTrasactions.length-shifAmount-1;
			for(int i=allValidTrasactions.length-1;i>endshift;i--)
				allValidTrasactions[i]=new BigDecimal(0);
			
			
			lastSynchronizeTime.setTime(currentDate.getTime());
			
			 sum=new BigDecimal(0);
			
			 
			for(int i=0;i<60;i++){
				if(allValidTrasactions[i].intValue()>0){
					dates+="("+i+","+allValidTrasactions[i].intValue()+") , ";
				}
				sum=sum.add(allValidTrasactions[i]);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return dates+"   "+sum;
    	
    }   
}