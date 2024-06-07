package ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyHttpAlbumClient {

		// 순수 자바코드에서 HTTP 통신
		// 1. 서버 주소 경로
		// 2. URL 클래스 필요
		// 3. url.openConnection() <-- 스트림을 활용하는 IO 작업 진행

		// 이는 자원을 요청해서 응답 받는 작업임.
		// http 메세지가 문자 형태로 날라옴.
		// 자원의 요청 = GET
		// 자원을 서버에 저장 = POST
		// TCP/IP 기반의 통신 = 신뢰성 있는 통신(요청 -> 반드시 응답)
		// UDP = 응답 안 올 수 있음(빠름)
	
	public static void main(String[] args) {

		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/albums/1");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // HttpURLConnection이 필요함.
			conn.setRequestMethod("GET");
			// RequestProperty : Request Header 값 세팅(String key, String value)
			// 타입 설정(application/json) 형식으로 전송 (Request body 전달 시 application/json로 서버에 전달
			conn.setRequestProperty("Content-type", "application/json");

			// 응답 코드 확인
			int responseCode = conn.getResponseCode();
			System.out.println("response code : " + responseCode);

			// 200: 성공
			// 400: url 잘못 입력됨.(문법상 오류)
			// 404: 클라이언트가 요청한 문서를 찾지 못한 경우
			// 405: 메서드 허용X(메서드를 수행하기 위한 해당 자원의 이용이 허용되지 않았을 경우
			// 415: 지원되지 않은 형식으로 클라이언트가 요청해서 서버가 요청에 대한 승인을 거부한 오류
			// 500: 웹 서버가 요청사항을 수행할 수 없을 경우(서버 자체의 연산이 잘못됨.)

			// String => 불변한 문자열 처리를 위한 클래스(String 객체 생성 시 값 변경X. 새로운 객체로 추가 및 변경됨)
			// StringBuffer => 가변한 문자열 처리를 위한 클래스(새로운 문자열 추가 및 변경 시 기존의 객체로 추가 및 변경됨)
			// StringBuilder => StringBuffer와 동일(but, 멀티 스레드가 아닌 싱글 스레드에서 빠른 성능을 가짐)
			// 문자열을 추가하거나 변경하는 작업이 많으면 StringBuffer를, 적으면 String을 사용
			// subString(시작 위치, 끝 위치) => StringBuffer 객체의 시작 위치에서 끝 위치까지의 문자를 뽑아냄.
			
			// HTTP 응답 메세지의 데이터를 추출[] -- Stream -- []
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// getInputStream : 인코딩 작업을 추가로 해야해서 보조 스트림 연결함.

			String inputLine;
			StringBuffer buffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine); // buffer: 객체 생성 X -> 오류 발생 -> 객체 생성하기
			}
			in.close();

			System.out.println(buffer.toString());
			System.out.println("----------------------------");
			
			// gson lib 활용
			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			// GsonBuilder() : 날짜 형식 지정, 널 값 처리, 필드명 변경 등 다양한 옵션을 설정하여 Gson 객체 생성 가능
			// setPrettyPrinting(): JSON 문자열을 가독성 좋게 출력하는 옵션
			// => GsonBuilder를 통해 생성된 Gson 객체가 JSON 문자열을 들여쓰기와 개행을 적용하여 가독성을 높여줌.

			// tojson : 객체를 json 문자로 변환
			// fromjson : 문자를 객체로 변환

			Album albumDTO = gson.fromJson(buffer.toString(), Album.class);
			// buffer.toString() : 파싱할 대상
			// 객체를 생성해서 주소값에 넣어줌.

			System.out.println(albumDTO.getId());
			System.out.println(albumDTO.getUserId());
			System.out.println(albumDTO.getTitle());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end of main

}// end of class
