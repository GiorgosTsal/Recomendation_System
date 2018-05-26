package project_recomendation_system;




import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** @see https://stackoverflow.com/questions/6067898 */
public class recomendation_system extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JTextField textField_T;
    private JTextField textField_N;
    private JTextField textField_X;
    private JTextField textField_M;
    private JTextField textField_K;

    
    
    Data_Matrix data ;
    private JTextPane textPane;
    
	public  static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("SpinSlider!");
                f.getContentPane().add(new recomendation_system());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
                String UserDir = System.getProperty("user.dir");
                System.out.println(UserDir);
            }
        });
    }

    
	
	
	public recomendation_system() {
       
    	init_components();



    }


    public void readTXT(){
    	
    	String path = System.getProperty("user.dir");
    	path = path + "/CONFIGFILE.TXT";
//    	Scanner fileIn = new Scanner(new File(path));
    	System.out.println(path);
    	
    	try {
			Scanner fileIn = new Scanner(new File(path));
			
			while(fileIn.hasNextLine()){
				
				String  input_data;
				int value ;
								
				input_data = fileIn.nextLine() ;
				switch(input_data.charAt(0)){
					
				case 'T' : textField_T.setText(input_data.substring(1, input_data.length())); break ;
				case 'N' : textField_N.setText(input_data.substring(1, input_data.length())); break ;
				case 'M' : textField_M.setText(input_data.substring(1, input_data.length())); break ;
				case 'X' : textField_X.setText(input_data.substring(1, input_data.length())+"%"); break ;
				case 'K' : textField_K.setText(input_data.substring(1, input_data.length())); break ;
					
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	
    }
    
    
    public void setTxt(){
    	String path = System.getProperty("user.dir");
    	path = path + "/CONFIGFILE.TXT";
        File logFile = new File(path);

        try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			writer.write("T" + textField_T.getText()+"\n");
			writer.write("N" + textField_N.getText()+"\n");
			writer.write("M" + textField_M.getText()+"\n");
			writer.write("X" + textField_X.getText().substring(0,textField_X.getText().length()-1)+"\n");
			writer.write("K" + textField_K.getText()+"\n");
			writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

    
    public void init_components(){


         
         JPanel panel = new JPanel();

         textPane = new JTextPane();
         GroupLayout groupLayout = new GroupLayout(this);
         groupLayout.setHorizontalGroup(
         	groupLayout.createParallelGroup(Alignment.LEADING)
         		.addGroup(groupLayout.createSequentialGroup()
         			.addContainerGap()
         			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
         			.addGap(29)
         			.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
         			.addGap(28))
         );
         groupLayout.setVerticalGroup(
         	groupLayout.createParallelGroup(Alignment.LEADING)
         		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
         			.addContainerGap()
         			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
         				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
         				.addComponent(textPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
         			.addGap(150))
         );
         panel.setLayout(new GridLayout(0, 2, 0, 0));
         
         JLabel lblInputData = new JLabel("input data");
         panel.add(lblInputData);
         
         JLabel lblNewLabel_1 = new JLabel("New label");
         panel.add(lblNewLabel_1);
         
         JLabel label = new JLabel("T:");
         panel.add(label);
         
         textField_T = new JTextField();
         panel.add(textField_T);
         textField_T.setColumns(10);
         
         JLabel lblNewLabel = new JLabel("N:");
         panel.add(lblNewLabel);
         
         textField_N = new JTextField();
         panel.add(textField_N);
         textField_N.setColumns(10);
         
         JLabel lblM = new JLabel("M");
         panel.add(lblM);
         
         textField_M = new JTextField();
         panel.add(textField_M);
         textField_M.setColumns(10);
         
         JLabel lblX = new JLabel("X:");
         panel.add(lblX);
         
         textField_X = new JTextField();
         panel.add(textField_X);
         textField_X.setColumns(10);
         
         JLabel lblK = new JLabel("K:");
         panel.add(lblK);
         
         textField_K = new JTextField();
         panel.add(textField_K);
         textField_K.setColumns(10);
         
         JButton btnNewButton = new JButton("set values");
         btnNewButton.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		setTxt();
         	}
         });
         panel.add(btnNewButton);
         
         JButton btnNewButton_1 = new JButton("read values");
         btnNewButton_1.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		readTXT();
                int m = Integer.parseInt(textField_M.getText());
                int n = Integer.parseInt(textField_N.getText());
                int x = Integer.parseInt(textField_X.getText().substring(0, textField_X.getText().length()-1));
                data = new Data_Matrix(m, n, x);
                data.print_data_matrix();
                data.print_data_matrix_graphics(textPane);
         	}
         });
         panel.add(btnNewButton_1);
         setLayout(groupLayout);
    	
    	
    	
    }
}




