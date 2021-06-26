package game;

public class CharacterConverter {
	private static String oldchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String newchars = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ";
	
	private static String oldnums = "0123456789";
	private static String newnums = "０１２３４５６７８９";
	
	public static String convert(String data) {
		data = data.toUpperCase();
		String newdata = "";
		for(int i = 0; i < data.length(); i++) {
			String a = data.substring(i,i+1);
			int index = oldchars.indexOf(a);
			if(index == -1) return null;
			newdata += newchars.substring(index,index+1);
		}
		return newdata;
	}
	
	public static String convertNums(String data) {
		data = data.toUpperCase();
		String newdata = "";
		for(int i = 0; i < data.length(); i++) {
			String a = data.substring(i,i+1);
			int index = oldnums.indexOf(a);
			if(index == -1) return null;
			newdata += newnums.substring(index,index+1);
		}
		return newdata;
	}
	
	public static String decode(String data) {
		data = data.toUpperCase();
		String newdata = "";
		for(int i = 0; i < data.length(); i++) {
			String a = data.substring(i,i+1);
			int index = newchars.indexOf(a);
			if(index == -1) return null;
			newdata += oldchars.substring(index,index+1);
		}
		return newdata;
	}
	
	public static String decodeNums(String data) {
		data = data.toUpperCase();
		String newdata = "";
		for(int i = 0; i < data.length(); i++) {
			String a = data.substring(i,i+1);
			int index = newnums.indexOf(a);
			if(index == -1) return null;
			newdata += oldnums.substring(index,index+1);
		}
		return newdata;
	}
}
