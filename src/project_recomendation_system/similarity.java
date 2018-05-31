package project_recomendation_system;

public class similarity {

	
	
	public static Double Jacard(double[] vector1 , double[] vector2){
		
		Double join = 0.0;
		Double section = 0.0;
		
		if(vector1.length != vector2.length){
			System.err.println("at computation of cosine similarity vectors are not the same length!");
			return 0.0 ;
		}
		int length = vector1.length;
		
		for(int index=0; index<length; index++){
			
			if(vector1[index]!= -1 && vector2[index]!=-1){
				
				if(vector1[index] == vector2[index]){
					join += 1;
				}
				else{
					section+= 1 ;
				}
				
			}
			
		}
		
		if(section==0){ // case where all elements are the same 
			return 1.0; 
		}
		return join/section;
		
	}
	
	public static Double Cosine(double[] vector1 , double[] vector2){
		
		if(vector1.length != vector2.length){
			System.err.println("at computation of cosine similarity vectors are not the same length!");
			return 0.0 ;
		}
		int length = vector1.length;
		Double sum =  0.0;
		Double sum1 = 0.0;
		Double sum2 = 0.0;
		
		for(int index=0; index < length; index++){
			if(vector1[index]!= -1 && vector2[index]!=-1){
				sum1 = sum1 + vector1[index]*vector1[index];
				sum2 = sum2 + vector2[index]*vector2[index];
				sum  = sum  + vector1[index]*vector2[index];
			}
		
		}
		Double value = sum/(Math.sqrt(sum1)*Math.sqrt(sum2));

		return value;
		
	}
	
	
	
	public static Double Pearson(double[] vector1 , double[] vector2){
		
		if(vector1.length != vector2.length){
			System.err.println("at computation of cosine similarity vectors are not the same length!");
			return 0.0 ;
		}
		int length = vector1.length;
		
		
		Double vector1_mean = 0.0 ;
		Double vector2_mean = 0.0 ;
		int vector1_length = 0 ;
		int vector2_length = 0 ;
		
		for(int index=0; index < length; index++){
			
			// h apousia pliroforias einai pliroforia 
			// calculating mean value for each vector separately 
//			if(vector1[index]!=-1){ 
//				vector1_mean = vector1_mean + vector1[index];
//				vector1_length += 1;
//			}
//			
//			if(vector2[index]!=-1){
//				vector2_mean = vector2_mean + vector2[index];
//				vector2_length += 1;
//			}
			
			if(vector2[index]!=-1 && vector1[index]!=-1){
				
				vector1_mean = vector1_mean + vector1[index];
				vector1_length += 1;
				vector2_mean = vector2_mean + vector2[index];
				vector2_length += 1;
		}
			
		}
		
		vector1_mean = vector1_mean/vector1_length;
		vector2_mean = vector2_mean/vector2_length;
		
	
		for(int index=0; index < length; index++){
			
			if(vector1[index]!=-1){
				vector1[index] = vector1[index] - vector1_mean;
			}
			
			if(vector2[index]!=-1){
				vector2[index] = vector2[index] - vector2_mean;
			}
			
		}
		
		Double val = Math.abs(Cosine(vector1, vector2));
		System.err.println(val);
		return val;
	}
}
