import java.util.Scanner;

public class UserInterface {

	private static UserInterface userInterface;

	public static synchronized UserInterface getInstance() {

		if (userInterface == null) {
			userInterface = new UserInterface();
		}

		return userInterface;
	}

	private UserInterface() {

	}

	private boolean isAuthorization = false;

	private String login;

	private boolean menu() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("1 - Поиск");
		System.out.println("2 - История поиска");
		System.out.println("3 - Выйти");
		System.out.println("4 - Закрыть программу");

		while (true) {

			String choice = scanner.nextLine();

			if ("1".equals(choice)) {

				System.out.println("Введите запрос: ");
				String query = scanner.nextLine();

				//////////////////////////////
				/// Поиск запроса на википедии
				//////////////////////////////

				break;
			}

			if ("2".equals(choice)) {

				////////////////////////
				/// Вывод истории поиска
				////////////////////////

				break;
			}

			if ("3".equals(choice)) {

				isAuthorization = false;
				break;
			}

			if ("4".equals(choice)) {

				return false;
			}

			System.out.println("Некорректная команда");
		}

		return  true;
	}

	private void authorization() {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.print("Логин: ");
			login = scanner.nextLine();

			System.out.print("Пароль: ");
			String password = scanner.nextLine();

			///////////////////////////////
			/// проверка авторизации в базе
			///////////////////////////////

			break;

			//System.out.println("Пользователя не существует");
		}

		System.out.println("Вы успешно вошли");

		isAuthorization = true;
	}

	private void registration() {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("Придумайте логин: ");
			login = scanner.nextLine();

			//////////////////////////
			/// проверка логина в базе
			//////////////////////////

			break;

			//System.out.println("Пользователь с таким логином уже есть в базе");
		}

		////////////////////////////////
		/// внесение пользователя в базу
		////////////////////////////////

		System.out.println("Придумайте пароль: ");
		String password = scanner.nextLine();

		System.out.println("Вы успешно зарегистрировались");

		isAuthorization = true;
	}

	private boolean startMenu() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("1 - Войти");
		System.out.println("2 - Зарегистрироваться");
		System.out.println("3 - Закрыть программу");

		while (true) {

			String choice = scanner.nextLine();

			if ("1".equals(choice)) {

				authorization();
				break;
			}

			if ("2".equals(choice)) {

				registration();
				break;
			}

			if ("3".equals(choice)) {

				return false;
			}

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
