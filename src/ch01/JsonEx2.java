package ch01;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonEx2 {

	public static void main(String[] args) {
		
		Dog dog1 = new Dog("포메리안", 1);
		Dog dog2 = new Dog("웰시코기", 2);
		
		Dog[] dogArr = {dog1, dog2};
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String dog1Str =  gson.toJson(dog1);
		System.out.println(dog1Str);
		
		String dog2Str = gson.toJson(dog2);
		System.out.println(dog2Str);
		
		Dog dogObject = gson.fromJson(dog1Str, Dog.class);
		System.out.println(dogObject.getName());
				

	}
	

}
