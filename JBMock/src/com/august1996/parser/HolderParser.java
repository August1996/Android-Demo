package com.august1996.parser;

import com.august1996.core.TypeParser;

public class HolderParser implements TypeParser {

	@Override
	public Object parser(String type) {
		return ValueHolder.get(type);
	}

}
