package main.java.mtcg;

import main.java.webservicehandler.RequestContext;

import java.util.Dictionary;

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

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return " " + code + " (" + description + ")\r\n";
    }

    public static String generateResponse(RequestContext requestContext, Dictionary<Integer, String> messages) {
        //TODO: TEST THE WEBSERVICE HANDLER
        //handle request and send appropriate response
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(requestContext.getVersion());
        String messageID = requestContext.getMessageID();
        if (requestContext.getVerb().equals("GET")) {
            //retrieve information without modifying it
            //if found: 200 (OK)
            //along with response body (XML or JSON)
            //if not found: 404 (NOT FOUND)
            //syntax error: 400 (BAD REQUEST)
            if (requestContext.getURI().equals("/messages")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                for (int i = 0; i < messages.size(); i++) {
                    responseBuilder.append(messages.get(i));
                }
            } else if (messageID != null && requestContext.getURI().equals("/messages/" + messageID)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(messages.get(Integer.parseInt(messageID) - 1));
            }
        } else if (requestContext.getVerb().equals("POST")) {
            //create new resource
            //if created: 201 (Created) + entity which describes the status of the request
            //and refers to the new resource, and a location header
            //if resource can't be identified by a URI, either HTTP response code 200 (OK) or 204 (No Content)
            if (requestContext.getURI().equals("/messages")) {
                messages.put(messages.size(), requestContext.getBody());
                responseBuilder.append(ResponseCodes.CREATED.toString());
                //responseBuilder.append(messages.indexOf(requestContext.getBody()));
                responseBuilder.append(messages.get(requestContext.getBody()));
            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }
        } else if (requestContext.getVerb().equals("PUT")) {
            //update an existing resource
            //if it doesn't exist yet, decide between creating it or not (in my case it will not be created
            //to uphold the consistency of the request verbs)
            //if created: 201 (Created)
            //if modified: 200 (OK) or 204 (No Content)
            if (messageID != null && requestContext.getURI().equals("/messages/" + messageID)) {
                if (messages.get(Integer.parseInt(messageID)) != null
                        && !messages.get(Integer.parseInt(messageID)).isEmpty()) {
                    messages.put(Integer.parseInt(messageID), requestContext.getBody());
                    responseBuilder.append(ResponseCodes.OK.toString());
                    //responseBuilder.append(messages.indexOf(requestContext.getBody()));
                } else if (requestContext.getBody() == null || requestContext.getBody().isEmpty()
                        || requestContext.getBody().isBlank()) {
                    responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
                }
            }
        } else if (requestContext.getVerb().equals("DELETE")) {
            //delete resources identified by request URI
            //if successful and response includes entity describing status: 200 (OK)
            //if action has been queued: 202 (Accepted)
            //if action was performed but the response does not include an entity: 204 (No Content)
            if (messageID != null && requestContext.getURI().equals("/messages/" + messageID)) {
                messages.remove(Integer.parseInt(messageID));
                responseBuilder.append(ResponseCodes.OK.toString());
            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }
        } else {
            responseBuilder.append(ResponseCodes.BAD_REQUEST.toString());
        }
        return responseBuilder.toString().trim();
    }

}
