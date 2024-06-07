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
			URL url = new URL("https://jsonplaceholder.typicode.com/albums/1");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // httpurlconnection이 필요함.
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json"); // 오타 주의

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
