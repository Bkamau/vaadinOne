package io.vaxly;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private Notification notification;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");
        name .addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                String text = name.getValue().toString();
                if (text.equals("ben")){
                    notification = new Notification("Did i see a BEN somewhere", "That word is not allowed");
                    notification.show(Page.getCurrent());
                }
            }
        });


        Button button = new Button("Click Me");
        button.addClickListener( e -> {

            notification = new Notification("Did i see a BEN somewhere", "That word is not allowed");

            if (name.getValue().contains("ben")){
                notification.show(Page.getCurrent());

            }else if (name.getValue().contains("benny")){
                layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
            }else{
                name.setComponentError(new UserError("Marine 1 or 2"));
            }

        });
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
