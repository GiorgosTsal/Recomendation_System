package project_recomendation_system;

public class Cosine_similarity {

	public static Double compute(double[] vector1 , double[] vector2){
		
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
	
	
}
