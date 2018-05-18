package lambdasinaction.chap3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppActionsWithResource {

    public static void main(String... args) throws IOException {
        processFile(a -> a + "apptec");
    }

    public static String processFile(BufferedReaderProcessor tool) throws IOException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        for (String s : list) {
            System.out.println(tool.process(s));
        }

        return "";
    }

    public interface BufferedReaderProcessor {
        String process(String b) throws IOException;
    }
}
