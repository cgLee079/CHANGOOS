package com.cglee079.changoos.config.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BoardType {
	PROJECT("projects"), STUDY("studies"), BLOG("blogs");

	@Getter
	private final String val;

	public static BoardType from(String str) {
		for (BoardType b : BoardType.values()) {
			if (b.val.equalsIgnoreCase(str)) {
				return b;
			}
		}
		return null;
	}

}
