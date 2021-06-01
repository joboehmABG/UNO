import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck deck1 = new Deck();
    private ArrayList<Player> players = new ArrayList<>(0);
    private Scanner userIn = new Scanner(System.in);
    private PlayerAI playerAI = new PlayerAI();

    private int playerCount;
    private int cardsToDraw = 0;
    private int currentPlayer = 0;
    private int nextPlayer = 1;
    private String selectedColor = "none";
    private boolean cardsDrawn = false;
    private boolean eventHappened = false;

    Game(){
        deck1.createDeck();
    }

    public void start(){
        deck1.mixCards();
        System.out.print("Enter number of players (integer): ");
        playerCount = userIn.nextInt();
        for(int i = 0; i < playerCount; i++){
            System.out.println("Select Player " + i + " as AI? (0 = no, 1 = yes)");
            int userSelection = userIn.nextInt();
            if(userSelection == 0){
                players.add(new Player(false));
            }else{
                players.add(new Player(true));
            }
        }
        distributeCards();

        while(!checkWin()){

            if(deck1.getLastCard().isActionCard()){
                actionCardEvent();
            }

            System.out.print("current Card: " + deck1.getLastCard().getName());
            if(deck1.getLastCard().getColor().equals("Black")){
                System.out.println(" selected as: " + selectedColor);
            }else{
                System.out.println();
            }

            currentPlayer = (currentPlayer + nextPlayer + playerCount) % playerCount;
            System.out.println("Current Player: Player " + currentPlayer);

            playerAction();

        }

        System.out.print("\n\n" + "Player " + getWinner() + " wins!");
    }

    private void distributeCards(){
        for(int i = 0; i < (players.size() * 7); i++){
            players.get(i % players.size()).addCard(deck1.giveNextCard());
        }
    }

    private Boolean checkWin(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getCardNum() == 0){
                return true;
            }
        }
        return false;
    }

    private void actionCardEvent(){
        Card lastCard = deck1.getLastCard();

        switch(lastCard.getSymbol()){
            case "+2": if(!cardsDrawn){
                    cardsToDraw = cardsToDraw + 2;
                }
                break;
            case "+4": if(!cardsDrawn) {
                    cardsToDraw = cardsToDraw + 4;
                    selectColor();
            }
                break;
            case "reverse": if(!eventHappened){
                    if(playerCount == 2){
                        currentPlayer = (currentPlayer + nextPlayer + playerCount) % playerCount;
                    }else{
                        nextPlayer = nextPlayer * (-1);
                    }
                eventHappened = true;
            }
                break;
            case "suspend": if(!eventHappened){
                    currentPlayer = (currentPlayer + nextPlayer + playerCount) % playerCount;
                    eventHappened = true;
            }
                break;
            case "select color": if(!eventHappened){
                selectColor();
                eventHappened = true;
            }

        }
    }

    private void selectColor(){
        if(players.get(currentPlayer).isAI()){
            selectedColor = playerAI.chooseColor(players.get(currentPlayer).getHand());
        }else{
            System.out.println("Choose a Color: (0) Red   (1) Green   (2) Blue   (3) Yellow");
            switch(userIn.nextInt()){
                case 0: selectedColor = "Red";
                    break;
                case 1: selectedColor = "Green";
                    break;
                case 2: selectedColor = "Blue";
                    break;
                case 3: selectedColor = "Yellow";
                    break;
                default : System.out.println("invalid input.");
                    selectColor();
            }
        }
        System.out.println("current Card: " + deck1.getLastCard().getName() + " selected as: " + selectedColor);
    }

    private void playerAction(){
        Card lastCard = deck1.getLastCard();
        int cardToPlay;

        if(lastCard.getSymbol().equals("+4") && !cardsDrawn){
            System.out.println("You needed to draw " + cardsToDraw + " Cards. \n");
            cardToPlay = players.get(currentPlayer).getCardNum();
        }else if(cardsToDraw > 0){
            if(players.get(currentPlayer).hasDrawCard()){
                if(players.get(currentPlayer).isAI()){
                    cardToPlay = PlayerAI.aiTurn(true, selectedColor, deck1.getLastCard(), players.get(currentPlayer).getHand());
                }else{
                    players.get(currentPlayer).showCards();
                    System.out.println("(" + players.get(currentPlayer).getCardNum() + ") draw " + cardsToDraw + " Cards");
                    cardToPlay = drawCardSelection();
                }
            }else{
                System.out.println("You needed to draw " + cardsToDraw + " Cards. \n");
                cardToPlay = players.get(currentPlayer).getCardNum();
            }
        }else{
            if(players.get(currentPlayer).isAI()){
                cardToPlay = PlayerAI.aiTurn(false, selectedColor, deck1.getLastCard(), players.get(currentPlayer).getHand());
            }else{
                players.get(currentPlayer).showCards();
                System.out.println("(" + players.get(currentPlayer).getCardNum() + ") draw 1 Card");
                cardToPlay = cardSelection();
            }
        }

        executeDecision(cardToPlay);
    }

    private int cardSelection(){
        int cardNo = userIn.nextInt();
        if(isValidCard(cardNo)){
            return cardNo;
        }else{
            System.out.println("Invalid Card. Select Card to play:");
            players.get(currentPlayer).showCards();
            System.out.println("(" + players.get(currentPlayer).getCardNum() + ") draw 1 Card");
            return cardSelection();
        }
    }

    private boolean isValidCard(int cardNo){
        Card cardToPlay = players.get(currentPlayer).getCardNo(cardNo);
        Card previousCard = deck1.getLastCard();
        if(cardToPlay.getColor().equals("Black")){
            return true;
        }else if(cardToPlay.getColor().equals(previousCard.getColor()) || cardToPlay.getColor().equals(selectedColor)){
            return true;
        }else if(cardToPlay.getSymbol().equals(previousCard.getSymbol())){
            return true;
        }else if(cardNo == players.get(currentPlayer).getCardNum()){
            return true;
        }else if(cardNo > players.get(currentPlayer).getCardNum()){
            return false;
        }else{
            return false;
        }
    }

    private int drawCardSelection(){
        int cardNo = userIn.nextInt();
        if(players.get(currentPlayer).getCardNo(cardNo).getSymbol().equals("+2") || players.get(currentPlayer).getCardNo(cardNo).getSymbol().equals("+4")){
            return cardNo;
        }else if(cardNo == players.get(currentPlayer).getCardNum()){
            return cardNo;
        }else{
            System.out.println("Invalid Card. Select Card to play:");
            players.get(currentPlayer).showCards();
            System.out.println("(" + players.get(currentPlayer).getCardNum() + ") draw " + cardsToDraw + " Cards");
            return drawCardSelection();
        }
    }

    private void executeDecision(int cardNo){
        if(cardNo == players.get(currentPlayer).getCardNum()){
            if(cardsToDraw == 0){
                players.get(currentPlayer).addCard(deck1.giveNextCard());
            }else{
                for(int i = 0; i < cardsToDraw; i++){
                    players.get(currentPlayer).addCard(deck1.giveNextCard());
                }
                cardsToDraw = 0;
            }
            cardsDrawn = true;
        }else{
            deck1.addCard(players.get(currentPlayer).playCard(cardNo));
            selectedColor = "none";
            cardsDrawn = false;
            eventHappened = false;
        }

        players.get(currentPlayer).sortCards();
    }

    private int getWinner(){
        for(int i = 0; i < playerCount; i++){
            if(players.get(i).getCardNum() == 0){
                return i;
            }
        }
        return -1;
    }
}