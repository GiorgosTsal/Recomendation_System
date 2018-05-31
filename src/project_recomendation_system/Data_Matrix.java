package project_recomendation_system;

import java.util.Random;

import javax.swing.JTextPane;

public class Data_Matrix {
	
	int[][] data ;
	int M,N,X;
	public Data_Matrix(int m , int n , int x){
		this.M = m;
		this.N = n;
		this.X = x;
		create_matrix();
		
	}
	
	
	
	public Data_Matrix get_error_matrix(Data_Matrix prediction){
		
		Data_Matrix error = new Data_Matrix(this.M, this.N, this.X); // initialize matrix 
		
        for(int i=0; i< this.M; i++){
        	for(int j=0; j<this.N; j++){
        		if(data[i][j] !=-1){
        			error.data[i][j] = Math.abs(data[i][j] - prediction.data[i][j]);
        		}
        		else{
        			error.data[i][j] = -1 ;
        		}
        	}
        }
        
        return error ;
	}
	
	public float get_total_error(Data_Matrix prediction){
		
		Data_Matrix error = new Data_Matrix(this.M, this.N, this.X);
		
		error = get_error_matrix(prediction);
		float sum =0 ;
		int total = 0 ; 
        for(int i=0; i< this.M; i++){
        	for(int j=0; j<this.N; j++){
        		if(data[i][j] !=-1){
        			sum = sum +error.data[i][j];
        			total +=1 ;
        		}
        	}
        }
        
        return sum/total ;
		
	}
	
	
	
	public void create_matrix(){
		
		data = new int[M][N];
		Random rand = new Random();
	
		
		for(int i=0; i<M; i++){ // fill matrix with numbers 0 --> 5
			for(int j=0; j<N; j++){	
				data[i][j] = rand.nextInt(5) + 1 ;				
			}
		}
		
		
//		for(int i=0; i<M; i++){ // fill matrix with numbers 0 --> 5
//			for(int j=0; j<N; j++){	
//				if(i<M/2){
//					data[i][j] = 5 ;
//				}
//				else{
//					data[i][j] = 5 ;
//				}
//				
//			}
//		}
		
		float num_of_to_elements_remove = this.M*this.N*(100 - this.X)/100 ;
		
		while(num_of_to_elements_remove!=0){
			
			int i = rand.nextInt(this.M);
			int j = rand.nextInt(this.N);
			
			if(data[i][j]!= -1){
				data[i][j] = -1;
				num_of_to_elements_remove -= 1;
			}
			
		}
		
	}
	
	public void print_data_matrix(){
	
		for(int i=0; i<M; i++){
			for(int j=0; j<N; j++){
				
				System.out.print(data[i][j] + " " );			
			}
			System.out.println();
		}
		
	}
	
	
	public void print_data_matrix_graphics(JTextPane panel){
		String data_string = ""; 
		for(int i=0; i<M; i++){
			for(int j=0; j<N; j++){
//				data_string = data_string + data[i][j] + "\t" ;
				if(data[i][j]!=-1){
					data_string = data_string + data[i][j] + "\t" ;
				}
				else{
					data_string = data_string + "null" + "\t" ; 
				}

			}
			data_string = data_string +"\n" ;
		}
		panel.setText(data_string);
	}
	

}