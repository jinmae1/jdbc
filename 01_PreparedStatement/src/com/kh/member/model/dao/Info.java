package com.kh.member.model.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Info {

	public HashMap<String, String> readInfo() {
		HashMap<String, String> info = new HashMap<>();
		try {
			File src = new File("info.txt");
			Scanner sc = new Scanner(src);
			while (sc.hasNextLine()) {
				String keySet = sc.nextLine();
				info.put(keySet.split("=")[0], keySet.split("=")[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

}