package com.parabrisassi.sist.commons.validation;

import com.parabrisassi.sist.commons.errors.ValidationError;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class containing static helper methods used for validation.
 */
public class ValidationHelper {

    /**
     * Checks that the given {@code object} is null.
     *
     * @param object            The object to be checked.
     * @param errorList         The list containing the errors, in which new possible errors will be added.
     * @param notNullFieldError The error to be added on not null value.
     */
    public static void objectNull(Object object, List<ValidationError> errorList, ValidationError notNullFieldError) {
        if (object != null) {
            errorList.add(notNullFieldError);
        }
    }

    /**
     * Checks that the given {@code object} is not null.
     *
     * @param object         The object to be checked.
     * @param errorList      The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError The error to be added on null value.
     */
    public static void objectNotNull(Object object, List<ValidationError> errorList, ValidationError nullFieldError) {
        if (object == null) {
            errorList.add(nullFieldError);
        }
    }

    /**
     * Checks that the given {@code text} is not null,
     * and that its length is between {@code minLength} and {@code maxLength}.
     *
     * @param text           The text to be checked.
     * @param minLength      The text's min. length.
     * @param maxLength      The text's max. length.
     * @param errorList      The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError The error to be added on null value.
     * @param tooShortError  The error to be added on "too short" length.
     * @param tooLongError   The error to be added on "too long" length.
     */
    public static void stringNotNullAndLengthBetween(String text, int minLength, int maxLength,
                                                     List<ValidationError> errorList,
                                                     ValidationError nullFieldError,
                                                     ValidationError tooShortError,
                                                     ValidationError tooLongError) {
        if (text == null) {
            errorList.add(nullFieldError);
        } else {
            isBetween(text.length(), minLength, maxLength, errorList, tooShortError, tooLongError);
        }
    }

    /**
     * Checks that the given {@code text}'s length is between {@code minLength} and {@code maxLength},
     * if it's not null.
     *
     * @param text          The text to be checked.
     * @param minLength     The text's min. length.
     * @param maxLength     The text's max. length.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added no "too short" length.
     * @param tooLongError  The error to be added no "too long" length.
     */
    public static void stringNullOrLengthBetween(String text, int minLength, int maxLength,
                                                 List<ValidationError> errorList,
                                                 ValidationError tooShortError, ValidationError tooLongError) {
        if (text != null) {
            isBetween(text.length(), minLength, maxLength, errorList, tooShortError, tooLongError);
        }
    }


    /**
     * Checks that there are at most {@code maxAmountOfNulls} null values in the given {@code values}.
     *
     * @param maxAmountOfNulls     The max. amount of nulls allowed.
     * @param errorList            The list containing the errors, in which new possible errors will be added.
     * @param NOT_AT_LEAST_M_NULLS The error to be added on breaking the rule of max. amount of nulls.
     * @param values               The values to be checked.
     */
    public static void maxAmountOfNulls(int maxAmountOfNulls, List<ValidationError> errorList,
                                        ValidationError NOT_AT_LEAST_M_NULLS, Object... values) {
        if (Arrays.stream(values).filter(Objects::isNull)
                .collect(Collectors.toList()).size() > maxAmountOfNulls) {
            errorList.add(NOT_AT_LEAST_M_NULLS);
        }
    }

    /**
     * Checks that any of the given {@code values} are null.
     *
     * @param errorList    The list containing the errors, in which new possible errors will be added.
     * @param anyNullError The error to be added on breaking the rule of max. amount of nulls.
     * @param values       The values to be checked.
     */
    public static void anyNull(List<ValidationError> errorList, ValidationError anyNullError, Object... values) {
        maxAmountOfNulls(0, errorList, anyNullError, values);
    }

    /**
     * Checks that the given array is not {@code null}
     * and that its length is between the given {@code minLength} and {@code maxLength}
     *
     * @param array          The array to be checked.
     * @param minLength      The min length.
     * @param maxLength      The max length.
     * @param errorList      The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError The error to be added on null value.
     * @param tooShortError  The error to be added on "below min" situation.
     * @param tooLongError   The error to be added on "above max" situation.
     * @param <T>            The array type.
     */
    public static <T> void arrayNotNullAndLengthBetween(T[] array, int minLength, int maxLength,
                                                        List<ValidationError> errorList,
                                                        ValidationError nullFieldError,
                                                        ValidationError tooShortError,
                                                        ValidationError tooLongError) {
        if (array == null) {
            errorList.add(nullFieldError);
        } else {
            isBetween(array.length, minLength, maxLength, errorList, tooShortError, tooLongError);
        }
    }

