package JavaUnser;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;

public class testRMI {
    public static void main(String args[])throws Exception{
        //用于演示rmi的危险性
        //System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase","true");
        Context ctx=new InitialContext();
        ctx.lookup("rmi://127.0.0.1:3456/Object");
        System.out.println("loaded obj");
    }
}
