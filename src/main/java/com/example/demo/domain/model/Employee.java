package com.example.demo.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Employee {

	/** ID */
	@NotNull
	@Id
	private Integer id;
	
	/** 名前 */
	@NotNull
	private String name;
	
	/** 年齢 */
	@Min(20)
	private Integer age;
	
	/** 性別(数値) */
	private Integer gender;
	
	/** 性別(文字列) */
	@Transient
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
