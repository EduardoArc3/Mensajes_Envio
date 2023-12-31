package msp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class UserManager {

    public static final String CLASS_NAME = UserManager.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    private static final int MAX_MESSAGE_LENGTH = 500;
    private HashMap<String, Socket> connections;

    public UserManager() {
        super();
        connections = new  HashMap<String, Socket>();
    }

    public boolean connect(String user, Socket socket) {
        boolean result = true;

       Socket s = connections.put(user,socket);
       if( s != null) {
           result = false;
       }
       return result;
    }

    public Socket get(String user) {

        Socket s = connections.get(user);

        return s;
    }

    public void send(String message) {

        Collection<Socket> conexiones = connections.values();

        for( Socket s: conexiones) {
            try {
                PrintWriter output = new PrintWriter(s.getOutputStream(), true);
                output.println(message);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
                e.printStackTrace();
            }
        }

    }


    public String obtenerListaUsuario() {
        Set<String> ListaUsuarios = connections.keySet();
        return String.join(", ",ListaUsuarios);

    }

    public boolean disconnect(String userName) {
        if (connections.containsKey(userName)) {
            connections.remove(userName);
        return true;
    }
        return false;
    }
}
