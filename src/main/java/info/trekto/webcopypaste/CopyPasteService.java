package info.trekto.webcopypaste;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

class CopyPasteService extends Observable {
    private static final CopyPasteService INSTANCE = new CopyPasteService();
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CopyPasteService() {
    }

    public static CopyPasteService getInstance() {
        return INSTANCE;
    }

    private CopyPasteBean bean = new CopyPasteBean();
    private Collection<Observer> observers = new ArrayList<>();

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
        bean.addToHistory("\n================\n" + TIMESTAMP_FORMAT.format(new Date()) + ":\t\t" + bean.getInput());
        bean.setInput("");
        for (Observer observer : observers) {
            observer.update(this, bean);
        }
    }

    public String getInput() {
        return bean.getInput();
    }

    public String getHistory() {
        return bean.getHistory();
    }
}
