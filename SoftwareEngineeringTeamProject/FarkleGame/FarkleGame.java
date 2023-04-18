package FarkleGame;

//Credit To Tianyoul On GitHub For Help With Final
//Score Implementation And Randomizer Function

//Author: 			Adams Smith
//Description:		

public class FarkleGame {
	public static final int WINNING_SCORE = 10000;
	private User p0;
    private User p1;
    private User p2;
    private User currentUser;
    private Roll d0;
    private Roll d1;
    private Roll d2;
    private Roll d3;
    private Roll d4;
    private Roll d5;

    public void init(){
        p0 = new User(0, "p0");
        p1 = new User(0, "p1");
        p2 = new User(0, "p2");
        d0 = new Roll(1,false);
        d1 = new Roll(1,false);
        d2 = new Roll(1,false);
        d3 = new Roll(1,false);
        d4 = new Roll(1,false);
        d5 = new Roll(1,false);
    }

    public void run() {
    	//Set Players Names Within Login System
        p0.setUsername(("The name of player 1:"));
        p1.setUsername(("The name of player 2:"));
        p2.setUsername(("The name of player 3:"));
        currentUser = p2;

        while(true) {
            nextPlayer();
            //Print Name Of Who's Turn It Is
            //println(currentUser.getUsername()+"'s turn:");

            takeTurn();

            if(testForWin(currentUser)){
                User current;
                current = currentUser;
                //Find Current Winner
                //println(winner.getUsername()+" is winning.");
                resetDice();
                nextPlayer();
                //println(currentUser.getUsername()+"'s turn:");
                takeTurn();
                resetDice();
                nextPlayer();
                //println(currentUser.getUsername()+"'s turn:");
                takeTurn();
                current = findHighestScorer();
                //Set Winners Name
                //println(winner.getUsername()+" has won the game and the score is "+winner.getScore()+".");
                break;
            }

            resetDice();

        }


    }

    public User findHighestScorer(){
        if(p0.getScore()>p1.getScore()){
            if(p0.getScore()>p2.getScore()){
                return p0;
            } else {
                return p2;
            }
        } else {
            if (p1.getScore()>p2.getScore()){
                return p1;
            } else {
                return p2;
            }
        }
    }





    private void takeTurn(){
        boolean keepRolling = true;
        boolean scoreAllSix = false;
        boolean s0;
        boolean s1;
        boolean s2;
        boolean s3;
        boolean s4;
        boolean s5;
        int addScore = 0;
        while (keepRolling||scoreAllSix){
            scoreAllSix = false;
            rollDice();
            if (testBust()){
                printDice();
                //Set Bust Text
                //println("Sorry, you busted!");
                addScore = 0;
                break;}
            printDice();
            s0 = d0.setAside();
            s1 = d1.setAside();
            s2 = d2.setAside();
            s3 = d3.setAside();
            s4 = d4.setAside();
            s5 = d5.setAside();
            String asideValue = setDiceAside();
            while(!allScore(asideValue)){
            	//ERROR MESSAGE
            	//NO DICE TO SCORE
                //println("One or some of the dices you put aside can not score.");
                d0.setAside = s0;
                d1.setAside = s1;
                d2.setAside = s2;
                d3.setAside = s3;
                d4.setAside = s4;
                d5.setAside = s5;
                printDice();
                asideValue = setDiceAside();
            }
            while(asideValue.length()==0){
            	//ERROR MESSAGE
            	//NO DICE SET ASIDE
                //println("You must set aside at least one die.");
                printDice();
                asideValue = setDiceAside();
            }
            addScore = addScore + calculateScore(asideValue);
            //Changing Score
            //println("Current score is "+addScore+".");
            if (d0.setAside&&d1.setAside&&d2.setAside&&d3.setAside&&d4.setAside&&d5.setAside){
            	//Hot Dice Text
                //println("You have hot dice. Would You Like To Keep Rolling?");
                scoreAllSix = true;
                keepRolling = false;
                resetDice();
            } else {
            	//Add Button To Keep Rolling?
                //keepRolling = readBoolean("Continue rolling (true or false)?");
            }
        }
        currentUser.modScore(addScore);
        //Score Update - Current Score
        //println(currentUser.getUsername()+" has "+currentUser.getScore()+" points. Next player's turn!"+"\n");
    }

    public boolean allScore(String diceValue){
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num6 = 0;
        for(int i = 0 ; i < diceValue.length();i++){
            if ((""+diceValue.charAt(i)).equals("2")){
                num2++;
            } else if ((""+diceValue.charAt(i)).equals("3")){
                num3++;
            } else if ((""+diceValue.charAt(i)).equals("4")){
                num4++;
            } else if ((""+diceValue.charAt(i)).equals("6")){
                num6++;
            }
        }
        return ((num2==3||num2==6||num2==0)&&(num3==3||num3==6||num3==0)&&(num4==3||num4==6||num4==0)&&(num6==3||num6==6||num6==0));
    }
    private void resetDice(){
        d0.setAside = false;
        d1.setAside = false;
        d2.setAside = false;
        d3.setAside = false;
        d4.setAside = false;
        d5.setAside = false;
    }
    private void rollDice(){
        d0.diceRoll();
        d1.diceRoll();
        d2.diceRoll();
        d3.diceRoll();
        d4.diceRoll();
        d5.diceRoll();
    }

