package com.gemserk.commons.utils;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.commons.reflection.InternalField;
import com.gemserk.commons.reflection.InternalFieldDirectImpl;
import com.gemserk.commons.reflection.ReflectionUtils;
import com.gemserk.commons.reflection.ReflectionUtils.ClassCache;
import com.gemserk.componentsengine.utils.RandomAccessWithKey;

public class JavaCodeInstanceSerializerTest {

	public static interface JavaCodeInstanceSerializer {

		String toJavaCode(Object instance);

	}

	public static class JavaCodeInstanceSerializerImpl implements JavaCodeInstanceSerializer {

		@Override
		public String toJavaCode(Object instance) {
			Class<? extends Object> clazz = instance.getClass();

			StringBuilder stringBuilder = new StringBuilder();

			String className = clazz.getSimpleName();
			String instanceName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);

			stringBuilder.append("{");
			stringBuilder.append("\n");

			stringBuilder.append("\t" + className + " " + instanceName + " = new " + clazz.getSimpleName() + "();");
			stringBuilder.append("\n");

			ClassCache classCache = ReflectionUtils.getClassCache(clazz);
			RandomAccessWithKey<String, InternalField> internalFields = classCache.getInternalFields();

			for (int i = 0; i < internalFields.size(); i++) {
				InternalField internalField = internalFields.get(i);
				String fieldName = internalField.getFieldName();
				Object value = internalField.getValue(instance);
				String fieldValueString = fieldValueToString(value);

				if (internalField instanceof InternalFieldDirectImpl) {
					stringBuilder.append("\t" + instanceName + "." + fieldName + " = " + fieldValueString + ";");
				} else {
					String setterName = ReflectionUtils.getSetterName(fieldName);
					stringBuilder.append("\t" + instanceName + "." + setterName + "(" + fieldValueString + ");");
				}

				stringBuilder.append("\n");
			}

			stringBuilder.append("}");

			return stringBuilder.toString();
		}

		private String fieldValueToString(Object value) {
			if (value == null)
				return "null";
			String fieldValueString = value.toString();
			if (value != null && String.class.equals(value.getClass()))
				fieldValueString = "\"" + fieldValueString + "\"";
			else if (isShortField(value))
				fieldValueString = "(short) " + fieldValueString;
			else if (isLongField(value))
				fieldValueString = fieldValueString + "L";
			else if (isFloatField(value))
				fieldValueString = fieldValueString + "f";
			else if (isDoubleField(value))
				fieldValueString = fieldValueString + "d";
			return fieldValueString;
		}

		private boolean isShortField(Object value) {
			if (value == null)
				return false;
			return short.class.equals(value.getClass()) || Short.class.equals(value.getClass());
		}

		private boolean isLongField(Object value) {
			if (value == null)
				return false;
			return long.class.equals(value.getClass()) || Long.class.equals(value.getClass());
		}

		private boolean isFloatField(Object value) {
			if (value == null)
				return false;
			return float.class.equals(value.getClass()) || Float.class.equals(value.getClass());
		}

		private boolean isDoubleField(Object value) {
			if (value == null)
				return false;
			return double.class.equals(value.getClass()) || Double.class.equals(value.getClass());
		}

	}

	static class MyClassForTest {

		public String myField;

	}

	@Test
	public void testClassWithPublicStringFieldWithNullValue() {
		MyClassForTest myClassForTest = new MyClassForTest();
		myClassForTest.myField = null;
		String javaCode = new JavaCodeInstanceSerializerImpl().toJavaCode(myClassForTest);
		String expectedJavaCode = "{\n" //
				+ "	MyClassForTest myClassForTest = new MyClassForTest();\n" //
				+ "	myClassForTest.myField = null;\n" //
				+ "}";
		assertThat(javaCode, IsEqual.equalTo(expectedJavaCode));
	}

	@Test
	public void testClassWithPublicStringFieldWithNotNullValue() {
		MyClassForTest myClassForTest = new MyClassForTest();
		myClassForTest.myField = "Hello World";
		String javaCode = new JavaCodeInstanceSerializerImpl().toJavaCode(myClassForTest);
		String expectedJavaCode = "{\n" //
				+ "	MyClassForTest myClassForTest = new MyClassForTest();\n" //
				+ "	myClassForTest.myField = \"Hello World\";\n" //
				+ "}";
		assertThat(javaCode, IsEqual.equalTo(expectedJavaCode));
	}

	static class MyClassForTest2 {

		public boolean myBoolean;
		public short myShort;
		public int myInt;
		public long myLong;
		public float myFloat;
		public double myDouble;

	}

	@Test
	public void testClassWithPublicNumberFields() {
		MyClassForTest2 myClassForTest2 = new MyClassForTest2();

		myClassForTest2.myBoolean = true;
		myClassForTest2.myShort = (short) 10;
		myClassForTest2.myInt = 12345;
		myClassForTest2.myLong = 100000L;
		myClassForTest2.myFloat = 9.4f;
		myClassForTest2.myDouble = 5.4d;

		String javaCode = new JavaCodeInstanceSerializerImpl().toJavaCode(myClassForTest2);
		String expectedJavaCode = "{\n" //
				+ "	MyClassForTest2 myClassForTest2 = new MyClassForTest2();\n" //
				+ "	myClassForTest2.myBoolean = true;\n" //
				+ "	myClassForTest2.myShort = (short) 10;\n" //
				+ "	myClassForTest2.myInt = 12345;\n" //
				+ "	myClassForTest2.myLong = 100000L;\n" //
				+ "	myClassForTest2.myFloat = 9.4f;\n" //
				+ "	myClassForTest2.myDouble = 5.4d;\n" //
				+ "}";
		assertThat(javaCode, IsEqual.equalTo(expectedJavaCode));
	}

	static class MyClassForTest3 {

		private boolean myBoolean;

		public boolean getMyBoolean() {
			return myBoolean;
		}

		public void setMyBoolean(boolean myBoolean) {
			this.myBoolean = myBoolean;
		}

	}

	@Test
	public void testClassWithPrivateFields() {
		MyClassForTest3 myClassForTest3 = new MyClassForTest3();
		myClassForTest3.myBoolean = true;
		String javaCode = new JavaCodeInstanceSerializerImpl().toJavaCode(myClassForTest3);
		String expectedJavaCode = "{\n" //
				+ "	MyClassForTest3 myClassForTest3 = new MyClassForTest3();\n" //
				+ "	myClassForTest3.setMyBoolean(true);\n" //
				+ "}";
		assertThat(javaCode, IsEqual.equalTo(expectedJavaCode));
	}

}
