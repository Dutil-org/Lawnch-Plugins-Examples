package org.dutil.lawnch.model.examples;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Transient;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dutil.lawnch.model.job.PluginJob;
import org.dutil.lawnch.model.result.JsonResult;
import org.dutil.lawnch.model.result.Result;

import ro.fortsoft.pf4j.Extension;

@Extension
@Entity
@Inheritance
public class QuandlDataRetrievalJob extends PluginJob<JsonResult> {

	@Transient
	HttpClient m_httpClient;
	
	public QuandlDataRetrievalJob()
	{
		descriptor().commonName("Quandl Data Retrieval Job");
		m_httpClient = HttpClientBuilder.create().build();
		result(new JsonResult());
	}
	
	@Override
	public void execute() {		
		Result configuration = configuration();
		String stockId = configuration.value("stockId");
		String authToken = configuration.value("OauthToken");
		HttpGet request = new HttpGet("http://www.quandl.com/api/v1/datasets/" + stockId + ".json?sort_order=asc&auth_token=" + authToken);
		
		try 
		{
			HttpResponse response = m_httpClient.execute(request);
			result().insert(EntityUtils.toString(response.getEntity()));
			System.out.println("QuandlDataRetrievalJob: Added information to result!");
		} 
		catch (ParseException | IOException e) 
		{
			e.printStackTrace();
		}
	}
}
