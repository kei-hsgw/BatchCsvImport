package com.example.demo.domain.model;

import lombok.Data;

@Data
public class Employee {

	/** ID */
	private Integer id;
	
	/** 名前 */
	private String name;
	
	/** 年齢 */
	private Integer age;
	
	/** 性別(数値) */
	private Integer gender;
	
	/** 性別(文字列) */
	private String genderString;
	
	/** 性別の文字列を数値に変換 */
	public void convertGenderStringToInt() {
		
		// 文字列を数値に変換
		if ("男性".equals(genderString)) {
			gender = 1;
		} else if ("女性".equals(genderString)) {
			gender = 2;
		} else {
			String errorMsg = "Gender String is invalid: " + genderString;
			throw new IllegalStateException(errorMsg);
		}
	}
}
