package ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MyHttpUserClient {

	public static void main(String[] args) {

		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/users");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.addRequestProperty("Content-type", "application/json");

			// 응답 코드 확인
			int responseCode = conn.getResponseCode();
			System.out.println("response Code : " + responseCode);

			// 데이터 추출
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			StringBuffer buffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			in.close();

			System.out.println(buffer.toString());

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			User userDTO = gson.fromJson(buffer.toString(), User.class);
//			System.out.println(userDTO.getId());
//			System.out.println(userDTO.getName());

			Type userType = new TypeToken<List<User>>() {
			}.getType();

			List<User> userList = gson.fromJson(buffer.toString(), userType);

			System.out.println(userList.size());
			for (User user : userList) {
				System.out.println(user.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // end of main

}
