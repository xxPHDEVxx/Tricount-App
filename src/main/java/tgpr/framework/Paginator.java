package tgpr.framework;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.function.Consumer;

public class Paginator extends Panel {
    private final int pageSize;
    private final Consumer<Integer> pageChangedHandler;
    private final Button btnFirst;
    private final Button btnPrevious;
    private final Label lblPage;
    private final Button btnNext;
    private final Button btnLast;
    private int count = 0;
    private int page = 0;
    private int numPages = 0;

    public Paginator(Window window, int pageSize, Consumer<Integer> pageChangedHandler) {
        this.pageSize = pageSize;
        this.pageChangedHandler = pageChangedHandler;
        asHorizontalPanel(1);
        btnFirst = new Button("First", this::first).addTo(this);
        btnPrevious = new Button("Previous", this::previous).addTo(this);
        lblPage = new Label("Page ? of ?").addTo(this);
        btnNext = new Button("Next", this::next).addTo(this);
        btnLast = new Button("Last", this::last).addTo(this);

        window.addShortcut(btnNext, KeyStroke.fromString("<pagedown>"));
        window.addShortcut(btnPrevious, KeyStroke.fromString("<pageup>"));
        window.addShortcut(btnFirst, KeyStroke.fromString("<home>"));
        window.addShortcut(btnLast, KeyStroke.fromString("<end>"));
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        numPages = Math.max(0, count - 1) / pageSize + 1;
        if (page >= numPages)
            setPage(numPages - 1);
        refresh();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page == this.page) return;
        if (page < 0)
            page = 0;
        else if (page >= numPages)
            page = numPages - 1;
        this.page = page;
        refresh();
        pageChangedHandler.accept(getPage());
    }

    public int getStart() {
        return page * pageSize;
    }

    private void refresh() {
        lblPage.setText("Page " + (page + 1) + " of " + numPages);
        btnFirst.setEnabled(page > 0);
        btnPrevious.setEnabled(page > 0);
        btnLast.setEnabled(page < numPages - 1);
        btnNext.setEnabled(page < numPages - 1);
    }

    private void previous() {
        setPage(getPage() - 1);
    }

    private void next() {
        setPage(getPage() + 1);
    }

    private void first() {
        setPage(0);
    }

    private void last() {
        setPage(numPages - 1);
    }
}

