package ch02;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {
	  "userId": 1,
	  "id": 1,
	  "title": "quidem molestiae enim"
	}
 */
// DTO 클래스 - 데이터 트랜스퍼 오브젝트
// private --> Gson lib --> 변수에 접근해서 json 값을 넣어준다.
// setter 필요하다. 
// 사용자 정의 생성자  -> 객체 생성시 한번에 데이터 입력
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Album {
	
	// 대소문자가 같아야만 파싱 가능
	private int userId;
	private int id;
	private String title;
	
	
	

	
	
	
	
}
