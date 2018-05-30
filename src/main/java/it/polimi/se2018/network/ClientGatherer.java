package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.utils.Timer;
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

public class ClientGatherer extends Thread implements ClientGathererInterface {
    private ServerSocket serverSocket;
    private ArrayList<ClientInfo> preLobby = new ArrayList<>();
    private ArrayList<SocketReceiver> socketReceivers = new ArrayList<>();
    private Timer timer = new Timer();

    private static int SOCKET_PORT = 1111;
    private static int RMI_PORT = 1099;

    public ClientGatherer() {
        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            LocateRegistry.createRegistry(RMI_PORT);
            Naming.rebind("//localhost/Server", this);
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public synchronized PlayerActionInterface connectRMI(LocalModelInterface localModel, ViewInterface view) {
        PlayerAction pa = new PlayerAction();
        PlayerActionInterface paInterface = null;
        try {
            paInterface = (PlayerActionInterface) UnicastRemoteObject.exportObject(pa, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        preLobby.add(new ClientInfo(localModel, view, pa));
        notifyAll();
        //socketReceivers.add(null);
        return paInterface;
    }

    public boolean isEmpty() {
        return preLobby.isEmpty();
    }

    public Iterator<ClientInfo> getIterator() {
        return preLobby.iterator();
    }

    public synchronized void remove(ClientInfo clientInfo) {
        preLobby.remove(clientInfo);
    }

    @Override
    public void run() {
        Socket socket = null;
        while(true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PlayerAction pa = new PlayerAction();
            SocketReceiver socketReceiver = new SocketReceiver(socket);
            socketReceiver.setPlayerAction(pa);
            socketReceiver.start();
            SocketLocalModel localModel = new SocketLocalModel(socket);
            SocketView view = new SocketView(socket);
            synchronized (this) {
                preLobby.add(new ClientInfo(localModel, view, pa));
                notifyAll();
            }
            //TODO: remove unused socket receiver
            socketReceivers.add(socketReceiver);
        }
    }
}
