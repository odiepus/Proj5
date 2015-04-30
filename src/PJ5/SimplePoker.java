package PJ5;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.google.com/ig/directory?type=gadgets&url=www.labpixies.com/campaigns/videopoker/videopoker.xml
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each currentHand. 
 * The player is dealt one five-card poker currentHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the main poker game class.
 * It uses Decks and Card objects to implement poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class SimplePoker {

    // default constant values
    private static final int startingBalance=1000;
    private static final int numberOfCards=5;

    // default constant payout value and currentHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={
            "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	",
            "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private static final Decks oneDeck = new Decks(1);

    // holding current poker 5-card hand, balance, bet    
    private List<Card> currentHand;
    private int balance;
    private int bet;
    private int kindCounter = 0;

    /** default constructor, set balance = startingBalance */
    public SimplePoker()
    {
        this(startingBalance);
    }

    /** constructor, set given balance */
    public SimplePoker(int balance)
    {
        this.balance= balance;
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    {
        System.out.println("\n\n");
        System.out.println("Payout Table   	      Multiplier   ");
        System.out.println("=======================================");
        int size = multipliers.length;
        for (int i=size-1; i >= 0; i--)
        {
            System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
        }
        System.out.println("\n\n");
    }

    /** Check current currentHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()                           //Will check hand for winner and print out result
    {
       Collections.sort(currentHand, new cardComparator());/*---------------------->>>*/      //DO NOT REMOVE used to put cards in order. check methods rely on cards being in order

        if(checkForRoyalFlush()){}
        else if(checkForStrtOrFlush()){}
        else if(checkForJohnStamos()){}
        else if(checkForAKind()){}
        else if(checkForPairs()){}
        else{
            System.out.println("Bad Beat!  (You lost)");
        }
    }


    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/


/*******************CHECK FOR WINNERS***************************************************/

/*&*&*&*&*&*&*&*&*&*&*&START -- ROYAL FLUSH&**&*&*&*&*&*&*&*&*&*&*/
    private boolean checkForRoyalFlush(){
        if (isFlush(4) == 0){
            if (currentHand.get(4).getRank() == 13){
                if (currentHand.get(3).getRank() == 12){
                    if(currentHand.get(2).getRank() == 11){
                        if (currentHand.get(1).getRank() == 10){
                            if (currentHand.get(0).getRank() == 1){
                                System.out.println("Royal Flush!");
                                System.out.println("You won $" + (250 * bet) + "!");
                                balance += 250 * bet;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /*&*&*&*&*&*&*&*&*&*&*&END -- ROYAL FLUSH&**&*&*&*&*&*&*&*&*&*&*/

    /*&*&*&*&*&*&*&*&*&*&*&START -- STR8 or FLUSH&**&*&*&*&*&*&*&*&*&*&*/
    private boolean checkForStrtOrFlush(){
        if ((isStraight(numberOfCards - 1) == 0) && (isFlush(4) == 0)){
            System.out.println("Steel Wheel!!  (Straight Flush)");
            System.out.println("You won $" + (50 * bet) + "!");
            balance += 50 * bet;
            return true;
        }
        else if ((isStraight(numberOfCards - 1) == 0) && (isFlush(4) != 0)){
            System.out.println("Straight!");
            System.out.println("You won $" + (5 * bet) + "!");
            balance += 5 * bet;
            return true;
        }
        else if ((isFlush(4) == 0) && (isStraight(numberOfCards - 1) != 0)) {
            System.out.println("Flush!");
            System.out.println("You won $" + (6 * bet) + "!");
            balance += 6 * bet;
            return true;
        }
        kindCounter = 0;
        return false;
    }


    private int isStraight(int index){
        int cardA = 0, cardB = 0;

        if (index != 0) {
            cardA = currentHand.get(index - 1).getRank();
            cardB = currentHand.get(index).getRank();
        }

        if (index == 0){
            return  index;
        }
        else if (cardB - cardA ==1){
            return isStraight(index - 1);
        }
        else{
            return index;
        }
    }

    private int isFlush(int index){
        int cardA = 0, cardB = 0;

        if (index != 0) {
            cardA = currentHand.get(index - 1).getSuit();
            cardB = currentHand.get(index).getSuit();
        }

        if (index ==0){
            return  index;
        }
        else if (cardB - cardA  == 0){
            return isFlush(index - 1);
        }
        else{
            return index;
        }
    }


    /*&*&*&*&*&*&*&*&*&*&*&END -- STR8 or FLUSH&**&*&*&*&*&*&*&*&*&*&*/


    /*&*&*&*&*&*&*&*&*&*&*&START -- JOHN STAMOS&**&*&*&*&*&*&*&*&*&*&*/

    private boolean checkForJohnStamos(){
        if (findJohnStamosTop(numberOfCards - 1) == -1 && findJohnStamosBottom(numberOfCards - 1) == -1 && kindCounter == 5){
            System.out.println("John Stamos!   (Full House)");
            System.out.println("You won $" + (9 * bet) + "!");
            balance += 9 * bet;
            kindCounter = 0;
            return true;
        }
        kindCounter = 0;
        return false;
    }

    private int findJohnStamosTop(int index){
        int cardTop = currentHand.get(4).getRank();

        if (index < 0){
            return  index;
        }
        if (cardTop == currentHand.get(index).getRank()){
            kindCounter +=1;
            return  findJohnStamosTop(index - 1);
        }else if (cardTop != currentHand.get(index).getRank()){
            return  findJohnStamosTop(index - 1);
        }
        return index;
    }

    private int findJohnStamosBottom(int index){
        int cardBottom = currentHand.get(0).getRank();

        if (index < 0){
            return  index;
        }
        if (cardBottom == currentHand.get(index).getRank()){
            kindCounter +=1;
            return  findJohnStamosBottom(index - 1);
        }else if (cardBottom != currentHand.get(index).getRank()){
            return  findJohnStamosBottom(index - 1);
        }
        return index;
    }
    /*&*&*&*&*&*&*&*&*&*&*&END -- JOHN STAMOS&**&*&*&*&*&*&*&*&*&*&*/


    /*&*&*&*&*&*&*&*&*&*&*&START -- 4/3 KIND&**&*&*&*&*&*&*&*&*&*&*/

    private boolean checkForAKind(){

        if (findSameRank(numberOfCards - 1) < 0 && kindCounter == 4){
            System.out.println("Quads!!  (Four of a kind)");
            System.out.println("You won $" + (25 * bet) + "!");
            balance += 25 * bet;
            kindCounter = 0;
            return true;
        }
        kindCounter = 0;
        if (findSameRank(numberOfCards - 1) < 0 && kindCounter == 3){
            System.out.println("Trips!! (Three of a kind)");
            System.out.println("You won $" + (3 * bet) + "!");
            balance += 3 * bet;
            kindCounter = 0;
            return  true;
        }
        kindCounter = 0;
        return false;
    }


    private int findSameRank(int index){
        int cardMiddle = currentHand.get(2).getRank();

        if (index < 0){
            return  index;
        }
        else if (cardMiddle == (currentHand.get(index).getRank())){
            kindCounter+=1;
            return findSameRank(index - 1);
        } else if(cardMiddle != (currentHand.get(index).getRank())){
            return findSameRank(index - 1);
        }
        else{
            return index;
        }
    }
    /*&*&*&*&*&*&*&*&*&*&*&END -- 4/3 KIND&**&*&*&*&*&*&*&*&*&*&*/


    /*&*&*&*&*&*&*&*&*&*&*&START -- PAIRS &**&*&*&*&*&*&*&*&*&*&*/
    private boolean checkForPairs(){
        if (findPairs(4) == 0 && kindCounter == 2){
            System.out.println("Two Pairs!");
            System.out.println("You won $" + (2 * bet) + "!");
            balance += 2 * bet;
            kindCounter = 0;
            return true;
        }
        kindCounter = 0;
        if (findRoyalPair(4) == 0 && kindCounter == 1){
            System.out.println("Royal Pair!");
            System.out.println("You won $" + (bet) + "!");
            balance += bet;
            kindCounter = 0;
            return true;
        }
        kindCounter = 0;
        return false;
    }

    private int findPairs(int index){
        int cardA = 0, cardB = 0;

        if (index != 0) {
            cardA = currentHand.get(index - 1).getRank();
            cardB = currentHand.get(index).getRank();
        }

        if (index == 0){
            return  index;
        }
        else if (cardB == cardA){
            kindCounter +=1;
            return findPairs(index - 1);
        }
        else if (cardB != cardA){
            return findPairs(index - 1);
        }
        return index;
    }

    private int findRoyalPair(int index){
        int cardA = 0, cardB = 0;

        if (index != 0) {
            cardA = currentHand.get(index - 1).getRank();
            cardB = currentHand.get(index).getRank();
        }

        if (index == 0){
            return  index;
        }
        else if (cardB == cardA && (cardA == 11 || cardA == 12 || cardA == 13) ){
            kindCounter +=1;
            return findRoyalPair(index - 1);
        }
        else if (cardB != cardA){
            return findRoyalPair(index - 1);
        }
        return index;
    }
    /*&*&*&*&*&*&*&*&*&*&*&START -- PAIRS &**&*&*&*&*&*&*&*&*&*&*/


    /*%*%*%*%*%*%*%*%*%*%*%START -- MONEY CONTROL%*%*%*%*%*%*%*%*/

    private void showBalance(){
            System.out.println("Balance: " + balance + "\n");
    }

    private void getBet(){
        Scanner in = new Scanner( System.in );
        System.out.println("Enter bet: ");
        bet = in.nextInt();
    }

    private boolean verifyBet(){
        if (bet <= balance){
            balance -= bet;
            return true;}
        else {return false;}
    }

    private void showCurrentBalanceBet(){
        System.out.println("Current Balance: $" + balance + "  Current Bet: $" + bet);
    }
    /*%*%*%*%*%*%*%*%*%*%*%END -- MONEY CONTROL%*%*%*%*%*%*%*%*/


    /*%*%*%*%*%*%*%*%*%*%*%START -- CARD CONTROL%*%*%*%*%*%*%*%*/

    private void resetDeck(){
        oneDeck.reset();
    }

    private void shuffleDeck() {
        oneDeck.shuffle();
    }

    private void dealDeck() {
        try {
            currentHand = new ArrayList<Card>(oneDeck.deal(numberOfCards));
        } catch (PlayingCardException e) {
            System.out.println("*** In catch block : PlayingCardException : msg : " + e.getMessage());
        }
    }

    private class cardComparator implements Comparator <Card>{
        @Override
        public int compare(Card cardA, Card cardB) {

            if (cardA.getRank() < cardB.getRank()) {
                return -1;
            }
            else if (cardA.getRank() > cardB.getRank()){
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    private void showHand(){
        Collections.sort(currentHand, new cardComparator());
        System.out.println("Hand: " + currentHand);
    }

    private void whatCardsToKeep(){
        System.out.println("Enter position(s) to keep. (e.g. 0 1 2 3 4): ");
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();                                   //Following was taken from StackOverflow Samuel French and jlordo
        StringTokenizer strgToken = new StringTokenizer(userInput);         //method of taking in a string of ints and putting them into int array

        int size = strgToken.countTokens();

        int[] positionsToHold = new int[size];

        for (int i = 0; i < size; i++) {
            positionsToHold[i] = Integer.parseInt((String) strgToken.nextElement());
        }

        removeAndReplace(positionsToHold);
        showHand();
    }

    private void removeAndReplace(int[] positions){
        List <Card> newHand;
        int fillWith = numberOfCards - positions.length;

        try {
            newHand = new ArrayList<Card>(oneDeck.deal(fillWith));
            for (int i = 0; i < positions.length; i++) {
                newHand.add(currentHand.get(positions[i]));
            }
            currentHand.clear();
            for (int i = 0; i < numberOfCards; i++) {
                currentHand.add(newHand.get(i));
            }

        } catch (PlayingCardException e) {
            System.out.println("*** In catch block : PlayingCardException : msg : " + e.getMessage());
        }
    }
    /*%*%*%*%*%*%*%*%*%*%*%END -- CARD CONTROL%*%*%*%*%*%*%*%*/

    public void play()
    {
        /** The main algorithm for single player poker game
         *
         * Steps:
         * 		showPayoutTable()
         *
         * 		++
         * 		show balance,
         * 		get bet
         *		verify bet value
         *		update balance
         *		reset deck
         *		shuffle deck
         *		deal cards
         *		display cards
         *
         *		ask for position of cards to keep
         *      get positions in one input line
         *		update cards
         *		check hands
         *		display proper messages
         *		update balance if there is a payout
         *
         *		if balance = O:
         *			end of program
         *		else
         *			ask if the player wants to play a new game
         *			if the answer is "no" : end of program
         *			else : showPayoutTable() if user wants to see it
         *			goto ++
         */

        // implement this method!

        showPayoutTable();

        SimplePoker startGame = new SimplePoker();
        while (true){
            startGame.showBalance();
            startGame.getBet();
            if (startGame.verifyBet()) {

                startGame.shuffleDeck();
                startGame.dealDeck();
                startGame.showCurrentBalanceBet();
                startGame.showHand();
                startGame.whatCardsToKeep();
                startGame.checkHands();
                if (startGame.balance <= 0){
                    System.out.println("You have no more money. Now scram!!!");
                    break;
                }

                System.out.println("Play again? (y / n) ");
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();
                if (input.charAt(0) == 'n'){
                    System.out.println("Go cry to your mommy!!");
                    break;
                }
            }
            else {
                System.out.println("Please enter a bet of $" + balance + " or below.");
            }

            System.out.println("Would you like to see the payout table? ");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (input.charAt(0) == 'y'){
                showPayoutTable();
            }

            startGame.resetDeck();
        }
    }

    /** Do not modify this. It is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */
    public void testCheckHands()
    {
       try {
    		currentHand = new ArrayList<Card>();

    		// set Royal Flush
    		currentHand.add(new Card(1,3));
    		currentHand.add(new Card(10,3));
    		currentHand.add(new Card(12,3));
    		currentHand.add(new Card(11,3));
    		currentHand.add(new Card(13,3));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Straight Flush
    		currentHand.set(0,new Card(9,3));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Straight
    		currentHand.set(4, new Card(8,1));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Flush
    		currentHand.set(4, new Card(5,3));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	",
    	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    		// set Four of a Kind
    		currentHand.clear();
    		currentHand.add(new Card(8,3));
    		currentHand.add(new Card(8,0));
    		currentHand.add(new Card(12,3));
    		currentHand.add(new Card(8,1));
    		currentHand.add(new Card(8,2));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Three of a Kind
    		currentHand.set(4, new Card(11,3));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Full House
    		currentHand.set(2, new Card(11,1));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Two Pairs
    		currentHand.set(1, new Card(9,1));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// set Royal Pair
    		currentHand.set(0, new Card(3,1));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");

    		// non Royal Pair
    		currentHand.set(2, new Card(3,3));
    		System.out.println(currentHand);
    		checkHands();
    		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		  System.out.println(e.getMessage());
      	}
    }

    /* Quick testCheckHands() */
    public static void main(String args[])
    {
	   SimplePoker mypokergame = new SimplePoker();
	   mypokergame.testCheckHands();
    }
}
