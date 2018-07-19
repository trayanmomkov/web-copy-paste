package info.trekto.webcopypaste;

public class CopyPasteBean {

    private String input;
    private StringBuilder history;

    public CopyPasteBean() {
        history = new StringBuilder();
        input = "";
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getHistory() {
        return history.toString();
    }

    public void addToHistory(String history) {
        this.history.append(history);
    }
}
