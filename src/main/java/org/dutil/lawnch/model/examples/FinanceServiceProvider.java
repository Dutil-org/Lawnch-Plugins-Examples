package org.dutil.lawnch.model.examples;

import org.dutil.lawnch.model.serviceprovider.ServiceProvider;

import ro.fortsoft.pf4j.Extension;

@Extension
public class FinanceServiceProvider extends ServiceProvider {
	
	public FinanceServiceProvider()
	{
		super();
		descriptor().commonName("Financial ServiceProvider");
	}
}
