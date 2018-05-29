package project_recomendation_system;




import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class recomendation_system extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    
    static GUI gui;
    static Data_Matrix data,data_predicted ;
    static int T,N,M,X,K;
    
	public  static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	recomendation_system project = new recomendation_system();
                JFrame f = new JFrame("recomendation system");
                f.getContentPane().add(project.gui);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
            }
        });
    }

    
	
	
	public recomendation_system() {
		gui = new GUI();

    }

	
	public static void find_all_neighbors(){
		Nearest_Neighbor[] all_neighbors = new Nearest_Neighbor[data.N];
		for(int j=0; j<data.N; j++){
			all_neighbors[j] = new Nearest_Neighbor(data.M, "max");
			all_neighbors[j] = compute_cosine(j);
		}
		
//		float sum = 0;
//		all_neighbors[0].print_matrix();
//        for(int i=0; i< data.M; i++){
//            if(data.data[i][0]==-1){
//            	System.out.println("found null at : "+ i);
//            	for(int k=0; k<K; k++){
//            		sum = sum + all_neighbors[0].matrix[k].value*data.data[i][all_neighbors[0].matrix[k].index];
//            	}
//            	sum = sum / all_neighbors[0].get_sum_similaty();
//            	System.out.println("new value: " + sum );
//            }
//            
//        }
		///////////////////////////////
        for(int i=0; i< data.M; i++){
        	for(int j=0; j<data.N; j++){
        		data_predicted.data[i][j] = data.data[i][j];
        		if(data.data[i][j]==-1){
        			data_predicted.data[i][j] = (int)(find_predicted_val(all_neighbors[j], data, i, j));
        		}
        	}
        }
        data_predicted.print_data_matrix();
        
	}
	
	public static float find_predicted_val(Nearest_Neighbor geitones , Data_Matrix data , int i , int j){
		float sum = 0;
		float sum_sim=0;
		for(int k=0; k<geitones.size; k++){
			if(data.data[i][geitones.matrix[k].index]!=-1){
				sum = sum + geitones.matrix[k].value*data.data[i][geitones.matrix[k].index];
				sum_sim = sum_sim + geitones.matrix[k].value;
			}
		}
		
		
		float val = sum / sum_sim;
		return val ;
	}
	
	public static Nearest_Neighbor compute_cosine(int index_vector){
		int howMany = K;
        Nearest_Neighbor neighbors = new Nearest_Neighbor(howMany, "max");
        double[] vector1,vector2;
        vector1 = new double[data.M];
        vector2 = new double[data.M];
        
        
        
        for(int i=0; i< data.M; i++){
            vector1[i] = data.data[i][index_vector];
            
        }
        
        for(int j=0; j<data.N; j++){ // find all similaritis 
        	
        	if(index_vector!=j ){
	            for(int i=0; i< data.M; i++){
	                vector2[i] = data.data[i][j];
	            
	            } 
	            double val = Cosine_similarity.compute(vector1, vector2);
	            neighbors.add_new_value(j, (float)val);
	
	        }
        }
//        neighbors.print_matrix();
        return neighbors ;
	}

    public static void readTXT(){
    	
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
		
				case 'T' : T = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'N' : N = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'M' : M = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'X' : X = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'K' : K = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
					
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	
    }
    
    
    public static void setTxt(){
    	String path = System.getProperty("user.dir");
    	path = path + "/CONFIGFILE.TXT";
        File logFile = new File(path);

        try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			writer.write("T" + gui.textField_T.getText()+"\n");
			writer.write("N" + gui.textField_N.getText()+"\n");
			writer.write("M" + gui.textField_M.getText()+"\n");
			writer.write("X" + gui.textField_X.getText().substring(0,gui.textField_X.getText().length()-1)+"\n");
			writer.write("K" + gui.textField_K.getText()+"\n");
			writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static void createMatrix(){
        data = new Data_Matrix(M, N, X);
        data.print_data_matrix();
        data.print_data_matrix_graphics(gui.textPane);
        
        data_predicted = new Data_Matrix(M, N, X);
//        data_predicted.print_data_matrix();
//        data_predicted.print_data_matrix_graphics(gui.textPane);
    }
    
}