    public boolean testBust(){
        String diceValue ="";
        if (!d0.setAside()){
            diceValue = diceValue + d0.getRolledNum();
        }
        if (!d1.setAside()){
            diceValue = diceValue + d1.getRolledNum();
        }
        if (!d2.setAside()){
            diceValue = diceValue + d2.getRolledNum();
        }
        if (!d3.setAside()){
            diceValue = diceValue + d3.getRolledNum();
        }
        if (!d4.setAside()){
            diceValue = diceValue + d4.getRolledNum();
        }
        if (!d5.setAside()){
            diceValue = diceValue + d5.getRolledNum();
        }
        return calculateScore(diceValue)==0;
    }

    public int calculateScore(String diceValues){
        int score = 0;

        score = score + checkThreeOfAKind(diceValues, 1)*1000;
        score = score + checkThreeOfAKind(diceValues, 2)*200;
        score = score + checkThreeOfAKind(diceValues, 3)*300;
        score = score + checkThreeOfAKind(diceValues, 4)*400;
        score = score + checkThreeOfAKind(diceValues, 5)*500;
        score = score + checkThreeOfAKind(diceValues, 6)*600;
        if (countOccurrences(diceValues, 1)>3&&countOccurrences(diceValues, 1)<6){
            score = score + (countOccurrences(diceValues, 1)-3)*100;
        } else if (countOccurrences(diceValues, 1)<3){
            score = score + countOccurrences(diceValues, 1)*100;
        }
        if ((countOccurrences(diceValues, 5)>3&&countOccurrences(diceValues, 5)<6)){
            score = score + (countOccurrences(diceValues, 5)-3)*50;
        } else if (countOccurrences(diceValues, 5)<3){
            score = score + countOccurrences(diceValues, 5)*50;
        }
        return score;
    }

    public int checkThreeOfAKind(String diceValues, int num){
        if (countOccurrences(diceValues, num)>=3&&countOccurrences(diceValues, num)<6){
            return 1;
        } else if (countOccurrences(diceValues, num)==6){
            return 2;
        } else {
            return 0;
        }
    }

    public int countOccurrences(String combinedRolls, int testVal){
        return combinedRolls.length() - combinedRolls.replace(""+testVal, "").length();
    }

    private void printDice(){
        if (d0.setAside()){
            //print("D0: "+d0.getRolledNum()+" Set Aside,     ");
        } else {
            //print("D0: "+d0.getRolledNum()+",     ");
        }
        if (d1.setAside()){
            //print("D1: "+d1.getRolledNum()+" Set Aside,     ");
        } else {
            //print("D1: "+d1.getRolledNum()+",     ");
        }
        if (d2.setAside()){
            //print("D2: "+d2.getRolledNum()+" Set Aside,     ");
        } else {
            //print("D2: "+d2.getRolledNum()+",     ");
        }
        if (d3.setAside()){
            //print("D3: "+d3.getRolledNum()+" Set Aside,     ");
        } else {
            //print("D3: "+d3.getRolledNum()+",     ");
        }
        if (d4.setAside()){
            //print("D4: "+d4.getRolledNum()+" Set Aside,     ");
        } else {
            //print("D4: "+d4.getRolledNum()+",     ");
        }
        if (d5.setAside()){
            //print("D5: "+d5.getRolledNum()+" Set Aside,     "+"\n");
        } else {
            //print("D5: "+d5.getRolledNum()+",     "+"\n");
        }

    }



    private String setDiceAside(){
        String valueAside = "";
        
        
        /*if (!d0.setAside){
        	//Set Aside Dice? Potentially Made Into Button?
            if (readBoolean("Would you like to set D0 aside? (true or false)")){
                d0.setSetAside();
                valueAside=valueAside + d0.getRolledNum();
            }
        }
        if (!d1.setAside){
            if (readBoolean("Would you like to set D1 aside? (true or false)")){
                d1.setSetAside();
                valueAside=valueAside + d1.getRolledNum();
            }
        }
        if (!d2.setAside){
            if (readBoolean("Would you like to set D2 aside? (true or false)")){
                d2.setSetAside();
                valueAside=valueAside + d2.getRolledNum();
            }
        }
        if (!d3.setAside){
            if (readBoolean("Would you like to set D3 aside? (true or false)")){
                d3.setSetAside();
                valueAside=valueAside + d3.getRolledNum();
            }
        }
        if (!d4.setAside){
            if (readBoolean("Would you like to set D4 aside? (true or false)")){
                d4.setSetAside();
                valueAside=valueAside + d4.getRolledNum();
            }
        }
        if (!d5.setAside){
            if (readBoolean("Would you like to set D5 aside? (true or false)")){
                d5.setSetAside();
                valueAside=valueAside + d5.getRolledNum();
            }
        }*/
        return valueAside;
    }
    

    public boolean testForWin(User p){
        return (p.getScore() >= WINNING_SCORE);
    }

    private void nextPlayer(){
        if (currentUser.getUsername().equals(p0.getUsername())){
           currentUser = p1;
        } else if (currentUser.getUsername().equals(p1.getUsername())){
            currentUser = p2;
        } else {
            currentUser = p0;
        }
    }
}
