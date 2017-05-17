package com.august1996.test;

import com.august1996.anno.Type;
import com.august1996.parser.DefaultType;

public class UserEntity {
	@Type("username")
	public String username;
	@Type("icon")
	public String icon;
	@Type("gender")
	public int gender;
	@Type(DefaultType.NOW_TIMESTAMP)
	public long regTime;

	@Override
	public String toString() {
		return "UserEntity [username=" + username + ", icon=" + icon + ", gender=" + gender + ", regTime=" + regTime
				+ "]";
	}

}
