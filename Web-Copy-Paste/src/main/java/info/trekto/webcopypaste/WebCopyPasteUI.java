package info.trekto.webcopypaste;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class WebCopyPasteUI extends UI implements Observer {

    private TextArea inputTextArea;
    private Link link;
    private TextArea historyTextArea;
    private CopyPasteService service = CopyPasteService.getInstance();

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = WebCopyPasteUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final GridLayout mainLayout = new GridLayout(2, 10);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        expand(mainLayout);
        setContent(mainLayout);
        Page.getCurrent().setTitle("WEB COPY PASTE");

        mainLayout.addComponent(buildButtonsLayout(), 0, 0, 0, 0);
        mainLayout.addComponent(buildInputLayout(), 0, 1, 0, 9);
        mainLayout.addComponent(buildOutputLayout(), 1, 0, 1, 9);
        service.addObserver(this);
    }

    private Component buildButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        Button clearButton = new Button("Clear");
        clearButton.addClickListener(event -> service.clearPushed());
        layout.addComponent(clearButton);
        layout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        layout.setComponentAlignment(clearButton, Alignment.BOTTOM_LEFT);

        layout.setMargin(false);
        layout.setSpacing(false);
        return layout;
    }

    private Component buildInputLayout() {
        inputTextArea = new TextArea("Input");
        expand(inputTextArea);
        inputTextArea.addValueChangeListener(event -> service.inputChanged(event.getValue()));
        inputTextArea.addFocusListener(event -> inputTextArea.selectAll());

        VerticalLayout layout = new VerticalLayout();
        expand(layout);
        layout.addComponent(inputTextArea);
        layout.setMargin(false);
        layout.setSpacing(false);
        return layout;
    }

    private Component buildOutputLayout() {
        link = new Link();
        expand(link);

        historyTextArea = new TextArea("History");
        expand(historyTextArea);
        historyTextArea.setReadOnly(true);

        GridLayout layout = new GridLayout(1, 9);
        expand(layout);
        layout.addComponent(link, 0, 0, 0, 0);
        layout.addComponent(historyTextArea, 0, 1, 0, 8);
        layout.setMargin(false);
        layout.setSpacing(false);
        return layout;
    }

    @Override
    public void update(Observable o, Object arg) {
        inputTextArea.setValue(service.getInput());
        try {
            URL url = new URL(inputTextArea.getValue());
            link.setCaption(url.toString());
            link.setResource(new ExternalResource(url));
        } catch (MalformedURLException ex) {
            link.setCaption("");
            link.setResource(null);
        }

        historyTextArea.setValue(service.getHistory());
    }

    private void expand(Component component) {
        component.setWidth(100, Unit.PERCENTAGE);
        component.setHeight(100, Unit.PERCENTAGE);
    }
}
