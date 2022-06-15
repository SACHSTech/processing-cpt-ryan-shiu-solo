/**
 * A program @Sketch.java where the user must controll where kirby is in order to gain points and avoid losing lives
 * @author: R. Shiu
 */

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList; 

public class Sketch extends PApplet {
	PImage imgKirbyFalling;
  PImage imgKirbyEating;
  PImage imgLives;
  PImage imgCandy;
  PImage imgBackground0;
  PImage imgBackground1;
  PImage imgBackground2;
  PImage imgGameOver;
  PImage imgPlayAgain;
  PImage imgCoin;

  float fltplayerX = 350;
  float fltplayerY = 500;
  float fltkirbyFallingX = 0;
  float fltkirbyFallingY = 0; 
  float fltlives1X = 625;
  float fltlives1Y = 0;
  float fltlives2X = 575;
  float fltlives2Y = 0;
  float fltlives3X = 525;
  float fltlives3Y = 0;
  float fltbadcircleYMovement = 5;
  float fltMustHitY = 0;
  float fltMustHitScore = 10;
  float fltMustHitSpeed = 1;

  int intLives = 3;
  int intScore = 0;
  int intSpeedBoostScore = 20;
  int intPlayAgainX = 280;
  int intPlayAgainY = 425;
  int intHighScore1 = 0;
  int intHighScore2 = 0;
  int intHighScore3 = 0;

  boolean blnPlayerAlive = false;
  boolean blnKirbySpawn = false;
  boolean blnLevel1 = false;
  boolean blnStopSpeed = true;

  float[] candyX = new float[7];
  float[] candyY = new float[7];

  float[] badcircleX = new float[3];
  float[] badcircleY = new float[3];

  float[] mustHitX = new float[1];
  float[] mustHitY = new float[1];

  PImage[] backgrounds = new PImage[3];

  ArrayList<Integer> intHighScore = new ArrayList<Integer>();

	
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(700, 550);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(0);
    imgKirbyFalling = loadImage("kirby_falling.png");
    imgKirbyEating = loadImage("Kirby_eating.png");
    imgLives = loadImage("pixel_heart.png");
    imgCandy = loadImage("Candy.png");
    imgBackground0 = loadImage("background0.jpeg");
    imgBackground1 = loadImage("background1.jpg");
    imgBackground2 = loadImage("background2.jpeg");
    imgGameOver = loadImage("game_over.jpeg");
    imgPlayAgain = loadImage("play_again.png");
    imgCoin = loadImage("coin.png");

    imgKirbyFalling.resize(imgKirbyFalling.width/5, imgKirbyFalling.height/5);
    imgKirbyEating.resize(imgKirbyEating.width/7, imgKirbyEating.height/7);
    imgLives.resize(imgLives.width/30, imgLives.height/30);
    imgCandy.resize(imgCandy.width = 50, imgCandy.height = 50);
    imgCoin.resize(imgCoin.width = 50, imgCoin.height = 50);
    imgBackground0.resize(imgBackground0.width = 700, imgBackground0.height = 550);
    imgBackground1.resize(imgBackground1.width = 700, imgBackground1.height = 550);
    imgBackground2.resize(imgBackground2.width = 700, imgBackground2.height = 550);
    imgGameOver.resize(imgGameOver.width = 700, imgGameOver.height = 550);
    imgPlayAgain.resize(imgPlayAgain.width = 175, imgPlayAgain.height = 75);
    System.out.println(imgCandy.width + " " + imgCandy.height);

    backgrounds[0] = imgBackground0;
    backgrounds[1] = imgBackground1;
    backgrounds[2] = imgBackground2;


    for(int i = 0; i < candyY.length; i++){
      candyX[i] = random(width);
      candyY[i] = random(height);
      //System.out.println(circleX[i] + " " + circleY[i]);
    }

    for(int i = 0; i < badcircleY.length; i++){
      badcircleX[i] = random(width);
      badcircleY[i] = random(height);
    }

    for(int i = 0; i < mustHitY.length; i++){
      mustHitX[i] = random(width);
      mustHitY[i] = 0;
    }

