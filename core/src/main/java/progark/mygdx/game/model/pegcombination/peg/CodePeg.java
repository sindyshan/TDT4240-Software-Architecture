package progark.mygdx.game.model.pegcombination.peg;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
public class CodePeg implements PegInterface {
    private static final List<Color> ALLOWED_COLORS = List.of(
        Color.of("red"),
        Color.of("blue"),
        Color.of("green"),
        Color.of("yellow"),
        Color.of("orange"),
        Color.of("purple"),
        Color.of("pink"));

    private Color color;

    public CodePeg(String color) {
        setColor(color);
    }

    /**
     * @param color
     *          Check whether a certain color is allowed for CodePeg.
     */
    public boolean isAllowedColor(String color) {
        try {
            return ALLOWED_COLORS.contains(Color.of(color));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return Color of this CodePeg.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color
     *          Set the color of a CodePeg and update the figure.
     */
    public void setColor(String color) {
        if (!isAllowedColor(color)) {
            throw new IllegalArgumentException("Invalid color as code peg: " + color);
        }

        Optional<Color> foundColor = ALLOWED_COLORS.stream()
            .filter(c -> c.getColorName().equals(color))
            .findFirst();

        if (foundColor.isPresent()) {
            this.color = foundColor.get();
        } else {
            throw new IllegalArgumentException("Color not found in allowed colors: " + color);
        }
    }

    /**
     * @return the figure the color of the code peg are mapped to
     */
    public String getFigure(){
        return this.color.getFigure();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodePeg codePeg = (CodePeg) o;
        return Objects.equals(color, codePeg.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "CodePeg{color=" + color +"'}";
    }
}
