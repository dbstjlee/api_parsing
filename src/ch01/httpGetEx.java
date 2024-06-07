package ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class httpGetEx {

	public static void main(String[] args) {

		// 1. 남이 만들어준 서버에
		// 2. 자바코드로 요청
		// 3. 문자열 던져줌(json문자열)
		// 4. 자바 코드상에서 json 문자열 --> 클래스
		// 5. Todo 클래스 설계
		// 6. 응답 받은 문자열 --> 클래스 변환 작업
		// 7. 문자열 --> 클래스 fromJson("{}", todo.class)
		// toJson(TodoObject) --> {"name": "홍"}

		// 서버 -> Html코드로 받고
		// 자바로 받고
		// 문자열 -> 파싱

		// end point = todos

		// 클래스화 --> todo 클래스 여러개 -> 배열
		// 근데 /1은 하나의 클래스임.(배열 필요X)

		// URL 클래스 만들기
		try {
			// http 경로
			String todo = "https://jsonplaceholder.typicode.com/todos/1";
			// 객체 생성
			URL url = new URL(todo);
			// 연결 요청
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// Method(GET)
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json"); // 오타 주의

			// HTTP 응답 메시지
			int responseCode = conn.getResponseCode();
			System.out.println("HTTP CODE : " + responseCode);

			// 스트림 열기(문자 기반 입력 스트림)
			BufferedReader brIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			StringBuffer responseBuffer = new StringBuffer(); // 보조 스트림
			while ((inputLine = brIn.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			brIn.close();
			System.out.println(responseBuffer.toString());
			System.out.println("----------------------------");
			
			// 옵션값
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			
			Todo todoDTO = gson.fromJson(responseBuffer.toString(), Todo.class);
			
			
//			// 객체 --> json 형식의 문자열로 변환
//			String Str1 = gson.toJson(todo);
//			System.out.println(responseBuffer);
//			// while문이 돌면서 받은 데이터가 보조 스트림인 responseBuffer 여기에 담기기 때문.

//			// 문자열 --> 객체(클래스)로 변환
//			String Str2 = gson.fromJson(Str1, todo.getClass());
//			System.out.println(Str2.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
