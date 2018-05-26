package project_recomendation_system;

import java.util.Random;

import javax.swing.JTextPane;

import javafx.scene.chart.PieChart.Data;

public class Data_Matrix {
	
	int[][] data ;
	int M,N,X;
	public Data_Matrix(int m , int n , int x){
		this.M = m;
		this.N = n;
		this.X = x;
		create_matrix();
		
	}
	
	
	
	public void create_matrix(){
		
		data = new int[M][N];
		Random rand = new Random();
	
		
		for(int i=0; i<M; i++){ // fill matrix with numbers 0 --> 5
			for(int j=0; j<N; j++){	
				data[i][j] = rand.nextInt(5) + 1 ;				
			}
		}
		
		for(int i=0; i<M; i++){ // fill matrix with numbers 0 --> 5
			for(int j=0; j<N; j++){	
				data[i][j] = rand.nextInt(6);				
			}
		}
		
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
