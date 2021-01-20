package main.mtcg;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.mtcg.cards.Card;
import main.mtcg.cards.Pack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Postgres {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "password";
    private Connection connection;
    private Statement statement;
    private int packageNumber = 0;

    public Postgres() {
        //reconnect with every database action
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        /*finally {
            //close resources
            //TODO: drop database at the end
            try {
                if (statement != null)
                    statement.close();
            } catch(SQLException ignored){
            }
            try {
                if (connection != null)
                    connection.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }*/
    }

    //TODO: have all methods that can fail return a boolean and print out "task failed" in that case. With those methods, it also makes more sense to use a prepared statement.
    //no need to create a new db since the db "postgres" already exists
    public void createTables() throws SQLException {
        //TODO: create all necessary tables
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL, bio TEXT, image TEXT, coins INT DEFAULT 20 NOT NULL, elo INT DEFAULT 100 NOT NULL, card_ids TEXT)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS BATTLERS (username TEXT PRIMARY KEY NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS CARDS (id TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, damage DOUBLE PRECISION NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS SESSIONS (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS PACKAGES (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)");
        //statement.executeUpdate("CREATE TABLE IF NOT EXISTS SCOREBOARD (username TEXT PRIMARY KEY NOT NULL, elo INTEGER DEFAULT 100 NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS TRADINGS (id TEXT PRIMARY KEY NOT NULL, card_to_trade TEXT NOT NULL, type TEXT NOT NULL, minimum_damage INT NOT NULL)");
    }

    //TODO: write the appropriate SQL statements and insert them
    public void register(User user) throws SQLException {
        //TODO: introduce id as primary key to allow duplicate usernames? I'll just use the username as a pk for now.
        //TODO: update - it seems like there is not point in adding a userid, since the username and password have to be checked anyway
        //interesting sources:
        //https://www.tutorialspoint.com/postgresql/postgresql_environment.htm
        //https://alvinalexander.com/java/java-mysql-insert-example-preparedstatement/
        //https://www.javatpoint.com/PreparedStatement-interface
        //https://www.sqlshack.com/learn-sql-naming-conventions/
        //https://stackoverflow.com/questions/36258247/java-prepared-statements-for-postgresql
        //if you try to view the sql operations through the lens of CRUD, sign up would correspond to create which in turn corresponds to insert
        //TODO: use datagrip https://www.jetbrains.com/datagrip/features/generation.html
        //TODO: should fail if there is an attempt to create another user with the same username AND password
        //TODO: maybe add default values like the coin int? The statement has already been parsed, so it's a good time to add more data to the database

        //TODO: if the username already exists it should fail

        //TODO: check for duplicate user!!!

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (username, password) values (?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();

        } catch (Exception e) {
            //ignore duplicate users
        }
    }

    public void updateUser(User user) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USERS SET (username, bio, image) values (?, ?, ?) WHERE username = '" + user.getUsername() +"'");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getBio());
            preparedStatement.setString(3, user.getImage());
            preparedStatement.execute();

        } catch (Exception e) {
            //ignore duplicate users
        }

    }

    public void addBattler(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BATTLERS (username) values (?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();

        } catch (Exception e) {
            //ignore duplicate users
        }
    }

    public void connect(String url, User user) throws SQLException {
        this.connection = DriverManager.getConnection(url, user.getUsername(), user.getPassword());
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void login(User user) throws SQLException {
        //through the lens of CRUD, login corresponds to READ which in turn corresponds to SELECT
        //TODO: should fail if the password is incorrect
        /*PreparedStatement preparedStatement = connection.prepareStatement("SELECT username, password FROM USERS WHERE username = ? and password = ?");
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getString(1).equals(user.getUsername()) && resultSet.getString(2).equals(user.getPassword());*/
    }

    public void createCard(Card card) throws SQLException {
        /*PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CARDS (id, name, damage) values (?, ?, ?)");
        preparedStatement.setString(1, card.getId());
        preparedStatement.setString(2, card.getName());
        preparedStatement.setDouble(2, card.getDamage());
        preparedStatement.execute();*/
    }

    public void createPackage(Pack pack) throws SQLException {
        //it makes most sense to only store the card ids in the package table
        //then when i want to access a card from a package, i simple iterate through the ids of said package, and then get that card from the card table by id
        for (Card c: pack.getPack()) {
            //statement.executeUpdate("" + c.getId());
            createCard(c);
        }
        //statement.executeUpdate("INSERT INTO PACKAGES (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)");
    }

    public void createBattle(User user) {
        //TODO: add the user to the battle table
    }

    public int getBattlerCount() throws SQLException {
        //TODO: check whether both battlers are in the database
        int counter = 0;
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(username) FROM BATTLERS");
        while (resultSet.next()) {
            counter += 1;
        }
        return counter;
    }

    public User popBattler() throws SQLException {
        //retrieve User by getting the name from the battler table, then getting that User from the user table
        //then remove the User from the battler table
        String username = "";
        ResultSet resultSet = statement.executeQuery("SELECT username FROM BATTLERS LIMIT 1");
        while (resultSet.next()) {
            username = resultSet.getString("username");
        }
        statement.executeUpdate("DELETE FROM BATTLERS WHERE username ='" + username + "'");

       return getUser(username);
    }


    public int generatePackageNumber() {
        return this.packageNumber += 1;
    }

    public User getUser(String username) throws SQLException {
        //TODO: get current users. or should i get them from sessions?
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE username = '" + username + "'");
        //because of the error "ResultSet not positioned properly, perhaps you need to call next."
        String password = "";
        String bio = "";
        String image = "";
        int coins = 0;
        int elo = 0;
        while (resultSet.next()) {
            password = resultSet.getString("password");
            bio = resultSet.getString("bio");
            image = resultSet.getString("image");
            coins = resultSet.getInt("coins");
            elo = resultSet.getInt("elo");
        }
        return new User(username, password, bio, image, coins, elo);
    }

    public Pack getCardsForUser(String username) throws SQLException {
        ArrayList<String> cardIds = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT card_ids FROM USERS WHERE username = '" + username + "'");
        resultSet.close();
        ArrayList<Card> cards = null;
        String id;
        String name;
        int damage;
        for (String cardId: cardIds) {
            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM CARDS WHERE ID = " + cardId);
            while (resultSet2.next()) {
                id = resultSet2.getString("id");
                name = resultSet2.getString("name");
                damage = resultSet2.getInt("damage");
                cards.add(new Card (id, name, damage));
            }
            resultSet2.close();
        }

        return new Pack(cards);
    }

    public String getStatsForUser(String username) {
        //select from user elo
        return "";
    }

    public String getScoreboard() {
        return "";
    }

    public void createTradingDeal(Trade trade) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TRADINGS (id, card_to_trade, type, minimum_damage) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, trade.getId());
        preparedStatement.setString(2, trade.getCardToTrade());
        preparedStatement.setString(3, trade.getType());
        preparedStatement.setInt(4, trade.getMinimumDamage());
        try {

        } catch (Exception e) {
            preparedStatement.execute();
        }
    }

    public String getTradingDeals() throws SQLException, JsonProcessingException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADINGS");
        StringBuilder stringBuilder = new StringBuilder();
        while (resultSet.next()) {
            stringBuilder.append(Json.serializeTrade(new Trade(resultSet.getString("id"), resultSet.getString("card_to_trade"), resultSet.getString("type"), resultSet.getInt("minimum_damage"))));
        }
        return stringBuilder.toString();
    }

    public void acquirePackages(User user) {

    }

    public void deleteTradingDeal(String tradeID) throws SQLException {
        statement.executeUpdate("DELETE FROM TRADINGS WHERE id = '" + tradeID + "'");
    }


    public void wipe() {
        //TODO: drop every table from the database, basically the opposite of createTables()
    }

//TODO: welche daten gehören wirklich in die datenbank?

}
