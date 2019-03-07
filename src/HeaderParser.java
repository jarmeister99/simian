import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HeaderParser {
    private String header;
    private LinkedHashMap<String, Integer> signals;
    private String moduleName;

    public HeaderParser() {
        this.signals = new LinkedHashMap<String, Integer>();
    }

    public HeaderParser(String header) {
        this();
        this.header = header;
    }

    public void load(String header) {
        this.header = header;
        this.getInputs();
    }

    public LinkedHashMap<String, Integer> getSignals(){
        return this.signals;
    }
    public String getModuleName(){
        return this.moduleName;
    }

    // lowercase
    private String sanitize(String string) {
        String newString = string.toLowerCase()
                .replaceAll("[();]", "")
                .replaceAll("\n", "")
                .replaceAll("\\[", "")
                .replaceAll(":0]", "")
                .replaceAll(",", "")
                .replaceAll(" +", " ");
        return newString;
    }

    private void getInputs() {
        this.signals.clear();
        String[] headerInfo = this.sanitize(this.header).split(" ");
        if (headerInfo.length == 1){
            return;
        }
        this.moduleName = headerInfo[1];

        boolean saveEnable = false;
        int bitWidth = 1;
        for (int i = 1; i < headerInfo.length; i++){
            if (headerInfo[i].equals("input")){
                bitWidth = 1;
                saveEnable = true;
            }
            else if (headerInfo[i].equals("output")){
                saveEnable = false;
            }
            else if (headerInfo[i].matches("\\d+")){
                bitWidth = Integer.parseInt(headerInfo[i]) + 1;
            }
            else{
                if (saveEnable){
                    this.signals.put(headerInfo[i], bitWidth);
                }
            }
        }
    }

}
