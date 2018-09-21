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
    static Data_Matrix data,data_predicted,error_matrix ;
    static int T,N,M,X,K;
    static Nearest_Neighbor[] all_neighbors;
    static boolean exercise_A_or_B = false ;
    static boolean reverse = false ;
    
	public  static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//            	recomendation_system project = new recomendation_system();
            	
            	recomendation_system.gui = new GUI();
            	
                JFrame f = new JFrame("recomendation system");
                f.getContentPane().add(recomendation_system.gui);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
            }
        });
    }

    
	
	
	public recomendation_system() {
		gui = new GUI();

    }
	 
	// write txt files 
	// revers matrix 
	// second plot 
	// create jar for execution 
	// ftiaxe kai to kolomatrix sta mpam 
	
	public static void exercise_A(){
		exercise_A_or_B = false ;
		gui.clear.doClick(); // clear plot 
		exercise_step(3,75,"A");
		exercise_step(5,75,"A");
		exercise_step(10,75,"A");
	}
	
	public static void exercise_B(){
		exercise_A_or_B = true ;
		gui.clear.doClick(); // clear plot 
		exercise_step(5,80,"B");
		exercise_step(5,70,"B");
		exercise_step(5,50,"B");
		exercise_step(5,30,"B");
	}
	
	public static void exercise_step(int k , int x,String exersice){
		
		
		// set right parameters for exercise 
		gui.textField_T.setText(Integer.toString(10));
		gui.textField_N.setText(Integer.toString(50));
		gui.textField_M.setText(Integer.toString(50));
		gui.textField_X.setText(Integer.toString(x)+"%");
		gui.textField_K.setText(Integer.toString(k));
		setTxt(); // set txt parameters 
		readTXT(); // read txt....
		
		
       float error_Jacard,error_Cosine,error_Pearson;
       error_Jacard = 	0 ;
       error_Cosine = 	0 ;
       error_Pearson =	0 ;

      
       
		for(int iterations=0; iterations<T; iterations++){
	 		createMatrix(); // creates new matrix 
	
	 		for(int method=1; method<4; method++){
		 		find_all_neighbors(method);
				prediction();
				float error = find_error();
				
				switch(method){
				
				case 1: error_Jacard  += error ;
						write_TXT_data("Jacard", iterations, error);
						break;
				case 2: error_Cosine  += error ;
						write_TXT_data("Cosine", iterations, error);
						break;
				case 3: error_Pearson += error;
						write_TXT_data("Pearson", iterations, error);
						break;
				}
	 		}
	 		
	 		
	 		reverse_matrix(); // reverse matrix 
	 		for(int method=1; method<4; method++){
		 		find_all_neighbors(method);
				prediction();
				float error = find_error();
				
				switch(method){
				
				case 1: error_Jacard  += error ;
						write_TXT_data("Jacard_reverse", iterations, error);
						break;
				case 2: error_Cosine  += error ;
						write_TXT_data("Cosine_reverse", iterations, error);
						break;
				case 3: error_Pearson += error;
						write_TXT_data("Pearson_reverse", iterations, error);
						break;
				}
	 		}
	 		reverse_matrix(); // reverse again matrix so its the original!  

		}
		
		switch(exersice){
		case "A": 
			gui.add_new_data_exercise(1,error_Jacard/T,k);
			gui.add_new_data_exercise(2,error_Cosine/T,k);
			gui.add_new_data_exercise(3,error_Pearson/T,k);
			break;
		case "B":
			gui.add_new_data_exercise(1,error_Jacard/T,x);
			gui.add_new_data_exercise(2,error_Cosine/T,x);
			gui.add_new_data_exercise(3,error_Pearson/T,x);
		}

		
	}
	
	
	public static void reverse_matrix(){
		
		Data_Matrix temp_matrix = new Data_Matrix(M, N, X);
		temp_matrix = data ;
		
		
		int temp = N ;
		N = M ;
		M = temp ;
        data = new Data_Matrix(M, N, X);
        data_predicted = new Data_Matrix(M, N, X);
        error_matrix = new Data_Matrix(M+1, N+1, X);
        
        for(int i=0; i< data.M; i++){
        	for(int j=0; j<data.N; j++){
        		data.data[i][j] = temp_matrix.data[j][i];
    
        	}
        }
        
        data.print_data_matrix_graphics(gui.model); // print our data matrix in gui
		
	}
	
	public static void compute_all_iterations(){
		
		for(int iterations=0; iterations<T; iterations++){
	 		createMatrix(); // creates new matrix 
	 		gui.textPane.setText(gui.textPane.getText() +"\nIteration : "+ iterations);
	 		for(int method=1; method<4; method++){
	 			
		 		String name=null;
		 		
		 		switch(method){
		 		case 1:name = "Jacard"; break ; 
		 		case 2:name = "Cosine"; break ;
		 		case 3:name = "Pearson"; break;
		 		}
		 		
		 		
		 		gui.textPane.setText(gui.textPane.getText() +"\ncomputing : "+ name + " similarity");
		 		
		 		find_all_neighbors(method);
				prediction();
				float error = find_error();
				
				gui.add_new_data(method,error);
				
				
				switch(method){
				case 1:	write_TXT_data("Jacard" ,  iterations , error) ;
				case 2:	write_TXT_data("Cosine" ,  iterations , error) ;
				case 3:	write_TXT_data("Pearson" , iterations , error) ;
				}
				
	 		}

		}
	}
	
	
	
	
	public static void compute_one_iterations(int method){ // computes one iteration
														  // find k-neighbors --> predict new values --> calculate error 
		
	 		createMatrix(); // creates new matrix 
	 		String name=null;
	 		
	 		switch(method){
	 		case 1:name = "Jacard"; break ; 
	 		case 2:name = "Cosine"; break ;
	 		case 3:name = "Pearson"; break;
	 		}
	 		
	 		gui.textPane.setText(gui.textPane.getText() +"\ncomputing : "+ name + " similarity");
	 		
	 		find_all_neighbors(method);
			prediction();
			float error = find_error();		
			gui.add_new_data(method,error);
	}
	
	
	
	
	public static float find_error(){
		
		float error;

		error_matrix = data.get_error_matrix(data_predicted);
		error = data.get_total_error(data_predicted);
		error_matrix.data[M][N] = error ;
		error_matrix.print_data_matrix_graphics(gui.model_error);

        gui.textPane.setText(gui.textPane.getText() +"\ntotal error :" + data.get_total_error(data_predicted) );
		return data.get_total_error(data_predicted);
		
	}

	
	public static void find_all_neighbors(int case_similarity){
		all_neighbors = new Nearest_Neighbor[data.N];
		for(int j=0; j<data.N; j++){
			all_neighbors[j] = new Nearest_Neighbor(data.M, "max");
			all_neighbors[j] = find_k_neighbors(j,case_similarity);
		}
		
	}
	
	public static void prediction(){
		
        for(int i=0; i< data.M; i++){
        	for(int j=0; j<data.N; j++){
        		data_predicted.data[i][j] = data.data[i][j];
        			data_predicted.data[i][j] = (int)(find_predicted_val(all_neighbors[j], data, i, j));
    
        	}
        }
        data_predicted.print_data_matrix_graphics(gui.model_prediction);
//        System.out.println("new predicted matrix!");
//        data_predicted.print_data_matrix();
//		find_error();
		
		
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
	
	public static Nearest_Neighbor find_k_neighbors(int index_vector,int case_similarity){
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
	            double val = similarity.Cosine(vector1, vector2);
	            
	            
	            switch(case_similarity){
	            
	            case 1: val=similarity.Jacard(vector1, vector2);  break ;
	            case 2: val=similarity.Cosine(vector1, vector2);  break ;
	            case 3: val=similarity.Pearson(vector1, vector2); break;
	            
	            }
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
//    	System.out.println(path);
    	
    	try {
			Scanner fileIn = new Scanner(new File(path));
			
			while(fileIn.hasNextLine()){
				
				String  input_data;
								
				input_data = fileIn.nextLine() ;
				switch(input_data.charAt(0)){
		
				case 'T' : T = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'N' : N = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'M' : M = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'X' : X = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
				case 'K' : K = Integer.parseInt(input_data.substring(1, input_data.length())); break ;
					
				}
			}
			
			fileIn.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

    	
    	
    	
    }
    
    
    
    public static void write_TXT_data(String method , int loop , float error){
    	//for every loop 
    	String path = System.getProperty("user.dir");
    	String name = null;
    	if(!exercise_A_or_B){
    		name = "/data exercise A/"+ method+"_"+"K"+Integer.toString(K)+"_"+Integer.toString(loop)+".TXT";
    	}
    	else{
    		name = "/data exercise B/"+ method+"_"+"X"+Integer.toString(X)+"_"+Integer.toString(loop)+".TXT";
    	}
    	
    	path = path + name ;
        File logFile = new File(path);
        System.out.println(path);
        
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			
			writer.write("Method: " + method +"\n");
			writer.write("Iteration : "+ Integer.toString(loop)+"\n");
			writer.write("Total matrix error: " + Float.toString(error)+"\n");
			writer.write("-1 values are null data \n\n\n\n");
			writer.write("********************** DATA MATRIX ********************** \n\n");
			
			
	        for(int i=0; i< data.M; i++){//write data matrix 

	        	for(int j=0; j<data.N; j++){
	        		if(data.data[i][j]!=-1){
	        			writer.write(" "); // fixes alignment of data
	        		}
	        		writer.write(Float.toString(data.data[i][j]) + "\t");
	        		
	        	}
	        	writer.write("\n");
	        }
	        
	        
			writer.write("\n\n\n********************** PREDICTION MATRIX ********************** \n\n");
	        for(int i=0; i< data.M; i++){//write prediction matrix

	        	for(int j=0; j<data.N; j++){

	        		writer.write(Float.toString(data_predicted.data[i][j]) + "\t");
	        		
	        	}
	        	writer.write("\n");
	        }
	        
	        
	        
			writer.write("\n\n\n********************** ERROR MATRIX ********************** \n\n");

	        for(int i=0; i< data.M; i++){//write error matrix

	        	for(int j=0; j<data.N; j++){
	        		if(data.data[i][j]!=-1){
	        			writer.write(" "); // fixes alignment of data
	        		}
	        		writer.write(Float.toString(error_matrix.data[i][j]) + "\t");
	        		
	        	}
	        	writer.write("\n");
	        }
	        
	        
	        
	        
			writer.close(); 

	        	
		} catch (IOException e) {
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
			e.printStackTrace();
		}
    }
    
    
    public static void createMatrix(){
        data = new Data_Matrix(M, N, X);
        data_predicted = new Data_Matrix(M, N, X);
        error_matrix = new Data_Matrix(M+1, N+1, X);
        
        data.print_data_matrix_graphics(gui.model); // print our data matrix in gui
        

//        data_predicted.print_data_matrix();
//        data_predicted.print_data_matrix_graphics(gui.textPane);
    }
    
}




