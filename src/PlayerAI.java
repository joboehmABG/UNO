import java.util.Random;
import java.util.ArrayList;

public class PlayerAI {

    public static int aiTurn(boolean hasCardsToDraw, String selectedColor, Card activeCard, ArrayList<Card> playerCards){

        boolean hasMove = false;
        ArrayList<Integer> availableOptions = new ArrayList<>();

        if(!hasCardsToDraw){
            for(int i = 0; i < playerCards.size(); i++){
                if(playerCards.get(i).getColor().equals(activeCard.getColor()) || playerCards.get(i).getSymbol().equals(activeCard.getSymbol()) || playerCards.get(i).getColor().equals("Black")){
                    hasMove = true;
                    availableOptions.add(i);
                }else if(playerCards.get(i).getColor().equals(selectedColor)){
                    hasMove = true;
                    availableOptions.add(i);
                }
            }
        }else{
            for(int i = 0; i < playerCards.size(); i++){
                if(playerCards.get(i).getSymbol().equals("+2") || playerCards.get(i).getSymbol().equals("+4")){
                    hasMove = true;
                    availableOptions.add(i);
                }
            }
        }

        if(!hasMove){
            return playerCards.size();
        }else{
            Random rng = new Random();
            return availableOptions.get(rng.nextInt(availableOptions.size()));
        }

    }

    public String chooseColor(ArrayList<Card> playerCards){
        int[] amountOfColCards = {0, 0, 0, 0};
        int choosenCol = 0;
        for(int i = 0; i < playerCards.size(); i++){
            switch(playerCards.get(i).getColor()){
                case "Red": amountOfColCards[0] += 1;
                    break;
                case "Green": amountOfColCards[1] += 1;
                    break;
                case "Blue": amountOfColCards[2] += 1;
                    break;
                case "Yellow": amountOfColCards[3] += 1;
                    break;
            }
        }
        if(amountOfColCards[choosenCol] < amountOfColCards[1]){
            choosenCol = 1;
        }
        if(amountOfColCards[choosenCol] < amountOfColCards[2]){
            choosenCol = 2;
        }
        if(amountOfColCards[choosenCol] < amountOfColCards[3]){
            choosenCol = 3;
        }

        switch (choosenCol){
            case 1: return "Green";
            case 2: return "Blue";
            case 3: return  "Yellow";
            default: return "Red";
        }
    }
}
