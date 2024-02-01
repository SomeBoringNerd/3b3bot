package xyz.someboringnerd.b3bot.managers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@UtilityClass
public class DatabaseManager {

    public static void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:./database.db");
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS messages (primary_key INTEGER PRIMARY KEY, player TEXT NOT NULL, message TEXT NOT NULL, timestamp TEXT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sessions (primary_key INTEGER PRIMARY KEY, player TEXT NOT NULL, start TEXT NOT NULL, stop TEXT NOT NULL, death INTEGER DEFAULT 0, kills INTEGER DEFAULT 0, messages INTEGER DEFAULT 0, ping INTEGER)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS players  (primary_key INTEGER PRIMARY KEY, player TEXT NOT NULL, firstseen, TEXT NOT NULL, kill INTEGER, death INTEGER DEFAULT 0, messages INTEGER DEFAULT 0, total_session INTEGER DEFAULT 1, totalplaytime TEXT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS server   (primary_key INTEGER PRIMARY KEY, playerCount INTEGER, timestamp TEXT NOT NULL)");

        } catch (SQLException e) {
            throw new RuntimeException("Error creating tables", e);
        }
    }



}
