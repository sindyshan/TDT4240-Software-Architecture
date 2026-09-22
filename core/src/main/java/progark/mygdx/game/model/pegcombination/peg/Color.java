package progark.mygdx.game.model.pegcombination.peg;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;


public class Color {
    private static final Map<String, Color> COLORS = new HashMap<>();
    private static final Map<String, String> COLOR_TO_FIGURE = Map.of(
        "red", "circle",
        "white", "diamond",
        "blue", "square",
        "green", "triangle",
        "yellow", "star",
        "orange", "hexagon",
        "purple", "pentagon",
        "black", "cross",
        "pink", "heart");

    private final String colorName;
    private final String figure;

    private Color(String colorName) {
        this.colorName = colorName;
        this.figure = COLOR_TO_FIGURE.get(colorName);
    }

    public static Color of(String colorName) {
        if (!COLOR_TO_FIGURE.containsKey(colorName)) {
            throw new IllegalArgumentException("Invalid color: " + colorName);
        }
        return COLORS.computeIfAbsent(colorName, Color::new);
    }

    /**
     * @return the name of this color
     */
    public String getColorName() {
        return this.colorName;
    }

    /**
     * @return the figure of this color
     */
    public String getFigure() {
        return this.figure;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Color color = (Color) obj;
        return Objects.equals(colorName, color.colorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorName);
    }

    @Override
    public String toString() {
        return "Color{name='" + colorName + "', figure='" + figure + "'}";
    }
}
