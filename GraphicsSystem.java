package turtle;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import uk.ac.leedsbeckett.oop.LBUGraphics;



public class GraphicsSystem extends LBUGraphics 
{
	   
        public static void main(String[] args)
        {
                new GraphicsSystem(); //create instance of class that extends LBUGraphics (could be separate class without main), gets out of static context
        }

        public GraphicsSystem() 
        {
            JFrame MainFrame = new JFrame();                //create a frame to display the turtle panel on
            MainFrame.setLayout(new FlowLayout());      
            MainFrame.add(this);                                        //"this" is this object that extends turtle graphics so we are adding a turtle graphics panel to the frame
            MainFrame.pack();                                           //set the frame to a size we can see
            MainFrame.setVisible(true);
            
            
            button();
            button2();
            button3();
            button4();
            about(); 
			
            //GUI warning Dialouge
            MainFrame.addWindowListener(new WindowAdapter() 
	        {
	        	public void windowClosing(WindowEvent e) 
		        {
	        		int jo = JOptionPane.showConfirmDialog(MainFrame, "Did you Save Image before closing?", "Warning!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);	
	        		if (jo == JOptionPane.YES_OPTION) {
	                			System.exit(0);
	                }
	                else {
	                	MainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	                	JOptionPane.showConfirmDialog(MainFrame, "Click on Save Image", "Info!!!", JOptionPane.PLAIN_MESSAGE);
	                }	
	            }
	        });
        }
        
        //method to save commands
        public void save(String command) {
        	  
    		try {
    			FileWriter fileWriter = new FileWriter("textfile.txt", true);  //creating and appending file
    			fileWriter.write(command + "\n");
    			System.out.println("file created");
    			fileWriter.close();
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}   	
  		}   
         
        //Overriden about method
        @Override
        public void about()
        {
			super.about();  //calling about function of parent class
        	displayMessage("Anush"); //displays message containing my name
        }
        
        private int radius;
        //store radius value
        public  void setRadius(int radius){
            this.radius = radius;
        }
        //get radius
        public  int getRadius(){
            return this.radius;
        }
        
        //overriden circle method
        @Override
        public void circle(int radius)
        {
        	penDown(); 
        	Graphics2D g = getGraphics2DContext(); //new graphics2D object created
        	g.setColor(PenColour); //sets pencolour
        	g.drawOval(getxPos(), getyPos(), radius, radius); //draws circle
        	setRadius(radius); 
        }
        
        //fill method to draw filled circle
        public void fill() {
        	Graphics2D g = getGraphics2DContext();
        	g.setColor(PenColour);
        	g.fillOval(getxPos(), getyPos(), getRadius(), getRadius()); //fills circle
        }
        
        //pen command to set RGB color
        public void RGBpen(int color1, int color2, int color3) {
        	setPenColour(new Color(color1, color2, color3)); 
        }
        
        //triangle command with one parameter
        public void tirangle(String triSize)
        {
				penDown();
				for (int i = 0; i < 3; i++) {
					forward(triSize);
					turnRight(120);
				}
        }
        
        //triangle command with three parameter
        public void polygon(int side1 , int side2, int side3) {
        	penDown();
        	
        	//angle for scalen triangle
        	int arc1 = (int) Math.round(Math.toDegrees(Math.acos((Math.pow(side2, 2) + Math.pow(side3, 2) - Math.pow(side1, 2)) / (2 * side2 * side3))));
        	int arc2 = (int) Math.round(Math.toDegrees(Math.acos((Math.pow(side1, 2) + Math.pow(side3, 2) - Math.pow(side2, 2)) / (2 * side1 * side3))));
        	int arc3 = (int) Math.round(Math.toDegrees(Math.acos((Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(side3, 2)) / (2 * side1 * side2))));
        	
        	//different degree
        	arc1 = 180 - arc1;
        	arc2 = 180 - arc2;
        	arc3 = 180 - arc3;
        
	        forward(side3);
			turnRight(arc2);
			forward(side1);
			turnRight(arc3);
			forward(side2);
			turnRight(arc1);					
        }
        
        //method to save image
        public void savImg() {
			BufferedImage bi= new BufferedImage(1000, 600, BufferedImage.TYPE_INT_RGB);  
			File imageFile = new File("image.png"); 	//creates png file
		    
			//draws graphics in image
			Graphics2D drawing= bi.createGraphics(); 	
		 	printAll(drawing);
        	try {
    		    
    		    ImageIO.write(bi, "png", imageFile);
    		    System.out.println("image saved");
    		} catch (IOException e) {
    			e.printStackTrace();
    	}
        }
        
        //button to save image
        public void button (){
        	JButton but = new JButton("Save Image");          
            this.add(but);
            but.addActionListener(e1->{
            	savImg();  //savImg method called
            });
        }
       
        //button to draw random shape
		public void button2 (){
        	JButton but = new JButton("Random shape");          
            this.add(but);
            but.addActionListener(e1->{
            	penDown();
            	Random rn = new Random();
            	int ran = rn.nextInt(360) + 100;
            	int size = rn.nextInt(200) + 100; 
            	int degree  = ran * 4;
            	int side = rn.nextInt(20) + 4;
            	for (int i = 0; i < side; i++) {
            		forward(size);
					turnLeft(degree);
					
			}  
            });
        }
       
		//button to change pen colour using color chooser from color class 
        public void button3 (){
        	JButton but = new JButton("Pen Color");          
            this.add(but);
            but.addActionListener(e1->{
            	Color color = JColorChooser.showDialog(null, "Pick a Color", Color.black);
                setPenColour(color);
                save(String.valueOf(color));  
            });
        }
        
      //button to change panel colour using color chooser from color class
        public void button4 (){
        	JButton but = new JButton("Panel Color");          
            this.add(but);
            but.addActionListener(e1->{
            	Color color = JColorChooser.showDialog(null, "Pick a Color", Color.black);
            	setBackground_Col(color);
                save(String.valueOf(color));
                clear();
            });
        }
        
        //method to call three parameter
        static String getNbr(String str) 
        { 
            // Replace each non-numeric number with a space
            str = str.replaceAll("[^\\d]", " "); 
            // Remove leading and trailing spaces
            str = str.trim(); 
            // Replace consecutive spaces with a single space
            str = str.replaceAll(" +", " "); 
            if (str.equals(""))
		           return "-1";
            return str; 
        } 
        
        public void processCommand(String command)
        {
        	command = command.toLowerCase();
        		
        		save(command);
        		
        		//replaces everything except 0-9 and stores in a string varriable
        		String Size = command.replaceAll("[^0-9]", "");  
        		String rad = command.replaceAll("[^0-9]", "");
        		String intValue = command.replaceAll("[^0-9]", "");
               	
        		//getNbr method called
        		String str = getNbr(command);                                  
        		String [] array = str.split(" ");
        		int [] intarr = new int [array.length];
        		for(int i=0; i<array.length; i++) {
        			intarr[i] = Integer.parseInt(array[i]);
	        	}
        		    		
        		boolean isNumeric = intValue.matches("-?\\d+(\\.\\d+)?"); //checks whether intvalue has numeric value or not
              		
        		    //command support
	                if (command.equals("about")){
	                	clearInterface();
	                	about();
	                	
	                }
	                
	                else if (command.equals("clear")) {
	                	clearInterface();
	                	clear();
	                	
	                }
	                
	                else if (command.equals("penup")) {
	                	clearInterface();
	                	penUp();
	                	
	                }
	                
	                else if (command.equals("pendown")) {
	                	clearInterface();
	                	penDown();
	                	
	                }
	                                
	                else if (command.equals("turnleft" + " " + intValue)) {
	                	clearInterface();
	                	turnLeft(intValue);
	                	
	                }
	                
	                else if (command.equals("turnright" + " " + intValue)) {
	                	clearInterface();
	                	turnRight(intValue);
	                }
	                
	                else if (command.equals("forward" + " " + intValue)) {
	                	clearInterface();
	                	forward(intValue);
	                	
	                }
	                
	                else if (command.equals("backward" + " " + intValue)) {
	                	clearInterface();
	                	forward("-" + intValue);
	                	
	                }
	             
	                else if (command.equals("red")) {
	                	clearInterface();
	                	setPenColour(Color.red);
	                }
	                
	                else if (command.equals("green")) {
	                	clearInterface();
	                	setPenColour(Color.green);
	                }
	                
	                else if (command.equals("black")) {
	                	clearInterface();
	                	setPenColour(Color.black);
	                }
	                
	                else if (command.equals("white")) {
	                	clearInterface();
	                	setPenColour(Color.white);
	                }
	                
	                else if (command.equals("reset")) {
	                	clearInterface();
	                	reset();
	                }
	                
	                
	                else if (command.equals("circle" + " " + rad)) {
	                	int radius = Integer.parseInt(rad); //converts rad to integer
	                	clearInterface();
						circle(radius);
	                }
	                
	                else if (command.equals("fill")) {
	                	clearInterface();
	                	fill();
	                }
	                
	                else if (command.equals("triangle" + " " + Size)) {
	                	clearInterface();
	                	tirangle(Size);
	                	
	                }
	                
	                else if(command.contains("rgb") && intarr.length == 3) {
		                
	                	if (command.equals("rgb" + " " + intarr[0] + " " + intarr[1] + " " + intarr[2])) {
	                		clearInterface();
	                		RGBpen(intarr[0], intarr[1], intarr[2]);
	                	}
	                }
                
	                
	                else if(command.contains("polygon") && intarr.length == 3) {
	                
	                   if (command.equals("polygon" + " " + intarr[0] + " " + intarr[1] + " " + intarr[2])) {
		                	clearInterface();
		                	polygon(intarr[0], intarr[1], intarr[2]);
		                }                 
	                }
	                else if (command.equals("turnleft") || command.equals("turnright") || command.equals("forward") || command.equals("backward")) {
	                	displayMessage("Parameter Missing");
	                }
	             
	                else if ((isNumeric) || (intValue.isEmpty())) {
	                	displayMessage("ERROR!!! Non Numeric or Non sensible Values");
	                }
	                
	                else {
	                	displayMessage("Invalid Commands");
	                }  
        		}         
       }