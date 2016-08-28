package com.android.core.utils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericUtil {

	public static Type[] getGenericParameter(final Type type) {
		if (ParameterizedType.class.isInstance(type)) {
			return ParameterizedType.class.cast(type).getActualTypeArguments();
		}
		if (GenericArrayType.class.isInstance(type)) {
			return getGenericParameter(GenericArrayType.class.cast(type)
					.getGenericComponentType());
		}
		return null;
	}

	public static Class<?> getRawClass(final Type type) {
		if (Class.class.isInstance(type)) {
			return Class.class.cast(type);
		}
		if (ParameterizedType.class.isInstance(type)) {
			final ParameterizedType parameterizedType = ParameterizedType.class
					.cast(type);
			return getRawClass(parameterizedType.getRawType());
		}
		if (WildcardType.class.isInstance(type)) {
			final WildcardType wildcardType = WildcardType.class.cast(type);
			final Type[] types = wildcardType.getUpperBounds();
			return getRawClass(types[0]);
		}
		if (GenericArrayType.class.isInstance(type)) {
			final GenericArrayType genericArrayType = GenericArrayType.class
					.cast(type);
			final Class<?> rawClass = getRawClass(genericArrayType
					.getGenericComponentType());
			return Array.newInstance(rawClass, 0).getClass();
		}
		return null;
	}

	protected static void gatherTypeVariables(final Class<?> clazz,
			final Type type, final Map<TypeVariable<?>, Type> map) {
		if (clazz == null) {
			return;
		}
		gatherTypeVariables(type, map);

		final Class<?> superClass = clazz.getSuperclass();
		final Type superClassType = clazz.getGenericSuperclass();
		if (superClass != null) {
			gatherTypeVariables(superClass, superClassType, map);
		}

		final Class<?>[] interfaces = clazz.getInterfaces();
		final Type[] interfaceTypes = clazz.getGenericInterfaces();
		for (int i = 0; i < interfaces.length; ++i) {
			gatherTypeVariables(interfaces[i], interfaceTypes[i], map);
		}
	}

	protected static void gatherTypeVariables(final Type type,
			final Map<TypeVariable<?>, Type> map) {
		if (ParameterizedType.class.isInstance(type)) {
			final ParameterizedType parameterizedType = ParameterizedType.class
					.cast(type);
			final TypeVariable<?>[] typeVariables = GenericDeclaration.class
					.cast(parameterizedType.getRawType()).getTypeParameters();
			final Type[] actualTypes = parameterizedType
					.getActualTypeArguments();
			for (int i = 0; i < actualTypes.length; ++i) {
				map.put(typeVariables[i], actualTypes[i]);
			}
		}
	}

	public static Map<TypeVariable<?>, Type> getTypeVariableMap(
			final Class<?> clazz) {
		final Map<TypeVariable<?>, Type> map = new LinkedHashMap<TypeVariable<?>, Type>();

		final TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
		for (TypeVariable<?> typeParameter : typeParameters) {
			map.put(typeParameter,
					getActualClass(typeParameter.getBounds()[0], map));
		}

		final Class<?> superClass = clazz.getSuperclass();
		final Type superClassType = clazz.getGenericSuperclass();
		if (superClass != null) {
			gatherTypeVariables(superClass, superClassType, map);
		}

		final Class<?>[] interfaces = clazz.getInterfaces();
		final Type[] interfaceTypes = clazz.getGenericInterfaces();
		for (int i = 0; i < interfaces.length; ++i) {
			gatherTypeVariables(interfaces[i], interfaceTypes[i], map);
		}

		return map;
	}

	public static Class<?> getActualClass(final Type type,
			final Map<TypeVariable<?>, Type> map) {
		if (Class.class.isInstance(type)) {
			return Class.class.cast(type);
		}
		if (ParameterizedType.class.isInstance(type)) {
			return getActualClass(ParameterizedType.class.cast(type)
					.getRawType(), map);
		}
		if (WildcardType.class.isInstance(type)) {
			return getActualClass(WildcardType.class.cast(type)
					.getUpperBounds()[0], map);
		}
		if (TypeVariable.class.isInstance(type)) {
			final TypeVariable<?> typeVariable = TypeVariable.class.cast(type);
			if (map.containsKey(typeVariable)) {
				return getActualClass(map.get(typeVariable), map);
			}
			return getActualClass(typeVariable.getBounds()[0], map);
		}
		if (GenericArrayType.class.isInstance(type)) {
			final GenericArrayType genericArrayType = GenericArrayType.class
					.cast(type);
			final Class<?> componentClass = getActualClass(
					genericArrayType.getGenericComponentType(), map);
			return Array.newInstance(componentClass, 0).getClass();
		}
		return null;
	}

	public static Type[] getGenericParameter(final Type t, final Class<?> c) {
		if (t == null) {
			return null;
		}
		Class<?> rowClass = GenericUtil.getRawClass(t);
		if (rowClass == null) {
			return null;
		}
		Type[] allTypes = getGenericSuperClassAndInterfaces(rowClass);
		for (Type type : allTypes) {
			if (c.equals(GenericUtil.getRawClass(type))) {
				return GenericUtil.getGenericParameter(type);
			}
			Type[] types = getGenericParameter(type, c);
			if (types != null) {
				return types;
			}
		}
		return null;
	}

	private static Type[] getGenericSuperClassAndInterfaces(final Class<?> cls) {
		List<Type> allTypes = new ArrayList<Type>();
		Type genericSuperclass = cls.getGenericSuperclass();
		if (genericSuperclass != null) {
			allTypes.add(genericSuperclass);
		}
		Type[] genericInterfaces = cls.getGenericInterfaces();
		if (genericInterfaces != null) {
			allTypes.addAll(Arrays.asList(genericInterfaces));
		}
		return allTypes.toArray(new Type[allTypes.size()]);
	}
}
