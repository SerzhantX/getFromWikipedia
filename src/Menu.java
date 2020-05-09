public class Menu {

	private static boolean isCreate = false;

	public static boolean createMenu() {

		if (isCreate) {

			return false;
		}


		return true;
	}

	private Menu() {

	}
}