    /**
     * Checks that the given array is {@code null}
     * or that its length is between the given {@code minLength} and {@code maxLength}
     *
     * @param array         The array to be checked.
     * @param minLength     The min length.
     * @param maxLength     The max length.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added on "below min" situation.
     * @param tooLongError  The error to be added on "above max" situation.
     * @param <T>           The array type.
     */
    public static <T> void arrayNullOrLengthBetween(T[] array, int minLength, int maxLength,
                                                    List<ValidationError> errorList,
                                                    ValidationError tooShortError,
                                                    ValidationError tooLongError) {
        if (array != null) {
            isBetween(array.length, minLength, maxLength, errorList, tooShortError, tooLongError);
        }
    }

    /**
     * Checks that the given array is not {@code null}
     * and that its length is between the given {@code minLength} and {@code maxLength}
     *
     * @param array          The array to be checked.
     * @param minLength      The min length.
     * @param maxLength      The max length.
     * @param errorList      The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError The error to be added on null value.
     * @param tooShortError  The error to be added on "below min" situation.
     * @param tooLongError   The error to be added on "above max" situation.
     */
    public static void arrayNotNullAndLengthBetween(byte[] array, int minLength, int maxLength,
                                                    List<ValidationError> errorList,
                                                    ValidationError nullFieldError,
                                                    ValidationError tooShortError,
                                                    ValidationError tooLongError) {
        if (array == null) {
            errorList.add(nullFieldError);
        } else {
            isBetween(array.length, minLength, maxLength, errorList, tooShortError, tooLongError);
        }
    }

    /**
     * Checks that the given array is {@code null}
     * or that its length is between the given {@code minLength} and {@code maxLength}
     *
     * @param array         The array to be checked.
     * @param minLength     The min length.
     * @param maxLength     The max length.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added on "below min" situation.
     * @param tooLongError  The error to be added on "above max" situation.
     */
    public static void arrayNullOrLengthBetween(byte[] array, int minLength, int maxLength,
                                                List<ValidationError> errorList,
                                                ValidationError tooShortError,
                                                ValidationError tooLongError) {
        if (array != null) {
            isBetween(array.length, minLength, maxLength, errorList, tooShortError, tooLongError);
        }
    }

    /**
     * Checks that the given {@code value} is not null, and that it is between {@code min} and {@code max}.
     *
     * @param value          The value to be checked.
     * @param min            The min. value.
     * @param max            The max. value.
     * @param errorList      The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError The error to be added on null value.
     * @param tooShortError  The error to be added on "below min" situation.
     * @param tooLongError   The error to be added on "above max" situation.
     * @param <T>            The type of object to be compared (must be a {@link Comparable}).
     */
    public static <T extends Comparable<T>> void notNullAndIsBetween(T value, T min, T max,
                                                                     List<ValidationError> errorList,
                                                                     ValidationError nullFieldError,
                                                                     ValidationError tooShortError,
                                                                     ValidationError tooLongError) {
        notNullAndIsBetween(value, min, max, Comparable::compareTo,
                errorList, nullFieldError, tooShortError, tooLongError);
    }

    /**
     * Checks that the given {@code value} is not null, and that it is between {@code min} and {@code max}.
     *
     * @param value          The value to be checked.
     * @param min            The min. value.
     * @param max            The max. value.
     * @param comparator     The {@link Comparator} to be used to compare values.
     * @param errorList      The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError The error to be added on null value.
     * @param tooShortError  The error to be added on "below min" situation.
     * @param tooLongError   The error to be added on "above max" situation.
     * @param <T>            The type of object to be compared.
     */
    public static <T> void notNullAndIsBetween(T value, T min, T max, Comparator<T> comparator,
                                               List<ValidationError> errorList,
                                               ValidationError nullFieldError,
                                               ValidationError tooShortError,
                                               ValidationError tooLongError) {
        if (value == null) {
            errorList.add(nullFieldError);
        } else {
            isBetween(value, min, max, comparator, errorList, tooShortError, tooLongError);
        }
    }


