import components.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CodeWriter {
    private LinkedHashMap<String, Integer> signals;
    private String moduleName;
    private ArrayList<TestCase> cases;

    public CodeWriter(LinkedHashMap<String, Integer> signals, String moduleName, ArrayList<TestCase> cases){
        this.signals = signals;
        this.moduleName = moduleName;
        this.cases = cases;
    }

    // TODO: replace this because this is stupid. Have a template file and then replace lines as needed
    public String generateCode(){
        String code = "";
        code += ("module " + this.moduleName + "();\n");

        for (String signal : this.signals.keySet()){
            if (this.signals.get(signal) > 1){
                code += ("logic [" + this.signals.get(signal) + "0:] " + signal + ";\n");
            }
            else{
                code += ("logic " + signal + ";\n");
            }
        }

        code += (this.moduleName + " UUT " + "(");
        for (String signal : this.signals.keySet()){
            code += ("." + signal + "(" + signal + "),");
        }
        code = (code.substring(0, code.length() - 1) + ");\n");
        code += "initial begin\n";
        for (String signal : this.signals.keySet()){
            code += ("\t" + signal + " <= 0\n");
        }
        code += "end\n";

        // TODO: Make this a bit more 'dynamic'
        code += "always #10 CLK <= !CLK\n";

        code += "initial begin\n";
        for (TestCase tc : this.cases){
            code += tc + ";\n";
        }
        code += "end\n";
        code += "endmodule\n";
        return code;
    }

}
