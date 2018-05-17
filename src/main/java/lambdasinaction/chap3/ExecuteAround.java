package lambdasinaction.chap3;

import java.io.*;

public class ExecuteAround {

    public static void main(String... args) throws IOException {
        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);
        System.out.println("---");
        String oneLine = processFile((BufferedReader b) -> b.readLine());
        System.out.println(oneLine);
        String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
        System.out.println(twoLines);
    }

    public static String processFileLimited() throws IOException {
        InputStream is = ExecuteAround.class.getClassLoader().getResourceAsStream("lambdasinaction/chap3/data.txt");
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(bis));
        return br.readLine();
    }


    public static String processFile(BufferedReaderProcessor tool) throws IOException {
        /* 获取资源 */
        InputStream is = ExecuteAround.class.getClassLoader().getResourceAsStream("lambdasinaction/chap3/data.txt");
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedReader fetchedResource = new BufferedReader(new InputStreamReader(bis));
        /* 用资源做事 */
        return tool.process(fetchedResource);
    }

    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }
}
