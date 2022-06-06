import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	PImage imgKirbyFalling;
  PImage imgKirbyEating;
  float playerX = 350;
  float playerY = 500;
  float kirbyFallingX = 0;
  float kirbyFallingY = 0; 
  int intScore = 0;
  boolean boolKirbySpawn = false;
  boolean boolLevel1 = false;

  float[] circleX = new float[15];
  float[] circleY = new float[15];

  
	
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

    imgKirbyFalling.resize(imgKirbyFalling.width/5, imgKirbyFalling.height/5);
    imgKirbyEating.resize(imgKirbyEating.width/5, imgKirbyEating.height/5);

    for(int i = 0; i < circleY.length; i++){
      circleX[i] = random(width);
      circleY[i] = random(height);
      System.out.println(circleX[i] + " " + circleY[i]);
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(0);

    // Falling circles
    for(int i = 0; i < circleY.length; i++){
      ellipse(circleX[i], circleY[i], 50 ,50);
      circleY[i]+=2;
      //System.out.println("falling");

      // Respawing circles back at the top once it is out of frame
      if(circleY[i] > 550){
        circleY[i] = 0;
      }
    }

    kirbyFallingX += 3;
    kirbyFallingY += 3;
	  
    // Moving Kirby and block according to the key that is pressed
    if(keyPressed){
      if(keyCode == RIGHT){
        playerX+= 5;
      }
      else if(keyCode == LEFT){
        playerX-= 5;
      }
      
      if(playerX >= 600){
        playerX = 600;
      }
      else if(playerX <= 0){
        playerX = 0;
      }
    }
    
    if(kirbyFallingX > playerX && kirbyFallingX < playerX + 100 && kirbyFallingY >= 480 && boolKirbySpawn == false){
      //System.out.println("It worked");
      boolKirbySpawn = true;
    }

    if(boolKirbySpawn == true){
      //System.out.println("KIRBYYYYYYYYYYYYYYYYYY");
      image(imgKirbyEating, playerX + 13, playerY - 55);
      kirbyFallingX += 700;
      kirbyFallingY += 700;
    }

    else if(kirbyFallingY == 525){
      kirbyFallingX = 0;
      kirbyFallingY = 0;

    }

    for(int count = 0; count < circleY.length; count++){
      if(circleY[count] > playerY && circleX[count] > playerX && circleX[count] < playerX + 100 && boolKirbySpawn == true){
        System.out.println("Hit!");
        circleY[count] = 0;
        circleX[count] = random(width);
        intScore++;

      }
    }

    fill(230, 152, 9);
    rect(playerX, playerY, 100, 25);
    image(imgKirbyFalling, kirbyFallingX, kirbyFallingY);

    textSize(40);
    fill(255, 255, 255);
    text(intScore, 550, 100);
  }
  
  // define other methods down here.
}