    intHighScore.add(0, intHighScore1);
    intHighScore.add(1, intHighScore2);
    intHighScore.add(2, intHighScore3);
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    if(intLives == 3){
      background(backgrounds[0]);
    }
    else if(intLives == 2){
      background(backgrounds[1]);
    }
    else{
      background(backgrounds[2]);
    }

    // Falling white circles
    for(int i = 0; i < candyY.length; i++){
      image(imgCandy, candyX[i], candyY[i]);
      candyY[i]+=2;
      //System.out.println("falling");

      // Respawing circles back at the top once it is out of frame
      if(candyY[i] > 550){
        candyY[i] = 0;
      }
    }

    // Spawining bad circles
    for(int i = 0; i < badcircleY.length; i++){
      fill(255, 0, 0);
      ellipse(badcircleX[i], badcircleY[i], 50, 50);
      badcircleY[i]+= fltbadcircleYMovement;
      //System.out.println(fltbadcircleYMovement);

      if(intScore == 1){
        blnStopSpeed = false;
      }

      if(intScore >  intSpeedBoostScore && blnStopSpeed == false){
        fltbadcircleYMovement += 5;
        blnStopSpeed = true;
        //System.out.println(fltbadcircleYMovement);
      }

      // Once the bad circle reaches 700, it respawns back to the top at a random width
      if(badcircleY[i] > 550){
        badcircleY[i] = 0;
        badcircleX[i] = random(width);
      }
    }


    fltkirbyFallingX += 3;
    fltkirbyFallingY += 3;
	  
    // Moving Kirby and block according to the key that is pressed
      if(keyPressed){
        if(keyCode == RIGHT){
          fltplayerX+= 10;
        }
        else if(keyCode == LEFT){
          fltplayerX-= 10;
        }
        
        if(fltplayerX >= 600){
          fltplayerX = 600;
        }
        else if(fltplayerX <= 0){
          fltplayerX = 0;
        }
      }
    
    // When kirby hits the rectangle, boolKirbySpawn is true
    if(fltkirbyFallingX > fltplayerX && fltkirbyFallingX < fltplayerX + 100 && fltkirbyFallingY >= 480 && blnKirbySpawn == false && blnPlayerAlive == false){
      //System.out.println("It worked");
      blnKirbySpawn = true;
      blnPlayerAlive = true;
    }

    // Kirby spawns on the rectangle
    if(blnKirbySpawn == true && blnPlayerAlive == true){
      //System.out.println("KIRBYYYYYYYYYYYYYYYYYY");
      image(imgKirbyEating, fltplayerX + 18, fltplayerY - 42);
      fltkirbyFallingX += 700;
      fltkirbyFallingY += 700;
    }

    // If the user misses kirby, he respawns back at the top
    else if(fltkirbyFallingY == 525){
      fltkirbyFallingX = 0;
      fltkirbyFallingY = 0;

    }

    // When a circle collides with Kirby, the score goes up by one and the circle respawns at height 0 and a random width
    for(int count = 0; count < candyY.length; count++){
      if(candyY[count] >= fltplayerY - 25 && candyX[count] > fltplayerX - 30 && candyX[count] < fltplayerX + 100 && blnKirbySpawn == true && blnPlayerAlive == true){
       // System.out.println("Hit!");
        candyY[count] = 0;
        candyX[count] = random(width);
        intScore++; 

      }
    }

    // When the player collides with a red circle, the player looses a life
    for(int count = 0; count < badcircleY.length; count++){
      if(badcircleY[count] > fltplayerY && badcircleX[count] > fltplayerX && badcircleX[count] < fltplayerX + 100 && blnKirbySpawn == true && blnPlayerAlive == true){
        badcircleY[count] = 0;
        badcircleX[count] = random(width);
        intLives--;
      }
    }

