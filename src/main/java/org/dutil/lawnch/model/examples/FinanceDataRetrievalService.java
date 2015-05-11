package org.dutil.lawnch.model.examples;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import org.dutil.lawnch.model.result.JsonResult;
import org.dutil.lawnch.model.service.PluginService;
import org.dutil.lawnch.model.task.ConfigurationFailedException;

import ro.fortsoft.pf4j.Extension;

@Extension
@Entity
@Inheritance
public class FinanceDataRetrievalService extends PluginService<JsonResult> {

	public FinanceDataRetrievalService()
	{
		descriptor().commonName("Finance Data Retrieval Service");
	}
	
	@Override
	public void prepareJobs() {
		System.out.println("FinanceDataRetrievalService> Preparing Jobs");
		QuandlDataRetrievalJob job = new QuandlDataRetrievalJob();
		result(job.result());
		try 
		{
			job.configuration(configuration());
		} 
		catch (ConfigurationFailedException e) 
		{
			e.printStackTrace();
		}
		addJob(job);
	}
}
