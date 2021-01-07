package main.java.mtcg;

import main.java.mtcg.cards.Card;

import java.sql.*;
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
        finally {
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
        }

    }

    //TODO: have all methods that can fail return a boolean and print out "task failed" in that case. With those methods, it also makes more sense to use a prepared statement.
    //no need to create a new db since the db "postgres" already exists
    public void createTables() throws SQLException {
        //TODO: create all necessary tables
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL, coins INTEGER DEFAULT 20 NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS CARDS (id TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, damage DOUBLE PRECISION NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS SESSIONS (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS PACKAGES (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)");
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
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (username, password) values (?, ?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.execute();
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

    public void createPackage(List<Card> pack) throws SQLException {
        //it makes most sense to only store the card ids in the package table
        //then when i want to access a card from a package, i simple iterate through the ids of said package, and then get that card from the card table by id
        for (Card c: pack) {
            //statement.executeUpdate("" + c.getId());
            createCard(c);
        }
        //statement.executeUpdate("INSERT INTO PACKAGES (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)");
    }

    public int generatePackageNumber() {
        return this.packageNumber += 1;
    }


}
