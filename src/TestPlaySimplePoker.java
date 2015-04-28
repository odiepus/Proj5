/*************************************************************************************
 *
 *  This program is used to test PJ5.SimplePoker class
 *  More info are given in Readme file
 * 
 *  PJ5 class allows user to run program as follows:
 *
 *    	java PJ5		    // default credit is $100
 *  or 	java PJ5 NNN		// set initial credit to NNN
 *
 *  Do not modify this file!
 *
 **************************************************************************************/

import PJ5.SimplePoker;

class TestPlaySimplePoker {

    public static void main(String args[]) 
    {
		SimplePoker mypokergame;
		if (args.length > 0)
			mypokergame = new SimplePoker(Integer.parseInt(args[0]));
		else
			mypokergame = new SimplePoker();
		mypokergame.play();
    }
}