    /**
     * Checks that the given {@code value} is between {@code min} and {@code max}, if it's not null.
     *
     * @param value         The number to be checked.
     * @param min           The min. value.
     * @param max           The max. value.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added on "below min" situation.
     * @param tooLongError  The error to be added on "above max" situation.
     * @param <T>           The type of object to be compared (must be a {@link Comparable}).
     */
    public static <T extends Comparable<T>> void nullOrIsBetween(T value, T min, T max,
                                                                 List<ValidationError> errorList,
                                                                 ValidationError tooShortError,
                                                                 ValidationError tooLongError) {
        nullOrIsBetween(value, min, max, Comparable::compareTo, errorList, tooShortError, tooLongError);
    }

    /**
     * Checks that the given {@code value} is between {@code min} and {@code max}, if it's not null.
     *
     * @param value         The number to be checked.
     * @param min           The min. value.
     * @param max           The max. value.
     * @param comparator    The {@link Comparator} to be used to compare values.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added on "below min" situation.
     * @param tooLongError  The error to be added on "above max" situation.
     * @param <T>           The type of object to be compared.
     */
    public static <T> void nullOrIsBetween(T value, T min, T max, Comparator<T> comparator,
                                           List<ValidationError> errorList,
                                           ValidationError tooShortError,
                                           ValidationError tooLongError) {
        if (value != null) {
            isBetween(value, min, max, comparator, errorList, tooShortError, tooLongError);
        }
    }


    /**
     * Checks that the given {@code value} is between {@code min} and {@code max}.
     *
     * @param value         The value to be checked.
     * @param min           The min. value.
     * @param max           The max. value.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added on below {@code min} value.
     * @param tooLongError  The error to be added on above {@code max} value.
     * @param <T>           The type of object to be compared (must be a {@link Comparable}).
     */
    public static <T extends Comparable<T>> void isBetween(T value, T min, T max, List<ValidationError> errorList,
                                                           ValidationError tooShortError,
                                                           ValidationError tooLongError) {
        isBetween(value, min, max, Comparable::compareTo, errorList, tooShortError, tooLongError);
    }

    /**
     * Checks that the given {@code value} is between {@code min} and {@code max}.
     *
     * @param value         The value to be checked.
     * @param min           The min. value.
     * @param max           The max. value.
     * @param comparator    The {@link Comparator} to be used to compare values.
     * @param errorList     The list containing the errors, in which new possible errors will be added.
     * @param tooShortError The error to be added on below {@code min} value.
     * @param tooLongError  The error to be added on above {@code max} value.
     * @param <T>           The type of object to be compared.
     */
    public static <T> void isBetween(T value, T min, T max, Comparator<T> comparator,
                                     List<ValidationError> errorList,
                                     ValidationError tooShortError, ValidationError tooLongError) {
        if (comparator.compare(value, min) < 0) {
            errorList.add(tooShortError);
        } else if (comparator.compare(value, max) > 0) {
            errorList.add(tooLongError);
        }
    }

    /**
     * Checks that the given {@code email} is valid and between the given lengths.
     *
     * @param email             The email to be checked
     * @param minLength         The email's min. length.
     * @param maxLength         The email's max. length.
     * @param errorList         The list containing the errors, in which new possible errors will be added.
     * @param nullFieldError    The error to be added on null value.
     * @param tooShortError     The error to be added on below {@code min} number.
     * @param tooLongError      The error to be added on above {@code max} number.
     * @param invalidEmailError The error to be added on invalid email.
     */
    public static void checkEmailNotNullAndValid(String email, int minLength, int maxLength,
                                                 List<ValidationError> errorList,
                                                 ValidationError nullFieldError, ValidationError tooShortError,
                                                 ValidationError tooLongError, ValidationError invalidEmailError) {

        int listLength = errorList.size();
        stringNotNullAndLengthBetween(email, minLength, maxLength, errorList,
                nullFieldError, tooShortError, tooLongError);
        // if listLength is equal to the size of the error list, the email's length is between min and max length.
        if (listLength == errorList.size() && !EmailValidator.getInstance().isValid(email)) {
            errorList.add(invalidEmailError);
        }
    }
}
