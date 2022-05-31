import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	PImage imgKirbyFalling;
  PImage imgKirbyEating;
  float playerX = 350;
  float playerY = 500;
  float kirbyFallingX = 0;
  float kirbyFallingY = 0; 
  boolean boolKirbySpawn = false;

	
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
    imgKirbyEating.resize(imgKirbyEating.width/10, imgKirbyEating.height/10);


    System.out.println(imgKirbyEating.width);
    System.out.println(imgKirbyEating.height);
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(0);

    kirbyFallingX += 3;
    kirbyFallingY += 3;
	  
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
    
    if(kirbyFallingX > playerX && kirbyFallingX < playerX + 100 && kirbyFallingY <= playerY){
      System.out.println("It worked");
      boolKirbySpawn = true;
    }

    if(boolKirbySpawn == true){
      System.out.println("KIRBYYYYYYYYYYYYYYYYYY");
      image(imgKirbyEating, playerX + 50, playerY - 25);

    }

    rect(playerX, playerY, 100, 25);
    image(imgKirbyFalling, kirbyFallingX, kirbyFallingY);
  }
  
  // define other methods down here.
}