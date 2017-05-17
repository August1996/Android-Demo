package com.august1996.parser;

import com.august1996.core.TypeParser;

public class DefaultParser implements TypeParser {

	@Override
	public Object parser(String type) {
		if (DefaultType.NOW_TIMESTAMP.equals(type)) {
			return System.currentTimeMillis();
		}
		return null;
	}
}
