package com.example.a3dstep.Model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Data {

	public static int LOGIN_WITH_FACEBOOK = 2;
	public static int LOGIN_NORMAL = 1;

	static final String[] county = {
			"Los Angeles", "Cook", "Harris", "Maricopa", "San Diego", "Orange", "Miami-Dade",
			"Kings", "Dallas", "Queens", "Riverside", "San Bernardino", "King", "Clark",
			"Tarrant", "Santa Clara", "Broward", "Bexar", "Wayne", "New York",
			"Philadelphia", "Alameda", "Middlesex", "Suffolk", "Sacramento", "Bronx", "Nassau", "Palm Beach",
			"Cuyahoga", "Hillsborough", "Allegheny", "Oakland", "Franklin", "Orange", "Hennepin", "Fairfax",
			"Contra Costa", "Travis", "Salt Lake", "St. Louis", "Montgomery", "Pima", "Honolulu", "Westchester",
			"Milwaukee", "Fulton", "Mecklenburg", "Fresno", "Shelby", "Wake", "Fairfield",
			"DuPage", "Erie", "Pinellas", "Marion", "Bergen", "Hartford", "Prince George's",
			"Duval", "New Haven", "Kern", "Macomb", "Ventura", "Gwinnett", "El Paso",
			"San Francisco", "Collin", "Baltimore", "Pierce", "Montgomery", "Worcester", "Hamilton", "Hidalgo",
			"Essex", "Multnomah", "Jefferson", "Monroe", "Oklahoma", "Suffolk", "San Mateo", "Snohomish", "Lake",
			"DeKalb", "Cobb", "San Joaquin", "Denton", "Will", "Jackson", "Norfolk", "Bernalillo",
			"Jefferson", "Hudson", "El Paso", "Davidson", "Lee", "Monmouth", "Bucks", "Providence"

	};


	//method to generate an ArrayList of random contents from string array county
	public static ArrayList randomList(int count) {
		Random random = new Random();
		HashSet item = new HashSet();

		count = Math.min(count, county.length);

		while (item.size() < count) {
			item.add(county[random.nextInt(county.length)]);
		}
		return new ArrayList(item);
	}
}
