package store.util;

import java.io.*;
import java.util.List;

public class FileUtil {

    public List<String> readFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            validatedIsFile(inputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines()
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 도중 오류가 발생했습니다.");
        }
    }

    private void validatedIsFile(InputStream inputStream) throws FileNotFoundException {
        if (inputStream == null) {
            throw new FileNotFoundException("존재하지 않는 파일입니다.");
        }
    }
}
