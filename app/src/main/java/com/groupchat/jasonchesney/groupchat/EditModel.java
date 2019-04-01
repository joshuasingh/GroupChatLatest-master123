package com.groupchat.jasonchesney.groupchat;

public class EditModel {

    String newgrouptitle;

    public EditModel() {}

    public EditModel(String newgrouptitle) {
        this.newgrouptitle = newgrouptitle;
        //this.oldgrouptitle = oldgrouptitle;
    }

    public String getNewgrouptitle()
    {
        return newgrouptitle;
     }

    public void setNewgrouptitle(String newgrouptitle)
    {
        this.newgrouptitle = newgrouptitle;
    }

//    public String getOldgrouptitle() {
//        return oldgrouptitle;
//    }
//
//    public void setOldgrouptitle(String oldgrouptitle) {
//        this.oldgrouptitle = oldgrouptitle;
//    }
}
