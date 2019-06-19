package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonResult {

	private String status = null;

	private Object result = null;
	// Getter Setter
}