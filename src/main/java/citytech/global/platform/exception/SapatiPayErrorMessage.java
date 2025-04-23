package citytech.global.platform.exception;

public enum SapatiPayErrorMessage {
    EMAIL_ID_ALREADY_EXISTS("SP001","Email Id Already Exists"),
    ADMIN_ALREADY_EXISTS("SP002","Admin already exists"),
    ZERO_BALANCE("SP003","Borrower should have 0 balance initially"),
    MINIMUM_500_BALANCE("SP004","Lender should have minimum balance of 500"),
    SOMETHING_WENT_WRONG("SP005","Something went wrong"),
    INVALID_RESPONSE("SP006","Invalid Response from API"),
    EXCEL_EXPORT_FAILED("SP007","Failed to export excel"),
    LOGIN_UNSUCCESSFUL("SP008","User Login Unsuccessful"),
    REGISTRATION_FAILED("SP009","Registration_failed"),
    LOGIN_CREDENTIALS_DOESNOT_MATCH("SP010","Login Credentials doesn't match"),
    SECURITY_INTERCEPTOR_EXCEPTION("SP011","Security interceptor Exception"),
    SECURITY_TOKEN_MISSING("SP012","Security Token is missing"),
    REQUEST_CONTEXT_NOT_SET("SP013","Request Context is not set"),
    UNKNOWN_ROLE("SP014","Unknown Role"),
    EMAIL_ID_INVALID("SP015","Email Id is not valid"),
    ;
    private String code;
    private String message;
    SapatiPayErrorMessage(String code, String message){
        this.code = code;
        this.message= message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
