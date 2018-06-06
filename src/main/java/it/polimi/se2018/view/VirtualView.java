package it.polimi.se2018.view;

import java.util.ArrayList;

public class VirtualView {
    ArrayList<ViewInterface> views = new ArrayList<>();

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

    public boolean checkConnection(int i) {
        //TODO: check views connections
        return true;
    }
}
