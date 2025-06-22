package io.uranus.ucrypt.data.constants;

public class AppConstants {

    public static final String NAME_REGEX = "^(?!^[0-9|$&+,:;=?@#|'<>.^*()%\\!\\-\\_\\~].*$).*(?=.*[a-zA-Z])?|([\\ء-\\ي0-9]).*(?=.*\\\\d)";
    public static final String INVALID_NAME_REGEX_ERROR_MESSAGE = "name is not valid!";
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 60;
    public static final int MIN_FILE_NAME_LENGTH = 1;
    public static final int MAX_FILE_NAME_LENGTH = 255;
    public static final String EMAIL_REGEX = ".+@.+\\..+";
    public static final String EMAIL_REGEX_NOT_VALID_ERROR_MESSAGE = "Email is not valid";
    public static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,24}$";
    public static final String PASSWORD_REGEX_NOT_VALID_ERROR_MESSAGE = "Password is not valid";
}
