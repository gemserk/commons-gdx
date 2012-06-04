package com.gemserk.commons.gdx.resources;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MultilanguageResourceBuilder<T> implements ResourceBuilder<T> {

	private Map<Locale, ResourceBuilder<T>> resourceBuilders = new HashMap<Locale, ResourceBuilder<T>>();
	private Locale defaultLocale;

	@Override
	public boolean isVolatile() {
		if (defaultLocale == null)
			throw new IllegalStateException("Multilanguage resource builder needs a default locale");
		ResourceBuilder<T> resourceBuilder = resourceBuilders.get(defaultLocale);
		if (resourceBuilder == null)
			throw new IllegalStateException("Multilanguage resource builder needs a default resource builder");
		return resourceBuilder.isVolatile();
	}

	public MultilanguageResourceBuilder<T> defaultLocale(Locale locale) {
		this.defaultLocale = locale;
		return this;
	}

	public MultilanguageResourceBuilder<T> resource(Locale locale, ResourceBuilder<T> resourceBuilder) {
		resourceBuilders.put(locale, resourceBuilder);
		return this;
	}

	@Override
	public T build() {
		Locale locale = Locale.getDefault();

		if (defaultLocale == null)
			throw new IllegalStateException("Multilanguage resource builder needs a default locale");

		if (!resourceBuilders.containsKey(locale))
			locale = defaultLocale;

		ResourceBuilder<T> resourceBuilder = resourceBuilders.get(locale);

		if (resourceBuilder == null)
			throw new IllegalStateException("Multilanguage resource builder needs a default resource builder");

		return resourceBuilder.build();
	}

}
