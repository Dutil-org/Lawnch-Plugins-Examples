package org.dutil.lawnch.model.examples;

import org.dutil.lawnch.model.serviceprovider.StatelessServiceProvider;

public class TestServiceProvider extends StatelessServiceProvider {

	public TestServiceProvider() {
		super();
		descriptor().commonName("Test ServiceProvider");
	}

}
