package util;

public class Converter {
	public Converter() {
	}

	public static String firstLowerCase(String value) {
		String first = value.substring(0, 1);
		return first.toLowerCase() + value.substring(1);
	};
}
