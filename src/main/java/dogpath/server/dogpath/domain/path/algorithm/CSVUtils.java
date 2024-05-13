package dogpath.server.dogpath.domain.path.algorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVUtils {
    public static int createCSV(List nodes) {
        int resCnt = 0;
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter("./temp.csv", StandardCharsets.UTF_8, false));

            fw.write("latitude, longitude");
            fw.newLine();
            fw.flush();

            for (Object node : nodes) {
                Node next = (Node) node;

                fw.write(next.centerPoint.x + "," + next.centerPoint.y);
                fw.newLine();
                resCnt++;

                fw.flush();
            }
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resCnt;
    }
}
