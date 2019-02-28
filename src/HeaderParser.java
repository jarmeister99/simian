import java.lang.reflect.Array;
import java.util.ArrayList;

public class HeaderParser {
    private String header;

    public HeaderParser(String header) {
        this.header = header;
    }

    public HeaderParser() {
    }

    ;

    public void load(String header) {
        this.header = header;
    }

    // remove "MODULE" and next term after module
    // remove [x:y]
    // remove "reg"
    // remove "(", ")", ";"
    // remove "output" and the next term(s)
    // remove "input"
    // remove "\n"
    // lowercase
    private String sanitize(String string) {
        String newString = string.toLowerCase().replaceAll("\\[\\d\\:\\d\\]", "")
                .replaceAll("reg|input", "")
                .replaceAll("[();]", "")
                .replaceAll("module\\s\\w+", "")
                .replaceAll("output\\s+\\w+", "")
                .replaceAll("[ \n]", "")
                .toLowerCase();
        return newString;
    }

    public ArrayList<String> findInputs() {
        ArrayList<String> inputs = new ArrayList<String>();

        for (String s : this.sanitize(this.header).split(",")){
            inputs.add(s);
        }
        return inputs;
    }

}
