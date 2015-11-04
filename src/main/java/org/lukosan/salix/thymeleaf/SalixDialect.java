package org.lukosan.salix.thymeleaf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.doctype.resolution.IDocTypeResolutionEntry;
import org.thymeleaf.doctype.translation.IDocTypeTranslation;
import org.thymeleaf.processor.IProcessor;

public class SalixDialect implements IExpressionEnhancingDialect {

	public static final String EXPRESSION_OBJECT_NAME = "salix";

	private final SalixProcessor salixProcessor;

	public SalixDialect(final SalixProcessor salixProcessor) {
		this.salixProcessor = salixProcessor;
	}

	@Override
	public String getPrefix() {
		return EXPRESSION_OBJECT_NAME;
	}

	@Override
	public Set<IProcessor> getProcessors() {

		return new HashSet<IProcessor>();
	}

	@Override
	public Map<String, Object> getExecutionAttributes() {
		return new HashMap<String, Object>();
	}

	@Override
	public Set<IDocTypeTranslation> getDocTypeTranslations() {
		return new HashSet<IDocTypeTranslation>();
	}

	@Override
	public Set<IDocTypeResolutionEntry> getDocTypeResolutionEntries() {
		return new HashSet<IDocTypeResolutionEntry>();
	}

	@Override
	public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
		final Map<String, Object> objects = new HashMap<>();
		objects.put(EXPRESSION_OBJECT_NAME, salixProcessor);
		return objects;
	}
}
