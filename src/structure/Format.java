package structure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {

	public static String protocolo(String protocolo) {
		protocolo = protocolo.replace(".", "").replace("-", "").replace("/", "").trim();

		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{5})(\\d{4})(\\d{1})");
		Matcher m = pattern.matcher(protocolo);
		m.matches();

		String a = m.group(1);
		String b = m.group(2);
		String c = m.group(3);
		String d = m.group(4);
		String e = m.group(5);

		return String.format("%s.%s.%s/%s-%s", a, b, c, d, e);
	}
	
}
