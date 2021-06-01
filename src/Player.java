import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>(0);
    private boolean AI;

    Player(boolean isAiPlayer){
        AI = isAiPlayer;
    }

    public void addCard(Card newCard){
        hand.add(newCard);
        sortCards();
    }

    public Card playCard(int i){
        Card tempCard = hand.get(i);
        hand.remove(i);
        return tempCard;
    }

    public void showCards(){
        for(int i = 0; i < hand.size(); i++){
            System.out.print( "(" + i + ") " + hand.get(i).getName() + "  ");
        }
    }

    public void sortCards(){
        for(int i = 0; i < (hand.size() - 1); i++){
            for(int j = 0; j < (hand.size() - 1); j++){
                if(hand.get(j).getSortingWorth() > hand.get(j + 1).getSortingWorth()){
                    exchange(j, j + 1);
                }
            }
        }
    }

    private void exchange(int i, int j){
        Card tempCard = hand.get(i);
        hand.set(i, hand.get(j));
        hand.set(j, tempCard);
    }

    public int getCardNum(){
        return hand.size();
    }

    public boolean hasDrawCard(){
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).getSymbol().equals("+2") || hand.get(i).getSymbol().equals("+4")){
                return true;
            }
        }
        return false;
    }

    public Card getCardNo(int cardNo){
        if(cardNo < hand.size()) {
            return hand.get(cardNo);
        }else{
            return new Card("false", "false", 0, false);
        }
    }

    public boolean isAI(){
        return AI;
    }

    public ArrayList<Card> getHand(){
        return hand;
    }
}
