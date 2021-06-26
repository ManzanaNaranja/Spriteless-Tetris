package game;

public class StringFormatter {
	public static String formatString(String s) {
		int len = s.length();
		if(len > 6) return null;
		int space = 6 - len;
		for(int i = 0; i < space; i++) {
			s = " " + s;
		}
		return s;
	}
}
