package PJ5;
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
    private static final int startingBalance=100;
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
        Collections.sort(currentHand, new cardComparator());
        System.out.println(currentHand);

        if(checkForRoyalFlush()){}
        else if(checkForStrtOrFlush()){}
        else if(checkForAKind()){}
        else if(checkForPairs()){}
        else{
            System.out.println("Sorry you lost this hand!");
        }


    }


    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/

    private boolean checkForRoyalFlush(){
        if (isFlush(4) == 0){
            if (currentHand.get(4).getRank() == 13){
                if (currentHand.get(3).getRank() == 12){
                    if(currentHand.get(2).getRank() == 11){
                        if (currentHand.get(1).getRank() == 10){
                            if (currentHand.get(0).getRank() == 1){
                                System.out.println("Royal Flush!");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkForStrtOrFlush(){
        if ((isStraight(numberOfCards - 1) == 0) && (isFlush(4) == 0)){
            System.out.println("Straight Flush!");
            return true;
        }
        else if ((isStraight(numberOfCards - 1) == 0) && (isFlush(4) != 0)){
            System.out.println("Straight!");
            return true;
        }
        else if ((isFlush(4) == 0) && (isStraight(numberOfCards - 1) != 0)) {
            System.out.println("Flush!");
            return true;
        }
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

    private boolean checkForAKind(){

        if (findSameRank(numberOfCards - 1) < 0 && kindCounter == 4){
            System.out.println("Four of a kind!");
            kindCounter = 0;
            return true;
        }
        else if (findSameRank(numberOfCards - 1) < 0 && kindCounter == 3){
            System.out.println("Three of a kind!");
            kindCounter = 0;
            return  true;
        }
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

    private boolean checkForPairs(){
        if (findPairs(4) == 0 && kindCounter == 2){
            System.out.println("Two Pairs!");
            kindCounter = 0;
            return true;
        }
        else if (findRoyalPair(4) == 0 && kindCounter == 1){
            System.out.println("Royal Pair!");
            kindCounter = 0;
            return true;
        }
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
        else{
            return index;
        }
    }

    private int findRoyalPair(int index){
        int cardA = 0, cardB = 0;
        int suitA = 0, suitB = -1;

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
        else{
            return index;
        }
    }




    private void showBalance(){
        System.out.println("Balance: " + balance + "\n");
    }


    private void getBet(){
        Scanner in = new Scanner( System.in );
        System.out.println("Enter bet: ");
        bet = in.nextInt();
    }

    private void verifyBet(){

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

        startGame.showBalance();
        startGame.getBet();
        startGame.shuffleDeck();
        startGame.dealDeck();
        startGame.showHand();


    }




    /** Do not modify this. It is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */
    public void testCheckHands()
    {
        try {
            currentHand = new ArrayList<Card>();

            // set Royal Flush
            System.out.println("Set Royal Flush");
            currentHand.add(new Card(1,3));
            currentHand.add(new Card(10,3));
            currentHand.add(new Card(12,3));
            currentHand.add(new Card(11, 3));
            currentHand.add(new Card(13, 3));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Straight Flush
            System.out.println("Set Straight Flush");
            currentHand.set(0, new Card(9, 3));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Straight
            System.out.println("Set Straight");
            currentHand.set(4, new Card(8,1));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Flush
            System.out.println("Set Flush");
            currentHand.set(4, new Card(5,3));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	",
            // "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

            // set Four of a Kind
            System.out.println("Set 4 o' kind");
            currentHand.clear();
            currentHand.add(new Card(8,3));
            currentHand.add(new Card(8,0));
            currentHand.add(new Card(12,3));
            currentHand.add(new Card(8, 1));
            currentHand.add(new Card(8, 2));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Three of a Kind
            System.out.println("Set 3 o' kind");
            currentHand.clear();
            currentHand.add(new Card(8, 3));
            currentHand.add(new Card(8, 0));
            currentHand.add(new Card(12, 3));
            currentHand.add(new Card(10, 1));
            currentHand.add(new Card(8, 2));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Full House
            System.out.println("Set John Stamos");
            currentHand.clear();
            currentHand.add(new Card(8, 3));
            currentHand.add(new Card(12, 0));
            currentHand.add(new Card(12, 3));
            currentHand.add(new Card(12, 1));
            currentHand.add(new Card(8, 2));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Two Pairs
            System.out.println("Set 2 pairs");
            currentHand.clear();
            currentHand.add(new Card(8, 3));
            currentHand.add(new Card(10, 0));
            currentHand.add(new Card(12, 3));
            currentHand.add(new Card(10, 1));
            currentHand.add(new Card(8, 2));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // set Royal Pair
            System.out.println("Set Royal Pair");
            currentHand.clear();
            currentHand.add(new Card(8, 3));
            currentHand.add(new Card(11, 0));
            currentHand.add(new Card(11, 3));
            currentHand.add(new Card(7, 1));
            currentHand.add(new Card(2, 2));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");

            // non Royal Pair
            System.out.println("Set a Pair");
            currentHand.clear();
            currentHand.add(new Card(8, 3));
            currentHand.add(new Card(10, 0));
            currentHand.add(new Card(12, 3));
            currentHand.add(new Card(10, 1));
            currentHand.add(new Card(1, 2));
            System.out.println(currentHand);
            checkHands();
            System.out.println("-----------------------------------\n");
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
        mypokergame.play();
    }



}
