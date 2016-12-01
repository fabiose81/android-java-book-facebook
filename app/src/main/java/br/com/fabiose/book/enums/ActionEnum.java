package br.com.fabiose.book.enums;

/**
 * Created by fabioestrela on 30/11/16.
 */

public enum ActionEnum {

    L("List"), A("Add"), D("Delete");

    private ActionEnum(String action) {
        this.action = action;
    }

    private final String action;

    public String getAction() {
        return action;
    }


    @Override
    public String toString(){
        return action;
    }
}
