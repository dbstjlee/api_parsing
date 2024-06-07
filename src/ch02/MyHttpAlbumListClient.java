package ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * JSON Array 형태를 파싱해보자.
 */
public class MyHttpAlbumListClient {

	public static void main(String[] args) {

		// 순수 자바코드에서 HTTP 통신
		// 1. 서버 주소 경로
		// 2. URL 클래스 필요
		// 3. url.openConnection() <-- 스트림을 활용하는 IO 작업 진행

		// 자원을 요청해서 응답 받는 작업임.
		// http 메세지가 문자 형태로 날라옴.
		// 자원의 요청 = GET
		// 자원을 서버에 저장 = POST

		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/albums");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // httpurlconnection이 필요함.
			conn.setRequestMethod("GET");
			// RequestProperty : Request Header 값 세팅(String key, String value)
			// 타입 설정(application/json) 형식으로 전송 (Request body 전달 시 application/json로 서버에 전달 
			conn.setRequestProperty("Content-type", "application/json");

			// TCPip 기반의 통신 = 신뢰성 있는 통신(요청 -> 반드시 응답)
			// UDP 응답 안 올 수 있음(빠름)

			// 응답 코드 확인
			int responseCode = conn.getResponseCode();
			System.out.println("response code : " + responseCode);

			// 400, 405 : 서버 경로 오류
			// 500 : 서버 자체의 연산이 잘못됨.

			// HTTP 응답 메세지의 데이터를 추출[] -- Stream -- []
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// getInputStream : 인코딩 작업을 해야해서 보조 스트림 연결

			String inputLine;
			StringBuffer buffer = new StringBuffer();
			// 한 번 생성되면 불변 -> 한줄 읽을 때마다 늘어남. -> StringBuffer 사용
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine); // buffer: 객체 생성 X -> 오류 발생 -> 객체 생성하기
			}
			in.close();

			System.out.println(buffer.toString());
			System.out.println("----------------------------");
			// gson lib 활용
			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().setPrettyPrinting().create(); // 예쁘게 출력
			// tojson : 객체를 json 문자로 변환
			// fromjson : 문자를 객체로 변환

			// 하나의 JSON Object 형태 파싱
			// Album albumDTO = gson.fromJson(buffer.toString(), Album.class);

			// [{...},{...},{...}]
			// Gson에서 제공하는 Type 이라는 데이터 타입을 활용할 수 있다.

			// JSON 배열 형태를 쉽게 파싱하는 방법 -> TypeToken 안에 List<T> 을 활용한다.
			Type albumType = new TypeToken<List<Album>>() {}.getType(); 
			// TypeToken 클래스를 이용해서 Lis<Album>형식의 객체를 내부적으로 만들고
			// 만들어진 List<Album> 데이터 타입을 getType() 메서드로 얻어온다. 
			// .getType() : TypeToken을 반환한다.
			// TypeToken 클래스는 추상클래스로 객체를 생성할 수 없어 {} 구현함.(익명 클래스)
			// => JSON 형태의 배열 데이터를 Java 객체로 받을 때 사용하는 코드 
			
			List<Album> albumList = gson.fromJson(buffer.toString(), albumType);
			// buffer.toString() : '배열로 감싸져 있는 문자'(파싱 대상)
			// => buffer.toString()를 albumType 객체로 변환(문자 -> 객체)

			System.out.println(albumList.size());
			for (Album a : albumList) {
				System.out.println(a.toString());
			}
			// album이라는 Object를 100개 받음. 하나 하나 반복해서 출력함.

		} catch (IOException e) {
			e.printStackTrace();
		}


	}// end of main

}// end of class
