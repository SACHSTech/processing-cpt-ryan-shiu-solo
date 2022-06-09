import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	PImage imgKirbyFalling;
  PImage imgKirbyEating;
  PImage imgLives;

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

  int intLives = 3;
  int intScore = 0;

  boolean blnPlayerAlive = false;
  boolean blnKirbySpawn = false;
  boolean blnLevel1 = false;
  boolean blnStopSpeed = false;

  float[] circleX = new float[7];
  float[] circleY = new float[7];

  float[] badcircleX = new float[3];
  float[] badcircleY = new float[3];

  
	
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

    imgKirbyFalling.resize(imgKirbyFalling.width/5, imgKirbyFalling.height/5);
    imgKirbyEating.resize(imgKirbyEating.width/7, imgKirbyEating.height/7);
    imgLives.resize(imgLives.width/30, imgLives.height/30);
    System.out.println(imgLives.width + imgLives.height);


    for(int i = 0; i < circleY.length; i++){
      circleX[i] = random(width);
      circleY[i] = random(height);
      System.out.println(circleX[i] + " " + circleY[i]);
    }

    for(int i = 0; i < badcircleY.length; i++){
      badcircleX[i] = random(width);
      badcircleY[i] = random(height);
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(0);

    // Falling white circles
    for(int i = 0; i < circleY.length; i++){
      ellipse(circleX[i], circleY[i], 50 ,50);
      circleY[i]+=2;
      //System.out.println("falling");

      // Respawing circles back at the top once it is out of frame
      if(circleY[i] > 550){
        circleY[i] = 0;
      }
    }

    // Spawining bad circles
    for(int i = 0; i < badcircleY.length; i++){
      fill(255, 0, 0);
      ellipse(badcircleX[i], badcircleY[i], 50, 50);
      badcircleY[i]+= fltbadcircleYMovement;
      System.out.println(fltbadcircleYMovement);

      if(intScore % 2 == 0 && blnStopSpeed == false){
        fltbadcircleYMovement += 5;
        blnStopSpeed = true;
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
    for(int count = 0; count < circleY.length; count++){
      if(circleY[count] > fltplayerY && circleX[count] > fltplayerX && circleX[count] < fltplayerX + 100 && blnKirbySpawn == true && blnPlayerAlive == true){
       // System.out.println("Hit!");
        circleY[count] = 0;
        circleX[count] = random(width);
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

    if(intLives == 0){
      blnPlayerAlive = false;
      fill(0);

    }

    else if(intLives == 2){
      fltlives3X = 1000;
      fltlives3Y = 1000;
    }
    
    else if(intLives == 1){
      fltlives2X = 1000;
      fltlives2Y = 1000;
    }

    
    
    image(imgLives, fltlives1X, fltlives1Y);
    image(imgLives, fltlives2X, fltlives2Y);
    image(imgLives, fltlives3X, fltlives3Y);


    fill(230, 152, 9);
    rect(fltplayerX, fltplayerY, 100, 25);
    image(imgKirbyFalling, fltkirbyFallingX, fltkirbyFallingY);

    textSize(40);
    fill(255, 255, 255);
    text(intScore, 550, 100);
  }
  
  // define other methods down here.

}