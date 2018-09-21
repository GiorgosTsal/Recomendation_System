package project_recomendation_system;

public class Nearest_Neighbor {// keep a matrix with the K Nearest Neighbors 
	
	int size ;
	String min_max;
	Tuple[] matrix;
	Nearest_Neighbor(int howMany , String min_max){
		
		this.min_max = min_max;
		size = howMany;
		matrix = new Tuple[size];
		
		 // initialize my values
			switch(this.min_max){	
			case "max":
				for(int i=0; i<size; i++){
					matrix[i] = new Tuple(0,-1000);
				}
				break;
			case "min":
				for(int i=0; i<size; i++){
					matrix[i] = new Tuple(0,1000);
				}
				break ;
				
			}
			
		
		
	}
	
	
	public void add_new_value(int index ,float value ){ // this function handles new data
														// checks if our new data has to be added to matrix and then we add it
		
		
		
		Tuple temp ;
			
		switch(this.min_max){
		
		case "max": 
			temp = get_min(); // compare our minimum matrix value with new data
			if(temp.value<value){
				matrix[temp.index].add(index, value);
			}
			break;
		
		case "min":
			temp = get_max();
			if(temp.value>value){
				matrix[temp.index].add(index, value);
			}
			break;
		}
	}
	
	public float get_sum_similaty(){
		float sum =0;
		for(int i=0; i<size; i++){
			sum = sum + matrix[i].value;
		}
		return sum ;
	}
	
	public Tuple get_min(){
		float val = 10000;
		int indexx = -1 ;
		for(int i=0; i<size; i++){ // find minimum value
			if(matrix[i].value < val ){
				val = matrix[i].value ;
				indexx = i ;
			}
		}
		
		if(indexx==-1){ // just in case something does not go right
			System.err.println("something went wrong!");
		}
		
		return new Tuple(indexx,val);
	}
	
	public Tuple get_max(){
		float val = -1000;
		int index = -1 ;
		for(int i=0; i<size; i++){ // find minimum value
			if(matrix[i].value > val ){
				val = matrix[i].value ;
				index = i ;
			}
		}
		
		if(index==-1){ // just in case something does not go right
			System.err.println("something went wrong!");
		}
		
		return new Tuple(index,val);
	}
	
	public void print_matrix(){
		System.out.println("nearest neighbors");
		for(int i=0 ; i<size; i++){
			System.out.println("index: " + matrix[i].index + " value: " + matrix[i].value);
		}
		
	}


}



class Tuple{
	
	float value;
	int index;
	
	public Tuple(int index, float value){
		this.index = index ;
		this.value = value ;
	}
	
	public void add(int index,float value){
		this.index = index;
		this.value = value;
	}
}