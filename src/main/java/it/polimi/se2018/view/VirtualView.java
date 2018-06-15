package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;

public class VirtualView implements Observer {
    ArrayList<ViewInterface> views = new ArrayList<>();
    Model model = Model.instance();

    public VirtualView() {

    }

    public void addClient(ViewInterface view) {
        views.add(view);
    }

    public void removeClient(int i) {
        views.remove(i);
    }

    public void reinsertClient(int i, ViewInterface view) {
        views.set(i, view);
    }

    public ViewInterface getView(int i) {
        return views.get(i);
    }

    public void update() {
        //TODO: update views from model
    }

    public boolean checkConnection(int i) {
        //TODO: check views connections
        return true;
    }
}
