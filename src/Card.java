public class Card {

    private String name;
    private String color;
    private String symbol;
    private int sortingWorth;
    private boolean actionCard;

    Card(String Ccolor, String cSymbol, int cSortingWorth, boolean cActionCard){
        color = Ccolor;
        symbol = cSymbol;
        sortingWorth = cSortingWorth;
        actionCard = cActionCard;
        name = color + " " + symbol;
    }

    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getSortingWorth() { return sortingWorth; }
    public boolean isActionCard() {
        return actionCard;
    }
}
