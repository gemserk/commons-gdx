package com.gemserk.componentsengine.properties;

public class ReferencePropertyWithFallback<T extends Object> implements Property<T> {

	String referencedPropertyName;
	String fallbackReferencePropertyName;
	PropertiesHolder holder;
	Property<T> cachedProperty;
	
	public ReferencePropertyWithFallback(String referencedPropertyName, String fallbackReferencePropertyName) {
		this(referencedPropertyName,fallbackReferencePropertyName,null);
	}
	
	public ReferencePropertyWithFallback(String referencedPropertyName,String fallbackReferencePropertyName, PropertiesHolder holder) {
		this.referencedPropertyName = referencedPropertyName.intern();
		this.fallbackReferencePropertyName = fallbackReferencePropertyName.intern();
		this.holder = holder;
	}
	
	public void setPropertiesHolder(PropertiesHolder entity) {
		this.holder = entity;
		this.cachedProperty = null;
	}

	@Override
	public T get() {
		return (T) getProperty().get();
	}

	private Property<T> getProperty() {
		if(cachedProperty==null){
			cachedProperty = (Property<T>) holder.getProperty(referencedPropertyName);
			if(cachedProperty==null){
				cachedProperty = (Property<T>) holder.getProperty(fallbackReferencePropertyName);
			}
		}
		return cachedProperty;
	}

	@Override
	public void set(T value) {
		getProperty().set(value);
	}

	@Override
	public String toString() {
		return "REFPROPWITHFALLBACK: " + referencedPropertyName + " - " + fallbackReferencePropertyName;
	}
}
