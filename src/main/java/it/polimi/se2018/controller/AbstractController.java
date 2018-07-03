package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;

public abstract class AbstractController {
    private ModelInterface modelInterface;

    public AbstractController(ModelInterface modelInterface) {
        this.modelInterface = modelInterface;
    }

    protected abstract int getPlayerIndex(PlayerAction pa);

    private boolean wfRangeCheck(int[] array){
        if(array.length == 2){
            return (array[0] >= 0 && array[0] <= 3 && array[1] >= 0 && array[1] <= 4);
        }
        else return false;
    }

    private boolean emptyWFPlaceCheck(int playerIndex, int[] array){
        if(array.length == 2){
            return (modelInterface.getWindowFrame(playerIndex).getDie(array[0], array[1]) == null);
        }
        else return false;
    }

    protected boolean validAction(PlayerAction pa) {
        int playerIndex = getPlayerIndex(pa);
        if(pa.isSwitchConnReq()) {
            return true;
        }
        if(!modelInterface.isStarted() && !modelInterface.isLobbyGathering()) {
            if(!(pa.getPatternCard() >= 0 && pa.getPatternCard() <= 3)) {
                return false;
            }
            return !modelInterface.playerHasChosenPC(playerIndex);
        }
        if(playerIndex + 1 != modelInterface.getTurn()) {
            return false;
        }
        if(pa.isSkipTurn()) {
            return true;
        }
        if(!(pa.getIdToolCard()>=0 && pa.getIdToolCard()<=3)){
            return false;
        }
        for(int dieFace : pa.getNewDieValue()){
            if(!(dieFace >= 1 && dieFace <= 6)){
                return false;
            }
        }
        //Check for the position in the Draft Pool
        for(int value : pa.getPosDPDie()){
            if(!(value >= 0 && value < modelInterface.getDraftPoolSize())){
                return false;
            }
        }
        for(int[] posRT : pa.getPosRTDie()){
            if(!(posRT[0] >= 1
                    && posRT[0] < modelInterface.getRound()
                    && posRT[1] >= 0
                    && posRT[1] < modelInterface.getRoundTrackSize(posRT[0]))){
                return false;
            }
        }
        for(int[] array: pa.getPlaceDPDie()){
            if(!wfRangeCheck(array)){
                return false;
            }
        }
        if(pa.getPlaceWFDie().size() != pa.getPlaceNewWFDie().size()) {
            return false;
        }
        for(int[] array: pa.getPlaceWFDie()){
            if(!wfRangeCheck(array)
                    && !emptyWFPlaceCheck(playerIndex, array)){
                return false;
            }
        }
        //Afterward the verification of the future placement, this code portion verifies if parameters are legal.
        for(int[] array: pa.getPlaceNewWFDie()){
            if(!wfRangeCheck(array)){
                return false;
            }
        }
        if(pa.getIdToolCard() > 3) {
            return false;
        }
        if(modelInterface.isStarted()) {
            WindowFrame wf = modelInterface.getWindowFrame(playerIndex);
            if((pa.getIdToolCard() > 0)
               && modelInterface.playerCanUseToolCard(playerIndex, pa.getIdToolCard())) {
                // turn using tool card
                ToolCard toolCard = modelInterface.getToolCard(pa.getIdToolCard());
                return toolCard.validAction(modelInterface, wf, pa);
            }
            else if(ToolCard.isPendingAction()) {
                ToolCard toolCard = ToolCard.getPendingToolCard();
                return toolCard.validAction(modelInterface, wf, pa);
            }
            else if(!pa.getPosDPDie().isEmpty() && !pa.getPlaceDPDie().isEmpty()) {
                // regular turn
                if(pa.getPosDPDie().get(0) >= 0){
                    Die die = modelInterface.getDraftPoolDie(pa.getPosDPDie().get(0));
                    int[] wfPlace = pa.getPlaceDPDie().get(0);
                    return (wfRangeCheck(pa.getPlaceDPDie().get(0))
                            && wf.checkRestrictions(die, wfPlace[0], wfPlace[1]));
                }
                else return false;
            }
            else {
                return false;
            }
        }
        return true;
    }
}
