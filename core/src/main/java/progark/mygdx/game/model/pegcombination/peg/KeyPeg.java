package progark.mygdx.game.model.pegcombination.peg;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class KeyPeg implements PegInterface {
    private static final List<Color> ALLOWED_COLORS = List.of(
        Color.of("white"),
        Color.of("black"));

    private Color color;

    public KeyPeg(String color) {
        setColor(color);
    }

    /**
     * @param color
     * @return whether the input is a valid color for key pegs
     */
    public boolean isAllowedColor(String color) {
        try {
            return ALLOWED_COLORS.contains(Color.of(color));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return the color of this key peg
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color
     * Set the color of a keyPeg and update the figure.
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
     * @return the figure the color of the key peg are mapped to
     */
    public String getFigure(){
        return this.color.getFigure();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyPeg keyPeg = (KeyPeg) o;
        return Objects.equals(color, keyPeg.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "KeyPeg{color=" + color + "'}";
    }
}
