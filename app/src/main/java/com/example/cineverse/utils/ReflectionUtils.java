package com.example.cineverse.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The {@link ReflectionUtils} class provide utility for reflection-related operations.
 */
public class ReflectionUtils {

    /**
     * Retrieves the generic type argument at the specified index of the generic superclass extended by the
     * given class.
     *
     * @param clazz The class for which to retrieve the generic type argument of its superclass.
     * @param index The index of the generic type argument to retrieve.
     * @return The {@code Class} object representing the generic type argument at the specified index,
     *         or {@code null} if the generic type information is not available, or the index is out of bounds.
     *
     * <p>This method is particularly useful when dealing with parameterized types (e.g., generic classes
     * or interfaces) to dynamically obtain information about the actual type arguments used at runtime.</p>
     * <br>
     * <p>Example usage:</p>
     * <pre>{@code
     * // Assuming a class definition like:
     * class A extends B<T, U> { ... }
     *
     * // Usage:
     * Class<?> firstType = ReflectionUtils.getGenericType(A.class, 0); // Returns T
     * Class<?> secondType = ReflectionUtils.getGenericType(A.class, 1); // Returns U
     * }</pre>
     * <br>
     * <p>Note: The index parameter corresponds to the position of the generic type argument
     * in the order they are declared in the class definition. For example, in the declaration
     * of a parameterized class like {@code class A extends B<T, U>}, index 0 would correspond
     * to type argument {@code T}, and index 1 would correspond to type argument {@code U}.</p>
     */
    public static Class<?> getGenericType(Class<?> clazz, int index) {
        // Get the generic superclass
        Type superClass = clazz.getGenericSuperclass();

        // Check if it's a parameterized type
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;

            // Get the actual type arguments
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            // Check if index is within bounds
            if (index >= 0 && index < typeArguments.length) {
                return (Class<?>) typeArguments[index];
            }
        }

        // If not found or out of bounds, return null
        return null;
    }

}
