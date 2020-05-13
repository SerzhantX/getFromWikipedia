import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;


public class getInfoFromWiki {

    public String getInfo(String request) {

        String body = "";

        try {



            URL url = new URL("https://ru.wikipedia.org/wiki/" + replaceSpace(request));

            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                String string = reader.readLine();

                while (string != null) {
                    body += string;
                    string = reader.readLine();
                }

                reader.close();

            } catch (IOException e) {

                return "Страница не найдена";
            }

        } catch (MalformedURLException ex) {

            return "Страница не найдена";
        }

        return parse(body);
    }

    private String parse(String input) {

        int startIndex = input.indexOf("<p><b>") + 5;
        int endIndex = input.indexOf("</p><div") + 1;

        input = input.substring(startIndex, endIndex);

        //boolean flag = false;
        String result = "";

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '>') {
                startIndex = i + 1;
            }

            if (input.charAt(i) == '<') {
                result += cutCode(input.substring(startIndex, i));
            }
        }

        return formatString(result);
    }

    private String cutCode(String input) {

        int startIndex = 0;

        boolean flag = false;
        String result = "";

        for (int i = 1; i < input.length(); i++) {

            if (input.charAt(i) == '#' && input.charAt(i - 1) == '&') {
                result += input.substring(startIndex, i - 1) + " ";
                flag = true;
            }

            if (input.charAt(i) == ';') {
                flag = false;
                startIndex = i + 1;
            }
        }

        result += input.substring(startIndex, input.length());

        return result;
    }

    private String formatString(String input) {

        int count = 0;
        int pos = 0;

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == ' ') {

                pos = i;
            }

            count++;

            if (count == 75) {

                count = i - pos;

                input = input.substring(0, pos) + "\n" + input.substring(pos + 1);
            }
        }

        return input;
    }

    private String replaceSpace(String input) {

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == ' ') {

                input = input.substring(0, i) + "_" + input.substring(i + 1);
            }

        }

        return input;
    }
}
