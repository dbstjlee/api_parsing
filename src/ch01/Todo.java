package ch01;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
	 {
	  "userId": 1,
	  "id": 1,
	  "title": "delectus aut autem",
	  "completed": false
	}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Todo {

	private int userId;
	private int id;
	private String title;
	private boolean completed;
}
