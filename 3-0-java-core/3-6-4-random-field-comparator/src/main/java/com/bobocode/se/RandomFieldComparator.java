package com.bobocode.se;

import com.bobocode.util.ExerciseNotCompletedException;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * If no field is available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 *<p><p>
 *  <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com">visit our website</a></strong>
 *  <p>
 *
 * @author Stanislav Zabramnyi
 */
public class RandomFieldComparator<T> implements Comparator<T> {
    private Field field;
    private Class<T> targetType;

    public RandomFieldComparator(Class<T> targetType) {
        this.targetType = targetType;

        // We retrieve all declared fields of the class.
        java.lang.reflect.Field[] allFields = targetType.getDeclaredFields();

        //We filter the fields: we keep only those that are primitives or implement Comparable.
        java.util.List<Field> comparableFields = java.util.Arrays.stream(allFields)
                .filter(f -> f.getType().isPrimitive() || Comparable.class.isAssignableFrom(f.getType()))
                .collect(java.util.stream.Collectors.toList());

        // If there are no suitable fields, throw an exception based on the condition.
        if (comparableFields.isEmpty()) {
            throw new IllegalArgumentException("No primitive or Comparable fields found in class " + targetType.getName());
        }

        // We select a random index and store the field in your variable.
        int randomIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(comparableFields.size());
        this.field = comparableFields.get(randomIndex);

        // Making the field accessible (in case it is private)
        this.field.setAccessible(true);
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value greater than a non-null value.
     *
     * @param o1
     * @param o2
     * @return positive int in case of first parameter {@param o1} is greater than second one {@param o2},
     *         zero if objects are equals,
     *         negative int in case of first parameter {@param o1} is less than second one {@param o2}.
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public int compare(T o1, T o2) {
        try {
            this.field.setAccessible(true);

            Comparable v1 = (Comparable) this.field.get(o1);
            Comparable v2 = (Comparable) this.field.get(o2);

            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return 1;
            if (v2 == null) return -1;

            return v1.compareTo(v2);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field " + this.field.getName(), e);
        }
    }

    /**
     * Returns the name of the randomly-chosen comparing field.
     */
    public String getComparingFieldName() {
        // todo: implement this method;
        return this.field.getName();
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        // todo: implement this method;
        return String.format("Random field comparator of class '%s' is comparing '%s'",
                targetType.getSimpleName(), getComparingFieldName());
    }
}
