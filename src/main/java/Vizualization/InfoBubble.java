package Vizualization;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class InfoBubble extends StackPane {


    private Circle circle;
    private Label label;
    private Tooltip tooltip;

    public InfoBubble(String tooltipText) {
        // Tworzymy okrąg o promieniu 20px
        circle = new Circle(20);
        circle.setFill(Color.CADETBLUE);
        circle.setStroke(Color.DARKBLUE);
        circle.setStrokeWidth(2);

        // Etykieta z literą "i"
        label = new Label("i");
        label.setFont(new Font("Arial", 14));
        label.setTextFill(Color.WHITE);

        this.getChildren().addAll(circle, label);

        // Konfiguracja Tooltipa
        tooltip = new Tooltip(tooltipText);
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(this, tooltip); // Tooltip jest teraz zainstalowany na obiekcie

        // Tylko faktycznie widoczna część (okrąg) będzie reagować na zdarzenia,
        // a nie cały obszar bounding boxa.
        this.setPickOnBounds(false);
    }
}