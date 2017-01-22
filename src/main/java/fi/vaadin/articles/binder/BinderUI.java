package fi.vaadin.articles.binder;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PropertyId;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The example UI, containing data input form.
 */
public class BinderUI extends UI {
    // Field that would be used by binder.bindInstanceFields
    private final TextField text = new TextField();

    // Field that would be used by binder.bindInstanceFields
    // It is explicitly stated, that this field is mapped to a property with name `size`
    @PropertyId("size")
    private final TextField imageSize = new TextField();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // crate a binder for a form, specifying the data class that will be used in binding
        Binder<ImageData> binder = new Binder<>(ImageData.class);

        // specify explicit binding in order to add validation and converters
        binder.forMemberField(imageSize)
                // input should not be null or empty
                .withValidator(string -> string != null && !string.isEmpty(), "Input values should not be empty")
                // convert String to Integer, throw ValidationException if String is in incorrect format
                .withConverter(new StringToIntegerConverter("Input value should be an integer"))
                // validate converted integer: it should be positive
                .withValidator(integer -> integer > 0, "Input value should be a positive integer");

        // tell binder to bind use all fields from the current class, but considering already existing bindings
        binder.bindInstanceFields(this);

        // crate data object with predefined imageName and imageSize
        ImageData imageData = new ImageData("Lorem ipsum", 2);

        // fill form with predefined data from data object and
        // make binder to automatically update the object from the form, if no validation errors are present
        binder.setBean(imageData);

        binder.addStatusChangeListener(e -> {
            // the real image drawing will not be considered in this article

            if (e.hasValidationErrors() || !e.getBinder().isValid()) {
                Notification.show("Form contains validation errors, no image will be drawn");
            } else {
                Notification.show(String.format("I will draw image with \"%s\" text and width %d\n",
                        imageData.getText(), imageData.getSize()));
            }
        });

        // add a form to layout
        setContent(new VerticalLayout(text, imageSize));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = BinderUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
