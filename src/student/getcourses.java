package student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class getcourses {

    private final StringProperty level;

    public getcourses(String course) {
        this.level = new SimpleStringProperty(course);
    }

    public StringProperty IDProperty() {
        return level;
    }

    public String getlevel() {
        return level.get();
    }

    public void setlevel(String level) {

        String[] part = level.split("(");
        String code = part[0].trim();
        this.level.set(code);
    }

}
