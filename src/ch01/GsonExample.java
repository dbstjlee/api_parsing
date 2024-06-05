package ch01;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// 파싱
public class GsonExample {

	public static void main(String[] args) {

		Student student1 = new Student("고길동", 40, "애완학과");
		Student student2 = new Student("둘리", 5, "문제학과");

		Student[] studentArr = { student1, student2 };

		// 특정 형식 (구조화) 있는 문자열로 변환하고 싶다. --> JSON 형태

		// Gson gson = new Gson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// 객체를 --> JSON 형식의 문자열로 변환시켜! --> 메서드 toJson() 호출하기
		String student1Str = gson.toJson(student1);
		System.out.println(student1Str);

		// setPrettyPrinting -> 예쁘게 출력하라 옵션...
		Gson gson2 = new Gson();
		String student2Str = gson.toJson(student2);
		System.out.println(student2Str);

		// 객체에서 --> 문자열 형태로 가능 그럼 반대로는?
		// 문자열에서 --> 클래스 형태로 어떻게 변경할까?

		// Object 형태로 변환
		Student studentObject = gson.fromJson(student1Str, Student.class);
		// student1Str 이 문자열을 어떻게 파싱할지
		// Student.class -> 클래스 명을 명시(최상의 클래스 Object에 있음)
		System.err.println(studentObject.getName());

		// 배열 형태로 변환
		Student[] StringArr = gson.fromJson(gson.toJson(studentArr), Student[].class);
		System.err.println(StringArr);

		for (Student student : StringArr) {
			System.err.println(student.toString());
		}

		System.out.println("-----------------------------");
		System.out.println(gson.toJson(StringArr));

	}

}
