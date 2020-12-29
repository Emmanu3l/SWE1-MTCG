package main.java.mtcg.clientserver;

public enum ResponseCodes {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found");

        private final int code;
        private final String description;

    ResponseCodes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    //TODO: retool this class to further handle the requests, as in: if it's a post request, see whether it's a login or Battle Request

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        //return " " + code + " (" + description + ")\r\n";
        return " " + code + " " + description + "\r\n";
    }

}
