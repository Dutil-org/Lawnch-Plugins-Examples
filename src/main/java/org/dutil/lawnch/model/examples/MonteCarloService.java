package org.dutil.lawnch.model.examples;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import org.bson.Document;
import org.dutil.lawnch.model.result.DictionaryResult;
import org.dutil.lawnch.model.result.JsonResult;
import org.dutil.lawnch.model.service.PluginService;
import org.dutil.lawnch.model.task.ConfigurationFailedException;

import ro.fortsoft.pf4j.Extension;

@Extension
@Entity
@Inheritance
public class MonteCarloService extends PluginService<JsonResult> {
	
	public MonteCarloService()
	{
		descriptor().commonName("Monto Carlo Service");
	}
	
	@Override
	protected void setDependencies() {
		addDependency("41f192db4cd55a71aa8f1a1e227ab75409dbb5f0", "857dc0bf483fdbc989cd466a5828510e884ed151");
	}

	@Override
	public void prepareJobs() {		
		System.out.println("MonteCarloService> Preparing Jobs");
		JsonResult deprep = (JsonResult) dependencies().get(0).task().result();
		Document stockDataDocument = deprep.findOne("{}");
		
		stockDataDocument = (Document) stockDataDocument.get("storage");
		ArrayList<ArrayList> stockData = (ArrayList<ArrayList>) stockDataDocument.get("data");
		
		
		double formerClose = -1;
		double[] logReturn = new double[stockData.size()];
		
		int i = 0;
		for(ArrayList subList: stockData)
		{
			double close =  (double) subList.get(4);
			
			if(i != 0)
				logReturn[i - 1] = Math.log(formerClose/close);
			
			formerClose = close;
			i++;
		}
		
	    double total = 0;
	    for (double element : logReturn) {
	        total += element;
	    }
	    
	    double average = total / logReturn.length;
	    double variance = getVariance(logReturn);
	    double deviation = getStdDev(logReturn);
	    double drift = average - (variance/2);
	    
//        System.out.println("MonteCarloService> Initializing with Values:");
//        System.out.println("\tVariance: " + variance);
//        System.out.println("\tDrift: " + drift);
//        System.out.println("\tDeviation: " + deviation);
//        System.out.println("\tAverage: " + average);
	    
	    DictionaryResult jobConfiguration = new DictionaryResult();
	    jobConfiguration.value("average", "" + average);
	    jobConfiguration.value("variance", "" + variance);
	    jobConfiguration.value("deviation", "" + deviation);
	    jobConfiguration.value("drift", "" + drift);
	    jobConfiguration.value("lastClose","" + (double) (stockData.get(stockData.size() - 1).get(4)));
				
//	    System.out.println("MonteCarloService> Starting Job");
		MonteCarloJob job = new MonteCarloJob();
		try 
		{
			job.configuration(jobConfiguration);
		} 
		catch (ConfigurationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result(job.result());
		addJob(job);
	}
	

    double getMean(double[] data)
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/data.length;
    }

    double getVariance(double[] data)
    {
        double mean = getMean(data);
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/data.length;
    }

    double getStdDev(double[] data)
    {
        return Math.sqrt(getVariance(data));
    }

    public double median(double[] data) 
    {
       double[] b = new double[data.length];
       System.arraycopy(data, 0, b, 0, b.length);
       Arrays.sort(b);

       if (data.length % 2 == 0) 
       {
          return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
       } 
       else 
       {
          return b[b.length / 2];
       }
    }
}
