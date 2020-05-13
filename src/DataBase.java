import java.sql.*;
import java.util.ArrayList;

public class DataBase {

	private static DataBase dataBase;

	public static synchronized DataBase getInstance() {

		if (dataBase == null) {
			dataBase = new DataBase();
		}

		return dataBase;
	}

	private DataBase() {

		connectToDB();
	}

	private Connection connection;

	private void connectToDB() {

		String url = "jdbc:sqlite:./db/getFromWikipedia.db";

		try {

			connection = DriverManager.getConnection(url);

		} catch(SQLException e) {

			System.out.println("Ошибка подключения к БД");
		}
	}

	public boolean addNewUser(String login, String password) {

		if (connection == null) {

			return false;
		}

		String query =  "INSERT INTO users (login, password, history) VALUES (" +
				"\"" + login + "\", " +
				"\"" + password + "\", " +
				"\"\");";

		try {

			Statement statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {

			return false;
		}

		return true;
	}

	public boolean checkLogin(String login) {

		if (connection == null) {
			return false;
		}

		String query = "SELECT login FROM users";

		ArrayList<String> logins = new ArrayList<>();

		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {

				String loginFromDB = resultSet.getString("login");

				logins.add(loginFromDB);

			}

		} catch (SQLException e) {

			System.out.println("err");
			return false;
		}

		for (var l : logins) {
			if (l.equals(login)) {

				return false;
			}
		}

		return true;
	}

	public boolean authorization(String login, String password) {

		if (connection == null) {
			return false;
		}

		String query = "SELECT password FROM users WHERE login = \"" + login + "\";";

		String passwordFromDB;

		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			passwordFromDB = resultSet.getString("password");

		} catch (SQLException e) {

			return false;
		}

		return passwordFromDB.equals(password);
	}

	public String[] getHistory(String login) {

		if (connection == null) {
			return null;
		}

		String query = "SELECT history FROM users WHERE login = \"" + login + "\";";

		String history;

		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			history = resultSet.getString("history");

		} catch (SQLException e) {

			return null;
		}

		return history.split("#");

	}

	public boolean setHistory(String login, String request) {

		if (connection == null) {
			return false;
		}

		String query = "SELECT history FROM users WHERE login = \"" + login + "\";";

		String history;

		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			history = resultSet.getString("history");

			if (!"".equals(history)) {
				history += "#";
			}

			history += request;

			query = "UPDATE users SET history = \"" + history + "\" WHERE login = \"" + login + "\";";
			statement.executeUpdate(query);

		} catch (SQLException e) {

			return false;
		}

		return true;
	}
}
