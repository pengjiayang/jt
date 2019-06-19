package com.jt.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain=true)
@AllArgsConstructor
public class ImageVO{
	private Integer error;
	private String url;
	private Integer width;
	private Integer heigth;
}