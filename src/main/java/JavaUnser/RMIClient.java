package JavaUnser;

import JavaUnser.RMIServer;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
public class RMIClient {
    public static void main(String[] args) throws Exception {
        RMIServer.IRemoteHelloWorld hello = (RMIServer.IRemoteHelloWorld)
                Naming.lookup("rmi://localhost:1099/Hello");
        String ret = hello.hello();
        System.out.println( ret);
    }
}
