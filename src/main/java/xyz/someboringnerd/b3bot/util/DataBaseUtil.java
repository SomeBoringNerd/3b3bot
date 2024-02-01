package xyz.someboringnerd.b3bot.util;

import lombok.experimental.UtilityClass;
import xyz.someboringnerd.b3bot.managers.SessionManager;
import xyz.someboringnerd.b3bot.systems.tracker.Session;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DataBaseUtil {

    public static void addMessage(String sender, String content) {
        if(ChatUtil.isIgnore()) return;

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:./database.db")) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO messages (player, message, timestamp) VALUES (?, ?, ?)");

            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, String.valueOf(TimeUtil.getNow()));

            preparedStatement.executeUpdate();
            if(SessionManager.getInstance().getSessionByName(sender) != null)
                SessionManager.getInstance().getSessionByName(sender).messageSent();

        } catch (SQLException e) {
            ChatUtil.setIgnore(true);
            ChatUtil.sendMessage("I'm having trouble saving a message to the data base, check the console for info");
            ChatUtil.setIgnore(false);
            LogUtil.print(e.getMessage());
        }
    }

    public static void saveSession(Session session)
    {
        if(ChatUtil.isIgnore()) return;

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:./database.db")) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sessions (player, start, stop, death, kills, messages, ping) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, session.getPlayer());
            preparedStatement.setString(2, String.valueOf(session.getStart()));
            preparedStatement.setString(3, String.valueOf(TimeUtil.getNow()));
            preparedStatement.setInt(4, session.getDeath());
            preparedStatement.setInt(5, session.getKill());
            preparedStatement.setInt(6, session.getSentMessage());
            preparedStatement.setInt(7, session.getAveragePing());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            ChatUtil.setIgnore(true);
            ChatUtil.sendMessage("I'm having trouble saving a tracked session to the data base, check the console for info");
            ChatUtil.setIgnore(false);
            LogUtil.print(e.getMessage());
        }
    }

    public static String[] getMessageFromPlayer(String player) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:./database.db")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM messages WHERE player=?");
            preparedStatement.setString(1, player);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> messages = new ArrayList<>();

            while (resultSet.next()) {
                String message = resultSet.getString("message");
                messages.add(message);
            }

            // Convert the List to an array
            return messages.toArray(new String[0]);

        } catch (SQLException e) {
            ChatUtil.setIgnore(true);
            ChatUtil.sendMessage("I'm having trouble fetching data from the database, check the console for info");
            ChatUtil.setIgnore(false);
            LogUtil.print(e.getMessage());
            return null;
        }
    }

}
