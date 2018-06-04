package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;

public class VirtualView {
    private Model model = Model.instance();
    public VirtualView() {
    }

    public void removeClient(int i) {
    }

    public boolean checkConnection(int i) {
        //TODO: check views connections
        return true;
    }
}
