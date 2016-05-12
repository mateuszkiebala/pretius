import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "Plik z danymi.txt";
        if (args.length != 0) {
            fileName = args[0];
        }

        BufferedReader bufferReader = null;
        try {
            FileReader filePath = new FileReader(fileName);
            bufferReader = new BufferedReader(filePath);
            String line;
            BigDecimal sum = new BigDecimal(0);
            while ((line = bufferReader.readLine()) != null) {
                HashMap<String, String> transMap = new HashMap<String, String>();
                for (String s : line.split("@")) {
                    String[] data = s.split(":");
                    if (data.length == 2)
                        transMap.put(data[0], data[1]);
                }

                if (transMap.containsKey("amount")) {
                    String amount = transMap.get("amount");
                    Pattern p = Pattern.compile("[0-9]*\\,?[0-9]+");
                    Matcher m = p.matcher(amount);
                    String money = "0";
                    if (m.find()) {
                        money = m.group();
                        money = money.replace(',', '.');
                    }
                    BigDecimal value = new BigDecimal(money);
                    sum = sum.add(value);
                }
            }
            System.out.println("Wynik: " + sum.setScale(2, BigDecimal.ROUND_HALF_UP) + " PLN");
        } catch (Exception e) {
            System.out.println("Error while reading file: " + e.getMessage());
        } finally {
            if (bufferReader != null)
                bufferReader.close();
        }
    }
}