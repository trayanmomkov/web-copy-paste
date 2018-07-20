/*
  Trayan Momkov 2018
 */

package info.trekto.webcopypaste;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

class CopyPasteService extends Observable {
    private static final CopyPasteService INSTANCE = new CopyPasteService();

    public static CopyPasteService getInstance() {
        return INSTANCE;
    }

    private CopyPasteBean bean;
    private Collection<Observer> observers;

    private CopyPasteService() {
        bean = new CopyPasteBean();
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void inputChanged(String text) {
        bean.setInput(text);
        for (Observer observer : observers) {
            observer.update(this, bean);
        }
    }

    public void clearPushed() {
        bean.setInput("");
        for (Observer observer : observers) {
            observer.update(this, bean);
        }
    }

    public String getInput() {
        return bean.getInput();
    }
}
