package progark.mygdx.game.model.pegcombination.peg;

public interface PegInterface {
    public Color getColor();

    public void setColor(String color);

    public boolean isAllowedColor(String color);

    public String getFigure();

}
