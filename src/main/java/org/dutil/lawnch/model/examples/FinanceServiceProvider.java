package org.dutil.lawnch.model.examples;

import org.dutil.lawnch.model.serviceprovider.GenericServiceProvider;

import ro.fortsoft.pf4j.Extension;

@Extension
public class FinanceServiceProvider extends GenericServiceProvider {
	
	public FinanceServiceProvider()
	{
		super();
		descriptor().commonName("Financial ServiceProvider");
	}
}
