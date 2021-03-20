package main.mtcg.clientserver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.mtcg.*;
import main.mtcg.cards.Card;
import main.mtcg.cards.Pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class RequestContext {
    //GET, POST, PUT, DELETE
    private String verb;
    private String URI;
    private String version;
    //TODO: shouldn't it be "header"?
    private ArrayList<String> headers;
    private String body;
    private String log;
    private ArrayList<Pack> allPackages = new ArrayList<>();
    private Dictionary<User,Pack> userPackDictionary = new Hashtable<>();
    private Dictionary<String, User> userDictionary = new Hashtable<>();
    private ArrayList<User> battlers = new ArrayList<>();
    //TODO: maybe just implement it without DB data? at least the complicated parts like deck/stack/battle
    //private ConcurrentHashMap

    public RequestContext(String verb, String URI, String version, ArrayList<String> headers, String body) {
        this.verb = verb;
        this.URI = URI;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }

    public RequestContext() {

    }

    //TODO: retire messageID
    //TODO: the purpose of this method could perhaps be more fittingly described with the name "handleRequest" instead of generateResponse
    public String handleRequest(Dictionary<Integer, String> messages) throws JsonProcessingException, SQLException {
        //TODO: TEST THE WEBSERVICE HANDLER
        //handle request and send appropriate response
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(getVersion());
        //String messageID = getMessageID();
        if (getVerb().equals("GET")) {
            //retrieve information without modifying it
            //if found: 200 (OK)
            //along with response body (XML or JSON)
            //if not found: 404 (NOT FOUND)
            //syntax error: 400 (BAD REQUEST)
            //TODO: THE ERROR LIES HERE. MAKE SURE TO ACCOUNT FOR TRAILING SLASHES, AS IN: IT COULD BE "/messages" or "/messages/"
            //TODO: recommended procedure: remove all trailing slashes
            /*if (getURI().equals("/messages")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                for (int i = 0; i < messages.size(); i++) {
                    responseBuilder.append(messages.get(i));
                }
            } else if (messageID != null && getURI().equals("/messages" + messageID)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(messages.get(Integer.parseInt(messageID) - 1));
            }*/
            if (getURI().equals("/cards")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //output all cards that belong to a specific user
                //...
                Pack cards = new Postgres().getCardsForUser(getUsernameFromToken());
                responseBuilder.append(Json.serializePack(cards));
            } else if (getURI().equals("/deck")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(getUsernameFromToken()); //TODO: get deck for user from token
                //...
            } else if (getURI().equals("/deck?format=plain")) {
                responseBuilder.append(ResponseCodes.OK);
                responseBuilder.append(new Postgres().getUser(getUsernameFromToken()).getDeck());
            } else if (getURI().startsWith("/users" /* + /username */)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                if (getUsernameFromToken().equals(getUsernameFromURI())) {
                    //User user = new Postgres().getUser(getUsernameFromToken());
                    User user = this.userDictionary.get(getUsernameFromToken());
                    responseBuilder.append(Json.serializeUser(user));
                }
                //...
            }
            else if (getURI().equals("/stats")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(new Postgres().getStatsForUser(getUsernameFromToken()));
                //...
            } else if (getURI().equals("/score")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(new Postgres().getScoreboard());
                //...
            } else if (getURI().equals("/tradings")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(new Postgres().getTradingDeals());
            }

        } else if (getVerb().equals("POST")) {
            //create new resource
            //if created: 201 (Created) + entity which describes the status of the request
            //and refers to the new resource, and a location header
            //if resource can't be identified by a URI, either HTTP response code 200 (OK) or 204 (No Content)
            //if (getURI().equals("/messages")) {
            //messages.put(messages.size(), getBody());
            if (getURI().equals("/users")) {
                User user = Json.parseUser(getBody());
                new Postgres().register(user);
                this.userDictionary.put(user.getUsername(), user);
                responseBuilder.append(ResponseCodes.CREATED.toString());
                responseBuilder.append(messages.get(getBody()));
                //TODO: parse user, pass object to database
                //TODO: this is what you have to do next
            } else if (getURI().equals("/sessions")) {
                User user = Json.parseUser(getBody());
                if (new Postgres().login(user)) {
                    responseBuilder.append(ResponseCodes.CREATED.toString());
                } else {
                    responseBuilder.append(ResponseCodes.NOT_FOUND.toString());
                }
                responseBuilder.append(messages.get(getBody()));


            } else if (getURI().equals("/packages")) {
                //TODO: check header token for "admin" username
                Pack pack = Json.parsePack(getBody());
                //new Postgres().createPackage(pack);
                this.allPackages.add(pack);
                responseBuilder.append(ResponseCodes.CREATED.toString());
                responseBuilder.append(messages.get(getBody()));
            } else if (getURI().equals("/transactions/packages")) {
                //TODO: acquire packages. parse user from header
                //get users from database, iterate through them and check whether the username is contained within the header
                new Postgres().acquirePackages(new Postgres().getUser(getUsernameFromToken()));
                //User user = new Postgres().getUser(getUsernameFromToken());
                if (this.allPackages != null && !this.allPackages.isEmpty()) {
                    try {
                        this.userDictionary.get(getUsernameFromToken()).addToStack(this.allPackages.get(1));
                    } catch (Exception e) {

                    }
                    this.allPackages.remove(0);
                    responseBuilder.append(ResponseCodes.CREATED.toString());
                } else {
                    responseBuilder.append(ResponseCodes.NOT_FOUND);
                }
                responseBuilder.append(messages.get(getBody()));

            } else if (getURI().equals("/tradings")) {
                new Postgres().createTradingDeal(Json.parseTrade(getBody()));
                responseBuilder.append(ResponseCodes.CREATED.toString());
                responseBuilder.append(messages.get(getBody()));

            } else if (getURI().equals("/battles")) {
                //TODO: this is where the battle should happen
                //add first person to database in the battle table
                //then add the second person to the database in the battle table
                //then, if there is at least two people in there, get their usernames and have them battle each other
                //then print out the winner, add it to their stats and remove them from the battle table
                this.battlers.add(new Postgres().getUser(getUsernameFromToken()));
                User user = this.userDictionary.get(getUsernameFromToken());
                //TODO: implement these three methods!!!
                //TODO: add battler before if statement
                //TODO: remove battlers in if STATEMENT

                //comment out if statement since it doesn't seem to work
                if (/*new Postgres().getBattlerCount() > 1*/ this.battlers.size() > 1) {
                    //Battle battle = new Battle(new Postgres().popBattler(), new Postgres().popBattler());
                    try {
                        Battle battle = new Battle(this.battlers.get(0), this.battlers.get(1));
                        battle.returnWinner();
                        this.battlers.remove(0);
                        this.battlers.remove(0);

                    } catch (Exception e) {

                    }
                    //passBattleLog(battle.getLog());
                } else {
                    new Postgres().addBattler(new Postgres().getUser(getUsernameFromToken()));
                    this.battlers.add(new Postgres().getUser(getUsernameFromToken()));
                }
                responseBuilder.append(ResponseCodes.CREATED.toString());
                responseBuilder.append(messages.get(getBody()));

            } else if (getURI().equals("/tradings" + "" /* + /id */)) {

            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }
        } else if (getVerb().equals("PUT")) {
            //update an existing resource
            //if it doesn't exist yet, decide between creating it or not (in my case it will not be created
            //to uphold the consistency of the request verbs)
            //if created: 201 (Created)
            //if modified: 200 (OK) or 204 (No Content)
            /*if (messageID != null && getURI().equals("/messages/" + messageID)) {
                if (messages.get(Integer.parseInt(messageID)) != null
                        && !messages.get(Integer.parseInt(messageID)).isEmpty()) {
                    messages.put(Integer.parseInt(messageID), getBody());
                    responseBuilder.append(ResponseCodes.OK.toString());
                    //responseBuilder.append(messages.indexOf(requestContext.getBody()));
                } else if (getBody() == null || getBody().isEmpty()
                        || getBody().isBlank()) {
                    responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
                }
            }*/
            if (getURI().equals("/deck")) {
                    responseBuilder.append(ResponseCodes.OK.toString());
                    //TODO: do something
                    //new Postgres().configureDeck(new Postgres().getUser(getUsernameFromToken()), getBody());
                User user = this.userDictionary.get(getUsernameFromToken());
                //TODO:
                //ObjectMapper objectMapper = new ObjectMapper();
                //objectMapper.
                //objectMapper.writeValueAsString(getBody());
                //user.addToDeck(Json.serializeCardCollection());
                ArrayList<String> arrayList = Json.parseIDList(getBody());
                for (String s : arrayList) {
                    try {
                        user.addFromStack(s);
                    } catch (Exception e) {

                    }

                }



                    //...

            } else if (getURI().startsWith("/users" /* + /username */)) {
                //TODO: parse user from uri and edit user data
                responseBuilder.append(ResponseCodes.OK.toString());
                if (getUsernameFromToken().equals(getUsernameFromURI())) {
                    new Postgres().updateUser(Json.parseUser(getBody()));
                }
                    //...
            }

        } else if (getVerb().equals("DELETE")) {
            //delete resources identified by request URI
            //if successful and response includes entity describing status: 200 (OK)
            //if action has been queued: 202 (Accepted)
            //if action was performed but the response does not include an entity: 204 (No Content)
            /*if (messageID != null && getURI().equals("/messages/" + messageID)) {
                messages.remove(Integer.parseInt(messageID));
                responseBuilder.append(ResponseCodes.OK.toString());
            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }*/
            if (getURI().startsWith("/tradings" /* + /id */)) {
                //TODO: get trading id from second part of URI
                responseBuilder.append(ResponseCodes.OK.toString());
                //TODO: delete trading deals
                new Postgres().deleteTradingDeal(getTradeId());
                //...
            }
        } else {
            responseBuilder.append(ResponseCodes.BAD_REQUEST.toString());
        }
        //TODO:something went wrong here... check with println
        //System.out.println("RESPONSE " + responseBuilder.toString());
        return responseBuilder.toString().trim();
    }

    public String getVerb() {
        return verb;
    }

    public String getURI() {
        return URI;
    }

    public String getVersion() {
        return version;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void passBattleLog(String log) {
        this.log = log;
    }

    public String retrieveBattleLog() {
        return this.log;
    }

    /*public String getMessageID() {
        String messageID;
        String[] extractID = URI.split("/");
        if (extractID.length == 3) {
            messageID = extractID[2];
        } else {
            messageID = null;
        }
        return messageID;
    }*/

    public void parseRequest(BufferedReader in) throws IOException {
        //parse the http request's header containing the following information
        String request = null;
        StringBuilder requestBuilder = new StringBuilder();
        String readRequest = in.readLine();
        while (readRequest != null && !readRequest.isEmpty() && !readRequest.isBlank()) {
            requestBuilder.append(readRequest).append("\r\n");
            System.out.println("readRequest: " + readRequest);
            readRequest = in.readLine();
        }
        if (readRequest != null) {
            request = requestBuilder.toString();
            String[] parsedRequest = request.trim().split("\r\n");
            String[] requestLine = parsedRequest[0].trim().split(" ");
            System.out.println("requestLine: "+ Arrays.toString(requestLine));
            String verb = requestLine[0].trim();
            System.out.println("verb: " + verb);
            String URI = requestLine[1].trim();
            System.out.println("URI: " + URI);
            String version = requestLine[2].trim();
            System.out.println("version: " + version);
            //parse header
            //request line: verb, URI, Version
            //optional request headers (name:value, ...)
            //check for blank line, then call parseBody
            //new line
            //skip spaces and/or empty lines or avoid continuing if all that's left is whitespace
            ArrayList<String> headers = new ArrayList<>(Arrays.asList(parsedRequest).subList(1, parsedRequest.length));
            System.out.println("headers: " + headers);
            this.setVerb(verb);
            this.setURI(URI);
            this.setVersion(version);
            this.setHeaders(headers);
        }
    }

    public void parseBody(BufferedReader in) throws IOException {
        //separate method because in.ready() doesn't work with the BufferedReader created from a StringReader
        //which is used in RequestContextTest
        //detect blank line to make sure the body exists
        //parse body (aka payload)
        //the body is optional and contains additional information for the server
        StringBuilder bodyBuilder = new StringBuilder();
        while (in.ready()) {
            //for some godforsaken reason the body isn't recognized as chars and it took me a few days to notice
            bodyBuilder.append((char)in.read());
        }
        String body = bodyBuilder.toString().trim();
        System.out.println("body: " + body);
        this.setBody(body);
    }

    public String getUsernameFromURI() {
        return getURI().split("/")[2];
    }

    public String getTradeId() {
        return getURI().split("/")[2];
    }

    public String getUsernameFromToken() {
        //something with string split
        //depending on the scenario, the username is in a different header index
        //iterate through headers
        String result = "";
        for (String s: headers) {
            if (s.startsWith("Authorization")) {
                //returns "user" from user-mtcgToken
                result = s.split("-")[0];
            }
        }
        return result;

        // alternatively
        /*
        Pattern pattern = Pattern.compile("Authorization: (.+)-(.+)");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
        //input is wrong format ...
        }

        String user = matcher.group(1);
        String token = matcher.group(2);
        */
    }

}