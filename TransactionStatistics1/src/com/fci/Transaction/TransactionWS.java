package com.fci.Transaction;

import java.io.IOException;
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

import org.apache.log4j.Logger;

import sun.org.mozilla.javascript.internal.GeneratedClassLoader;

@Path("/transaction")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Singleton
public class TransactionWS {
	// test commit 222
	private static BigDecimal[] allValidTrasactions = new BigDecimal[3661];

	private static Date lastSynchronizeTime = new Date();
	
	private static Logger log=Logger.getLogger(TransactionWS.class);

	static {

		for (int i = 0; i < allValidTrasactions.length; i++)
			allValidTrasactions[i] = new BigDecimal(0);

	}

	public TransactionWS() {

	}

	@POST
	@Path("/pushTransaction")
	public Response pushTransaction(@QueryParam("trdate") String trDate_str,
			@QueryParam("trvalue") String trValue_str) {

		synchronized (this.allValidTrasactions) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date trDate = null;
			BigDecimal trValue = null;
			int shiftAmount = 0;
			long diffSeconds = 0;
			int index = -1;
			BigDecimal sum = new BigDecimal(0);

			try {

				Date currentDate = new Date();
				shiftAmount = (int) ((currentDate.getTime() - lastSynchronizeTime.getTime()) / 1000);

				TransactionService.shiftArrayToLowerIndex(allValidTrasactions, shiftAmount, new BigDecimal(0));
				
				lastSynchronizeTime.setTime(currentDate.getTime());

				Response.status(Response.Status.NOT_FOUND).build();

				trDate = formatter.parse(trDate_str);

				diffSeconds = (trDate.getTime() - currentDate.getTime()) / 1000;

				index = (int) diffSeconds + 60;

				trValue = new BigDecimal(trValue_str);

				if (trValue.intValue() > 1000000) {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Transaction value must be less then 1000000").build();
				}

				if (index < allValidTrasactions.length && index > -1) {
					allValidTrasactions[index] = trValue;

					return Response.status(Response.Status.OK).entity("Transaction is pushed succesfully").build();
				} else {
					
					

					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Transaction Date must be not before 60 seconds and not after 1 hour").build();
				}

			} catch (ParseException e) {
				
				log.error(e.getMessage()+  "\nTransaction Date must be formated as YYYY-MM-dd HH:mm:ss");
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Transaction Date must be formated as YYYY-MM-dd HH:mm:ss").build();
			} catch (NumberFormatException e) {
				
				log.error(e.getMessage()+ "\nTransaction value is in a bad formate");
				
				return Response.status(Response.Status.BAD_REQUEST).entity("Transaction value is in a bad formate")
						.build();
			} catch (Exception e) {
				log.error(e.getMessage()+ "\nGeneral error");
				
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("General error").build();
			}

		}

	}
	/*
	 * 
	 */

	@GET
	@Path("/transactionStatistics")
	public Response transactionStatistics() {
		synchronized (this.allValidTrasactions) {

		Date trdate = null;
		BigDecimal trvalue = null;
		int shiftAmount = 0;
		long diffSeconds = 0;
		int index = -1;
		BigDecimal sum = new BigDecimal(0);

		String dates = "";
		try {

			Date currentDate = new Date();

			shiftAmount = (int) ((currentDate.getTime() - lastSynchronizeTime.getTime()) / 1000);

			TransactionService.shiftArrayToLowerIndex(this.allValidTrasactions, shiftAmount, new BigDecimal(0));

			lastSynchronizeTime.setTime(currentDate.getTime());

			BigDecimal[] statistics = TransactionService.getElementStatistics(this.allValidTrasactions, 60);

			return Response.status(Response.Status.OK).entity("{sum:"+statistics[0].intValue()+",count:"+statistics[1].intValue()+",avg:"+statistics[2].intValue()+",min:"+statistics[3].intValue()+",max:"+statistics[4].intValue()+"}").build();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage()+"\nGeneral error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("General error").build();
		}

		}

	}
}