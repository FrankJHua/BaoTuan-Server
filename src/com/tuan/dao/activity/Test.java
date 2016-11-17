package com.tuan.dao.activity;

import com.tuan.service.activity.ActivitySearchService;

public class Test {

	public static void main(String[] args) {
		
		ActivitySearchService service = new ActivitySearchService();
		String result = service.searchActivityByKeyword("海", 3);
		System.out.println(result);

	}

}
