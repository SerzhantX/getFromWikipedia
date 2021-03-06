import java.util.Scanner;

public class UserInterface {

	private static UserInterface userInterface;

	public static synchronized UserInterface getInstance() {

		if (userInterface == null) {
			userInterface = new UserInterface();
		}

		return userInterface;
	}

	private DataBase dataBase;

	private UserInterface() {

		dataBase = DataBase.getInstance();

		wikipedia = new Wikipedia();
	}

	private boolean isAuthorization = false;

	private String login;

	private Wikipedia wikipedia;

	private void searchOnWikipedia() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Введите запрос: ");
		String query = scanner.nextLine();

		if ("".equals(query)) {
			System.out.println("Вы не ввели запрос");
			return;
		}

		String response = wikipedia.getInfo(query);

		if (!dataBase.setHistory(login, query)) {
			System.out.println("Ошибка с БД");
		}

		System.out.println(response);
	}

	private void printHistory() {

		String[] history = dataBase.getHistory(login);

		for (var h : history) {

			System.out.println(h);
		}
	}

	private boolean menu() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("1 - Поиск");
		System.out.println("2 - История поиска");
		System.out.println("3 - Выйти");
		System.out.println("4 - Закрыть программу");

		String choice = scanner.nextLine();

		switch (choice) {
			case "1":
				searchOnWikipedia();
				break;
			case "2":
				printHistory();
				break;
			case "3":
				isAuthorization = false;
				break;
			case "4":
				return false;
			default:
				System.out.println("Некорректная команда");
		}

		return  true;
	}

	private void authorization() {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.print("Логин: ");
			login = scanner.nextLine();

			if ("".equals(login)) {

				System.out.print("Логин не должен быть пустым");
				continue;
			}

			System.out.print("Пароль: ");
			String password = scanner.nextLine();

			if (dataBase.authorization(login, password)) {

				break;
			}

			System.out.println("Данного пользователя нет в базе");

			return;
		}

		System.out.println("Вы успешно вошли");

		isAuthorization = true;
	}

	private void registration() {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.print("Придумайте логин: ");
			login = scanner.nextLine();

			if ("".equals(login)) {

				System.out.println("Логин не должен быть пустым");
				continue;
			}

			if (dataBase.checkLogin(login)) {

				break;
			}

			System.out.println("Пользователь с таким логином уже есть в базе");
		}

		System.out.print("Придумайте пароль: ");
		String password = scanner.nextLine();

		if (!dataBase.addNewUser(login, password)) {

			System.out.println("Ошибка регистрации");

			return;
		}

		System.out.println("Вы успешно зарегистрировались");

		isAuthorization = true;
	}

	private boolean startMenu() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("1 - Войти");
		System.out.println("2 - Зарегистрироваться");
		System.out.println("3 - Закрыть программу");

		String choice = scanner.nextLine();

		switch (choice) {
			case "1":
				authorization();
				break;
			case "2":
				registration();
				break;
			case "3":
				return false;
			default:
				System.out.println("Некорректная команда");
		}

		return true;
	}

	public boolean printInterface() {

		if (!isAuthorization) {
			return startMenu();
		}

		return menu();
	}
}
