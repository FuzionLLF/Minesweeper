import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.guido.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {


private int NUM_ROWS = 20;
private int NUM_COLS = 20;//Declare and initialize NUM_ROWS and NUM_COLS = 20
private MSButton[][] buttons; //2d array of minesweeper buttons
private ArrayList <MSButton> bombs; //ArrayList of just the minesweeper buttons that are mined
public void setup ()
{
    size(400, 400);
    textAlign(CENTER,CENTER);
    
    // make the manager
    Interactive.make( this );
    
    
    bombs = new ArrayList<MSButton>();
    buttons = new MSButton[NUM_ROWS][NUM_COLS];
    for(int r = 0;r<NUM_ROWS;r++)
    {
        for(int c=0;c<NUM_COLS;c++)
        {
            buttons[r][c] = new MSButton(r,c);
        }
    }//declare and initialize buttons
    setBombs();
}
public void setBombs()
{
for(int i =0;i<1;i++)
    {
   int row = (int)(Math.random()*NUM_ROWS);
   int col = (int)(Math.random()*NUM_COLS);
    if(bombs.contains(buttons[row][col])==false)
    {
        bombs.add(buttons[row][col]);
    }
    }//your code
}
public void draw ()
{
    background( 0 );
    if(isWon())
        displayWinningMessage();
}
public boolean isWon()
{
    for(int r = 0; r < NUM_ROWS; r++)
        for(int c = 0; c < NUM_COLS; c++)   
            if(!buttons[r][c].isMarked() && !buttons[r][c].isClicked())
            {
                return false;
            }//your code here
    return true;
}
public void displayLosingMessage()
{
    buttons[9][6].setLabel("Y");
    buttons[9][7].setLabel("O");
    buttons[9][8].setLabel("U");
    buttons[9][9].setLabel(" ");
    buttons[9][10].setLabel("L");
    buttons[9][11].setLabel("O");
    buttons[9][12].setLabel("S");
    buttons[9][13].setLabel("E");
//your code here
}
public void displayWinningMessage()
{
    buttons[9][6].setLabel("Y");
    buttons[9][7].setLabel("O");
    buttons[9][8].setLabel("U");
    buttons[9][9].setLabel(" ");
    buttons[9][10].setLabel("W");
    buttons[9][11].setLabel("I");
    buttons[9][12].setLabel("N");
    buttons[9][13].setLabel("");//your code here
}
public class MSButton
{
    private int r, c;
    private float x,y, width, height;
    private boolean clicked, marked;
    private String label;
    
    public MSButton ( int rr, int cc )
    {
        width = 400/NUM_COLS;
        height = 400/NUM_ROWS;
        r = rr;
        c = cc; 
        x = c*width;
        y = r*height;
        label = "";
        marked = clicked = false;
        Interactive.add( this ); // register it with the manager
    }
    public boolean isMarked()
    {
        return marked;
    }
    public boolean isClicked()
    {
        return clicked;
    }
    // called by manager
    
    public void mousePressed () 
    {
        clicked = true;
        if(keyPressed==true)
            marked = !marked;
        else if(bombs.contains(this))
            displayLosingMessage();
        else if(countBombs(r,c) > 0)
            label = "" + countBombs(r,c);
        else
        { 
        if(isValid(r,c-1)&&buttons[r][c-1].clicked == false){//left
        buttons[r][c-1].mousePressed();
        if(isValid(r-1,c-1)&&buttons[r-1][c-1].clicked == false){//top left
        buttons[r-1][c-1].mousePressed();
        }
        if(isValid(r-1,c)&&buttons[r-1][c].clicked == false){//top
        buttons[r-1][c].mousePressed();
        }
        if(isValid(r-1,c+1)&&buttons[r-1][c+1].clicked == false){//top right
        buttons[r-1][c+1].mousePressed();
        }
        if(isValid(r-1,c-1)&&buttons[r-1][c-1].clicked == false){//right
        buttons[r-1][c-1].mousePressed();
        }
        if(isValid(r+1,c+1)&&buttons[r+1][c+1].clicked == false){//bottom right
        buttons[r+1][c+1].mousePressed();
        }
        if(isValid(r+1,c)&&buttons[r+1][c].clicked == false){//bottom
        buttons[r+1][c].mousePressed();
        }
        if(isValid(r+1,c-1)&&buttons[r+1][c-1].clicked == false){//bottom left
        buttons[r+1][c-1].mousePressed();
        }
        }
    }
        
        
        //your code here
    }
    public void draw () 
    {    
        if (marked)
            fill(0);
        else if( clicked && bombs.contains(this) ) 
            fill(255,0,0);
        else if(clicked)
            fill( 200 );
        else 
            fill( 100 );
        rect(x, y, width, height);
        fill(0);
        text(label,x+width/2,y+height/2);
    }
    public void setLabel(String newLabel)
    {
        label = newLabel;
    }
    public boolean isValid(int r, int c)
    {
        if(r>=NUM_ROWS){
            return false;
        }
        if(r<0){
            return false;
        }
        if(c>=NUM_COLS){
            return false;
        }
        if(c<0){
            return false;
        }
        return true;
    //your code here
    }
    public int countBombs(int row, int col)
    {
        int numBombs = 0;
        if(isValid(r,c-1)&&bombs.contains(buttons[r][c-1]))//left
            numBombs++;
        if(isValid(r-1,c-1)&&bombs.contains(buttons[r-1][c-1]))//top left
            numBombs++;
        if(isValid(r-1,c)&&bombs.contains(buttons[r-1][c]))//top
            numBombs++;
        if(isValid(r-1,c+1)&&bombs.contains(buttons[r-1][c+1]))//top right
            numBombs++;
        if(isValid(r,c+1)&&bombs.contains(buttons[r][c+1]))//right
            numBombs++;
        if(isValid(r+1,c+1)&&bombs.contains(buttons[r+1][c+1]))//botton right
            numBombs++;
        if(isValid(r+1,c)&&bombs.contains(buttons[r+1][c]))//bottom
            numBombs++;
        if(isValid(r+1,c-1)&&bombs.contains(buttons[r+1][c-1]))//
            numBombs++;
        //your code here
        return numBombs;
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
