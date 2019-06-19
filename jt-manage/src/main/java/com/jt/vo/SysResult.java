package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	private Integer status;//200表示成功,201表示失败
	private String msg;//状态信息
	private Object data;//后天返回的数据
	public static SysResult oK() {
		return new SysResult(200,null,null);
	}
	public static SysResult oK(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	public static SysResult oK(Object data) {
		return new SysResult(200,null,data);
	}
	public static SysResult fail() {
		return new SysResult(201,null,null);
	}
}
