package fi.vaadin.articles.binder;

/**
 * A class to hold image data, used to draw a square image with specified text.
 */
public class ImageData {
    private String text;
    private int size;

    /**
     * Creates an image data with specified parameters.
     *
     * @param text text that will be drawn in the image
     * @param size size of the image square
     */
    public ImageData(String text, int size) {
        this.text = text;
        this.size = size;
    }

    /**
     * Gets text of the image.
     *
     * @return text of the image
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text of the image.
     *
     * @param text text of the image
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets size of the image square.
     *
     * @return size of the image square
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the image square.
     *
     * @param size size of the image square
     */
    public void setSize(int size) {
        this.size = size;
    }
}
