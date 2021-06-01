import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>(0);

    public void createDeck(){                                                                                           // initializing Cards with proper values
        String[] colorArr = {"Red", "Green", "Blue", "Yellow"};
        int[] colWorthArr = {0, 100, 200, 300};
        String[] symbolArr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "reverse", "suspend"};
        int[] symbolWorthArr = {1, 2, 3 , 4, 5, 6, 7, 8, 9, 12, 11, 10};
        boolean[] actionArr = {false, false, false, true};

        for(int i = 0; i < 108; i++) {
            if (i < 4){                                                                                                 // 4 x 0 - Cards
                deck.add(new Card(colorArr[i % 4], "0", colWorthArr[i % 4], false));
            }else if(i < 100){                                                                                          // normal colored Cards (2 of each)
                deck.add(new Card(colorArr[(i-4) / 24] , symbolArr[(i-4) % 12], colWorthArr[(i-4) / 24] + symbolWorthArr[(i-4) % 12], actionArr[((i-4)/3) % 4]));
            }else if(i < 104){                                                                                          // 4 x select color - Cards
                deck.add(new Card("Black", "select color", 400, true));
            }else{                                                                                                      // 4 x +4 - Cards
                deck.add(new Card("Black", "+4", 401, true));
            }
        }

    }

    public void mixCards(){                                                                                             // randomizing Card-array by swapping random Cards
        Random rng = new Random();
        for(int i = 0; i < 300; i++){
            exchange(rng.nextInt(108), rng.nextInt(108));
        }
    }

    private void exchange(int i, int j){
        Card tempCard = deck.get(i);
        deck.set(i, deck.get(j));
        deck.set(j, tempCard);
    }

    public Card getLastCard(){
        return deck.get(deck.size() - 1);
    }

    public Card giveNextCard(){
        if(deck.size() == 0){
            createDeck();
            mixCards();
        }
        Card tempCard = deck.get(0);
        deck.remove(0);
        return tempCard;
    }

    public void addCard(Card tempCard){                                                                                 // adding played Cards back to deck
        deck.add(tempCard);
    }

    private void deckOut(){                                                                                             // internal control Tool
        for(int i = 0; i < deck.size(); i++){
            System.out.print(i + "   ");
            System.out.print(deck.get(i).getName() + "   ");
            System.out.println(deck.get(i).isActionCard());
        }
    }

}
