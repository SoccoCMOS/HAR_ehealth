package dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities;

        import java.io.BufferedWriter;
        import java.io.FileWriter;
        import java.util.List;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;;

public class CSVFile {

    InputStream inputStream;
    List<String[]> resultList;
    List<double[]> doubletList;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void read() {
        resultList = new ArrayList<String[]>();
        doubletList = new ArrayList<double[]>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                doubletList.add(convert(row));
                resultList.add(row);
            }

        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
    }

    private double[] convert(String[] string) {
        double number[] = new double[string.length];

        for (int i = 0; i < string.length; i++) {
            number[i] = Double.parseDouble(string[i]); //
        }
        return number;
    }

    public String[] getItem(int index) {
        return this.resultList.get(index);
    }

    public List<String[]> getResultList() {
        return resultList;
    }

    public void setResultList(List<String[]> resultList) {
        this.resultList = resultList;
    }

    public List<double[]> getDoubletList() {
        return doubletList;
    }

    public List<double[]> ReadfromTo(int pos, int length) {

        List<double[]> window = new ArrayList<double[]>();
        if (pos + length <= doubletList.size()) {
            for (int i = 0; i < length; i++)
                window.add(this.doubletList.get(pos + i));
        } else {
            for (int i = 0; i < (doubletList.size() - pos); i++)
                window.add(this.doubletList.get(pos + i));
        }

        return window;
    }

    public static void writeCSVfile(String filename, double[] result) throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter(filename, true));
        StringBuilder sb = new StringBuilder();
        for (double element : result) {
            sb.append(element);
            sb.append("\t");
        }

        sb.append("\n");
        br.write(sb.toString());
        br.close();
    }
}
