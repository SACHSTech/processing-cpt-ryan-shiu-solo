import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	PImage imgKirbyFalling;
  PImage imgKirbyEating;
  PImage imgLives;

  float playerX = 350;
  float playerY = 500;
  float kirbyFallingX = 0;
  float kirbyFallingY = 0; 
  float lives1X = 625;
  float lives1Y = 0;
  float lives2X = 575;
  float lives2Y = 0;
  float lives3X = 525;
  float lives3Y = 0;

  int intLives = 3;
  int intScore = 0;

  boolean boolPlayerAlive = false;
  boolean boolKirbySpawn = false;
  boolean boolLevel1 = false;

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
      badcircleY[i]+=5;

      // Once the bad circle reaches 700, it respawns back to the top at a random width
      if(badcircleY[i] > 550){
        badcircleY[i] = 0;
        badcircleX[i] = random(width);
      }
    }


    kirbyFallingX += 3;
    kirbyFallingY += 3;
	  
    // Moving Kirby and block according to the key that is pressed
      if(keyPressed){
        if(keyCode == RIGHT){
          playerX+= 10;
        }
        else if(keyCode == LEFT){
          playerX-= 10;
        }
        
        if(playerX >= 600){
          playerX = 600;
        }
        else if(playerX <= 0){
          playerX = 0;
        }
      }
    
    // When kirby hits the rectangle, boolKirbySpawn is true
    if(kirbyFallingX > playerX && kirbyFallingX < playerX + 100 && kirbyFallingY >= 480 && boolKirbySpawn == false && boolPlayerAlive == false){
      //System.out.println("It worked");
      boolKirbySpawn = true;
      boolPlayerAlive = true;
    }

    // Kirby spawns on the rectangle
    if(boolKirbySpawn == true && boolPlayerAlive == true){
      //System.out.println("KIRBYYYYYYYYYYYYYYYYYY");
      image(imgKirbyEating, playerX + 18, playerY - 42);
      kirbyFallingX += 700;
      kirbyFallingY += 700;
    }

    // If the user misses kirby, he respawns back at the top
    else if(kirbyFallingY == 525){
      kirbyFallingX = 0;
      kirbyFallingY = 0;

    }

    // When a circle collides with Kirby, the score goes up by one and the circle respawns at height 0 and a random width
    for(int count = 0; count < circleY.length; count++){
      if(circleY[count] > playerY && circleX[count] > playerX && circleX[count] < playerX + 100 && boolKirbySpawn == true && boolPlayerAlive == true){
        System.out.println("Hit!");
        circleY[count] = 0;
        circleX[count] = random(width);
        intScore++; 

      }
    }

    // When the player collides with a red circle, the player looses a life
    for(int count = 0; count < badcircleY.length; count++){
      if(badcircleY[count] > playerY && badcircleX[count] > playerX && badcircleX[count] < playerX + 100 && boolKirbySpawn == true && boolPlayerAlive == true){
        badcircleY[count] = 0;
        badcircleX[count] = random(width);
        intLives--;
      }
    }

    if(intLives == 0){
      boolPlayerAlive = false;
      fill(0);

    }

    else if(intLives == 2){
      lives3X = 1000;
      lives3Y = 1000;
    }
    
    else if(intLives == 1){
      lives2X = 1000;
      lives2Y = 1000;
    }
    
    
    image(imgLives, lives1X, lives1Y);
    image(imgLives, lives2X, lives2Y);
    image(imgLives, lives3X, lives3Y);


    fill(230, 152, 9);
    rect(playerX, playerY, 100, 25);
    image(imgKirbyFalling, kirbyFallingX, kirbyFallingY);

    textSize(40);
    fill(255, 255, 255);
    text(intScore, 550, 100);
  }
  
  // define other methods down here.
}