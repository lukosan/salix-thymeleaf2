package org.lukosan.salix.thymeleaf;

import org.thymeleaf.context.Context;

public class SalixContext extends Context {

	private String scope;

	public SalixContext(String scope) {
		this.scope = scope;
	}
		
	public String getScope() {
		return scope;
	}
}