    // Drops Gold Coin 
    if(intScore > fltMustHitScore && blnKirbySpawn == true && blnPlayerAlive == true){
      for(int count = 0; count < 1; count++){
        image(imgCoin, mustHitX[count], mustHitY[count]);
        mustHitY[count]+= fltMustHitSpeed;
        if(mustHitY[count] > fltplayerY - 25 && mustHitX[count] > fltplayerX && mustHitX[count] < fltplayerX + 100){
          intScore = mustHit(intScore);
          fltMustHitScore += 10;
          //System.out.println("HIT");
          mustHitX[count] = random(width);
          mustHitY[count] = 0;
          fltMustHitSpeed++;
        }
        else if(mustHitY[count] > 550){
          mustHitX[count] = random(width);
          mustHitY[count] = 0;
          intLives--;
        }
      }

    }

    if(intLives == 3){
      image(imgLives, fltlives1X, fltlives1Y);
      image(imgLives, fltlives2X, fltlives2Y);
      image(imgLives, fltlives3X, fltlives3Y);
    }

    textSize(40);
    fill(255, 255, 255);
    text(intScore, 550, 100);


    fill(230, 152, 9);
    rect(fltplayerX, fltplayerY, 100, 25);
    image(imgKirbyFalling, fltkirbyFallingX, fltkirbyFallingY);

    if(intLives == 0){

      blnPlayerAlive = false;
      fill(0);
      fltlives1X = 1000;
      fltlives1X = 1000;
      fill(0);
      image(imgGameOver, 0, 0);
      textSize(40);
      fill(255, 255, 255);
      text("Final Score: " + intScore, 223, 100);
      image(imgPlayAgain, intPlayAgainX, intPlayAgainY);
      textSize(20);
      text("High Score: " + + intHighScore.get(0), 0, 20);
      /*
      text("High Score 2: " + + intHighScore.get(1), 0, 50);
      System.out.println(intHighScore2);
      text("High Score 3: " + + intHighScore.get(2), 0, 80);
      */
    }

    else if(intLives == 2){
      fltlives3X = 1000;
      fltlives3Y = 1000;
      image(imgLives, fltlives1X, fltlives1Y);
      image(imgLives, fltlives2X, fltlives2Y);
      image(imgLives, fltlives3X, fltlives3Y);
    }
    
    else if(intLives == 1){
      fltlives2X = 1000;
      fltlives2Y = 1000;
      image(imgLives, fltlives1X, fltlives1Y);
      image(imgLives, fltlives2X, fltlives2Y);
      image(imgLives, fltlives3X, fltlives3Y);
    }



    if(intScore > intHighScore1){
      intHighScore1 = intScore;
      intHighScore.set(0, intHighScore1);
      //System.out.println("Index 1:" + intHighScore.get(0));
    }

    /*
    else if(intScore < intHighScore1 && intScore > intHighScore2 && intScore > intHighScore3){
      intHighScore2 = intScore;
      //int intHighScore2Difference = intHighScore1 - intHighScore2;
      //intHighScore2 = intHighScore2 + intHighScore2Difference;
      System.out.println(intHighScore2);
      intHighScore.set(1, intHighScore2);
      System.out.println("Index 2:" + intHighScore.get(1));
    }

    else if(intScore > intHighScore3 && intScore < intHighScore1 && intScore < intHighScore3){
      intHighScore3 = intScore;
      intHighScore.add(2, intScore);
      System.out.println("Index 3:" + intHighScore.get(3));
    }
    */

    //System.out.println(intScore);
  }

  // define other methods down here.

  public int mustHit(int intScore){
    intScore += 5;
    return intScore;

  }

  public void mouseClicked(){
    if(intLives == 0){
      if(mouseX < intPlayAgainX + 175 && mouseX > intPlayAgainX && mouseY < intPlayAgainY + 75 && mouseY > intPlayAgainY){
        System.out.println("PRESSED");
        blnPlayerAlive = false;
        blnKirbySpawn = false;
        intScore = 0;
        intLives = 3;
        fltlives1X = 625;
        fltlives1Y = 0;
        fltlives2X = 575;
        fltlives2Y = 0;
        fltlives3X = 525;
        fltlives3Y = 0;
        fltbadcircleYMovement = 5;
        fltkirbyFallingX = 0;
        fltkirbyFallingY = 0;
        fltMustHitScore = 10;
        fltMustHitSpeed = 1;
        //blnStopSpeed = false;
      }

    }
  }
}