import components.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CodeWriter {
    private LinkedHashMap<String, Integer> signals;
    private String moduleName;
    private ArrayList<TestCase> cases;

    public CodeWriter(LinkedHashMap<String, Integer> signals, String moduleName, ArrayList<TestCase> cases;){
        this.signals = signals;
        this.moduleName = moduleName;
        this.cases = cases;
    }

    // TODO: replace this because this is stupid. Have a template file and then replace lines as needed
    public String generateCode(){
        String code = "";
        code += ("module " + signals.get(0) + "();\n");
        
        return code;
    }

}
