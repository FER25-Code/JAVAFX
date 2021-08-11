package com.demo.javaFx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	int id;
	private String site;
	private String bloc;
	private String piece;
	private String code;
	private String name;
	private String marque;
	private String type;
	private String serie;

}
