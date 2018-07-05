package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.view.ViewInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientGatherer extends Thread implements Iterable<ClientInfo>, ClientGathererInterface {
    // this object is used to synchronize the requests among clients that request to connect
    private Object lock;
    private ServerSocket serverSocket = null;
    // the list of clients that request to connect the game
    private ArrayList<ClientInfo> preLobby = new ArrayList<>();
    // the list of Socket Receiver related to that players that connect through Socket
    private ArrayList<SocketReceiver> socketReceivers = new ArrayList<>();

    // ports
    public static final int SOCKET_PORT = 1111;
    public static final int RMI_PORT = 1099;

    public ClientGatherer(Object lock) {
        this.lock = lock;
        try {
            // instance the ServerSocket
            serverSocket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // create the RMI registry and export the client gatherer in order to accept connections
            LocateRegistry.createRegistry(RMI_PORT);
            UnicastRemoteObject.exportObject(this, 0);
            Naming.rebind("//localhost:" + "/Server", this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        start();
    }

    /**
     * This method perform a connection with a RMI client.
     * @param localModel the remote reference to the LocalModel situated in the client
     * @param view the remote reference to the View situated in the client
     * @return the PlayerAction remote reference to the client
     */
    public PlayerActionInterface connectRMI(LocalModelInterface localModel, ViewInterface view) {
        // accept clients connections through RMI
        PlayerActionInterface paInterface = null;
        synchronized (lock) {
            // create the PlayerAction instance in order to perform the player's requests
            PlayerAction pa = new PlayerAction(lock);
            try {
                // create the remote reference of the PlayerAction
                paInterface = (PlayerActionInterface) UnicastRemoteObject.exportObject(pa, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            // add the client to the prelobby
            preLobby.add(new ClientInfo(localModel, view, pa));
        }
        // return the PlayerAction instance to the client
        return paInterface;
    }

    public Iterator<ClientInfo> iterator() {
        return preLobby.iterator();
    }

    @Override
    public void run() {
        Socket socket = null;
        while(true) {
            try {
                // accept clients connections through Socket
                socket = serverSocket.accept();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            // create the PlayerAction instance in order to perform the player's requests
            PlayerAction pa = new PlayerAction(lock);
            // create the SocketReceiver in order to write the information from the client
            SocketReceiver socketReceiver = new SocketReceiver(socket);
            socketReceiver.setPlayerAction(pa);
            socketReceiver.start();
            SocketLocalModel localModel = new SocketLocalModel(socket);
            SocketView view = new SocketView(socket);
            synchronized (lock) {
                preLobby.add(new ClientInfo(localModel, view, pa));
            }
            Iterator<SocketReceiver> iterator = socketReceivers.iterator();
            while(iterator.hasNext()) {
                if(iterator.next().isInterrupted()) {
                    iterator.remove();
                }
            }
            socketReceivers.add(socketReceiver);
        }
    }
}
