package org.dutil.lawnch.model.examples;

import org.dutil.lawnch.model.result.DictionaryResult;
import org.dutil.lawnch.model.result.Result;
import org.dutil.lawnch.model.service.StatelessService;
import org.dutil.lawnch.system.SessionInterface;

public class TestService extends StatelessService<DictionaryResult> {

	public TestService() {
		descriptor().commonName("Test for a stateless service");
	}

	@Override
	public DictionaryResult execute(Result configuration,
			SessionInterface session) {
		System.out.println("TestService> excuting :)");
		return null;
	}


}
