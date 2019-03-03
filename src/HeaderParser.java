import java.lang.reflect.Array;
import java.util.ArrayList;

public class HeaderParser {
    private String header;
    private ArrayList<String> signals;

    public HeaderParser(String header) {
        this.header = header;
        this.signals = new ArrayList<>();
    }

    public HeaderParser() {
    }

    ;

    public void load(String header) {
        this.header = header;
    }

    // lowercase
    public static String sanitize(String string) {
        String newString = string.toLowerCase()
                .replaceAll("\n", "")
                .replaceAll("\\[\\d+\\:\\d\\]", "")
                .replaceAll(",", "")
                .replaceAll(" +", " ");
        System.out.println(newString);
        return newString;
    }

    public ArrayList<String> getInputs() {
        ArrayList<String> inputs = new ArrayList<>();
        String[] signals = this.sanitize(this.header).split(" ");

        boolean readInputs = false;
        for (int i = 1; i < signals.length; i++) {
            if (i == 1){
                inputs.add(signals[i]);
            }
            else if (signals[i].equals("input")){
                readInputs = true;
                i++;
            }
            else if (signals[i].equals("output")){
                readInputs = false;
            }
            if (readInputs){
                inputs.add(signals[i]);
            }
        }
        return inputs;
    }

}
