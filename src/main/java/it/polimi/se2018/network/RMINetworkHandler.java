package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.ViewInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;

public class RMINetworkHandler extends NetworkHandler {

    private final int PORT = 1099;

    public RMINetworkHandler(String host) {
        super(host);
    }

    public PlayerActionInterface connect(LocalModel localModel, View view) {
        ClientGathererInterface clientGatherer;
        LocalModelInterface localModelInterface;
        ViewInterface viewInterface;
        PlayerActionInterface playerActionInterface = null;
        try {
            clientGatherer = (ClientGathererInterface)Naming.lookup("//" + host + ":" + PORT + "/Server");
            localModelInterface = (LocalModelInterface)UnicastRemoteObject.exportObject(localModel, 0);
            viewInterface = (ViewInterface)UnicastRemoteObject.exportObject(view, 0);
            playerActionInterface = clientGatherer.connectRMI(localModelInterface, viewInterface);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerActionInterface;
    }
}
