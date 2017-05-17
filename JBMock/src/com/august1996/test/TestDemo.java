package com.august1996.test;

import java.util.ArrayList;

import com.august1996.callback.OnMockedCallback;
import com.august1996.core.JBMock;
import com.august1996.parser.ValueHolder;
import com.august1996.thread.DefaultDispatcher;

public class TestDemo {
	public static void main(String[] args) {
		ValueHolder.register("username", "小明", "小红");
		ValueHolder.register("username", "小花");

		ValueHolder.register("icon", "http://www.baidu.com/1.jpg", "http://www.baidu.com/2.jpg");
		ValueHolder.register("icon", "http://www.baidu.com/3.jpg");

		ValueHolder.register("gender", 1);
		ValueHolder.register("gender", 0);

		ArrayList<UserEntity> list = JBMock.getInstance().syncGet(UserEntity.class, 3000, 10);
		for (UserEntity entity : list) {
			System.out.println(entity.toString());
		}

		JBMock.getInstance().asyncGet(UserEntity.class, 3000, 5, DefaultDispatcher.class,
				new OnMockedCallback<UserEntity>() {

					@Override
					public void onMocked(ArrayList<UserEntity> listEntity) {
						System.out.println(Thread.currentThread().getName());
						for (UserEntity entity : listEntity) {
							System.out.println(entity.toString());
						}
					}
				});

	}
}
