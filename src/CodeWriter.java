import java.util.ArrayList;
import java.util.HashMap;

public class CodeWriter {
    private ArrayList<String> signals;

    public CodeWriter(ArrayList<String> signals){
        this.signals = signals;
        this.generateCode();
    }

    // TODO: replace this because this is stupid. Have a template file and then replace lines as needed
    public String generateCode(){
        String code = "";
        code += ("module " + signals.get(0) + "();\n");

        return code;
    }

}
