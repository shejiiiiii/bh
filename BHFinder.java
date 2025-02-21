package kunganolang;

import java.util.*;

public class BHFinder {

	public static boardinghouses hey = new boardinghouses();
	public static Scanner sc = new Scanner(System.in);
	public static String tempBH[][] = new String[hey.BUCampuses.length][hey.arrnum];
	public static int arrIndex[][]= new int[hey.BHouses.length][hey.arrnum];
	public static int rangeOccupants = 0, rangePrice = 0, rangeDistance = 0;
	public static boolean usedFilter = false;
	
	public static void main(String[] args) {

		for(int i=0;i<hey.BUCampuses.length;i++)
			System.out.print("\t"+hey.BUCampuses[i]+"\t");
		System.out.print("\n\nPick a campus: ");
		String campus = sc.nextLine();
		campusFinder(campus);
		options(campus);	
		displayBH(tempBH, campus);
		wantMorePanel(campus);
	}
	
	public static String campusFinder(String campus) {

		if(Arrays.asList(hey.lowerCBUCampuses).contains(campus.toLowerCase()))
			System.out.println("\nLIST OF AVAILABLE BOARDING HOUSES");	
		for(int i = 0; i < hey.BUCampuses.length; i++) {
			if(campus.equalsIgnoreCase(hey.BUCampuses[i]) || campus.equalsIgnoreCase(hey.lowerBUCampuses[i])) {
				for(int j = 0; j < hey.BHouses.length-1; j++) {
					System.out.println((j+1) + ". " + hey.BHouses[i][j] + ": " 
										+ hey.BHDeets[i][j] 
										+ "\nTotal Occupants in a Room: " + hey.BHPeople[i][j]
										+ "\nPrice: " + hey.BHPrice[i][j]
										+ "\nDistance: "+ hey.BHDistance[i][j] + "\n");
				}
				return campus;
			}
		}
		System.out.println("Invalid Campus Input");
		main(null);	
		return campus;
	}
	
	public static void options(String campus) {
		System.out.println("-----------------\n"
				+ "[1] Search Boarding House\n"
				+ "[2] Open Filter Options\n"
				+ "[3] Change Campus\n"
				+ "[0] Exit\n");
		System.out.print("\nPick Option: ");
		String option = sc.nextLine();
		try {
			int Ioption = Integer.parseInt(option);
			if(Ioption == 1) {
				searchOptions(searchPanel(), campus);
			}
			else if(Ioption == 2) {
				filterPanel(campus);
			}
			else if(Ioption == 3) {
				resetBH();
				main(null);
			}
			else if(Ioption == 0) {
				System.out.println("THANK YOU FOR BROWSING");
				System.exit(0);
			}
			else {
				options(campus);
			}
		}
		catch(Exception e) {
			options(campus);
		}
		
	}
	
	public static String searchPanel() {
		System.out.println("Search for Boarding Houses: ");
		String search = sc.nextLine();
		return search;
	}
	
	public static String searchOptions(String search, String campus) {
		int i = campusChecker(campus);
		boolean searchSuccess = false;
		for(int j = 0; j < hey.BHouses[i].length; j++) {
			String word1  []= hey.lowerBHouses[i][j].split(" "); 
			if(hey.BHouses[i][j].equalsIgnoreCase(search)) {
				searchSuccess = true;
				System.out.println("\n\n"+hey.BHouses[i][j] + ": " + hey.BHDeets[i][j]
						+ "\nTotal Occupants in a Room: " + hey.BHPeople[i][j]
						+ "\nPrice: " + hey.BHPrice[i][j]
						+ "\nDistance: "+ hey.BHDistance[i][j] + "\n");
			}
			else
			{
				for(int k=0; k < word1.length; k++)
				{
					if(word1[k].equalsIgnoreCase(search)) {
						searchSuccess = true;
						System.out.println(hey.BHouses[i][j] + ": " + hey.BHDeets[i][j]
								+ "\nTotal Occupants in a Room: " + hey.BHPeople[i][j]
								+ "\nPrice: " + hey.BHPrice[i][j]
								+ "\nDistance: "+ hey.BHDistance[i][j] + "\n");
					}
				}
			}
			
			
		}
		
		if(!searchSuccess) {
			System.out.println("No Boarding Houses that starts with \"" + search + "\"");
			System.out.println("Do you want to search for more boarding houses[Y/N]? ");
			String choice= sc.nextLine();
			if(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("yes")) {
				searchOptions(searchPanel(), campus);
			}
			else if(choice.equalsIgnoreCase("n")||choice.equalsIgnoreCase("no")) {
				options(campus);
			}
			
		}

		wantMorePanel(campus);	
		return null;
	}
	public static void wantMorePanel(String campus) {
		System.out.print("Do you want to search for more[Y/N]? ");
		String choice= sc.nextLine();
		if(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("yes")) {
			System.out.println();
			resetBH();
			campusFinder(campus);
			options(campus);
		}
		else if(choice.equalsIgnoreCase("n")||choice.equalsIgnoreCase("no")) {
			System.out.println("\nTHANK YOU FOR BROWSING");
			System.exit(0);
		}
		else 
			wantMorePanel(campus);
	}
	
	public static void filterPanel(String campus) {
		System.out.println("-----------------\n"
				+ "Filter Options:\n"
				+ "[1] Occupants\n"
				+ "[2] Price\n"
				+ "[3] Distance\n"
				+ "[4] Clear All Filters\n"
				+ "[5] Back\n"
				+ "[0] Done");
		if(rangeOccupants!=0 || rangeDistance!=0 || rangePrice!=0)  
			System.out.println("\nCurrent Filters Applied: ");
		
		if(rangeOccupants!=0)  
			System.out.println("\tOccupants: "+hey.BHRanges[0][rangeOccupants-1]);
		if(rangePrice!=0)  
			System.out.println("\tPrice: "+hey.BHRanges[1][rangePrice-1]);
		if(rangeDistance!=0)  
			System.out.println("\tDistance: "+hey.BHRanges[2][rangeDistance-1]);
		
		System.out.print("\nPick a Filter Option: ");
		int filter = 0;
		try {
			filter = Integer.parseInt(sc.nextLine());
		}
		catch(Exception e){
			filterPanel(campus);
		}
		filterOptions(filter, campus);
	}

	public static int filterOptions(int filter, String campus) {
		switch(filter) {
		case 1:
			System.out.println("\n\nOccupants Range:\n"
							+ "[1] 1-2\n" 
							+ "[2] 3-4\n"
							+ "[3] 5-6\n"
							+ "[4] 7 or more\n"
							+ "[5] Sort by Occupant\n"
							+ "[0] Back");
			System.out.print("\nPick an Occupants Range: ");
				try {
					rangeOccupants = Integer.parseInt(sc.nextLine());
					if(rangeOccupants != 0 && rangeOccupants != 5) {
						usedFilter = true;
					}
					else if(rangeOccupants == 5) {
						occupantsBubbleSort(campus);
						wantMorePanel(campus);
						break;
					}
					filterPanel(campus);
				}
				catch(Exception e){
					filterOptions(filter, campus);
				}
			break;
		case 2:
			System.out.println("\n\nPrice Range:\n"
					+ "[1] PHP 500-1000\n" 
					+ "[2] PHP 1001-2000\n"
					+ "[3] PHP 2001-3000\n"
					+ "[4] PHP 3001-4000\n"
					+ "[5] PHP 4001-more\n"
					+ "[6] Sort by Price\n"
					+ "[0] Back");
			System.out.print("\nPick an Price Range: ");
				try {
					rangePrice = Integer.parseInt(sc.nextLine());
					if(rangePrice != 0 && rangePrice != 6) {
						usedFilter = true;
					}
					else if(rangePrice==6) {
						priceInsertionSort(campus);
						wantMorePanel(campus);
					}
					filterPanel(campus);
				}
				catch(Exception e){
					filterOptions(filter, campus);
				}
			break;
		case 3:
			System.out.println("\n\nDistance Range:\n"
					+ "[1] 0-9 METERS\n" 
					+ "[2] 10-19 METERS\n"
					+ "[3] 20-29 METERS\n"
					+ "[4] 30-39 METERS\n"
					+ "[5] 40 or more METERS\n"
					+ "[6] Sort by Distance\n"
					+ "[0] Back");
			System.out.print("\nPick an Distance Range: ");
				try {
					rangeDistance = Integer.parseInt(sc.nextLine());
					if(rangeDistance != 0 && rangeDistance != 6) {
						usedFilter = true;
					}
					else if(rangeDistance==6) {
						distanceSelectionSort(campus);
						wantMorePanel(campus);
					}
					filterPanel(campus);
				}
				catch(Exception e){
					filterOptions(filter, campus);
				}
			break;
		case 4:
			resetBH();
			filterPanel(campus);
			break;
		case 5:
			resetBH();
			options(campus);
			break;
		case 0:
			if(usedFilter) {
				filter1(rangeOccupants, rangePrice, rangeDistance, campus);
			}
			else {
				filter0(rangeOccupants, rangePrice, rangeDistance, campus);
			}
			break;
		default:
			filterPanel(campus);
		}
		
		return filter;
	}
	
	public static String[][] filter0(int rangeOccupants, int rangePrice, int rangeDistance, String campus) {
		int i = campusChecker(campus);
		
		for(int j = 0; j < hey.BHPeople[i].length; j++) {
			tempBH[j][0] = hey.BHPeople[i][j];
			tempBH[j][1] = hey.BHPrice[i][j];
			tempBH[j][2] = hey.BHDistance[i][j];
		}
		
		displayBH0(tempBH, campus);
		wantMorePanel(campus);
		
		return tempBH;
	}
	
	public static String[][] filter1(int rangeOccupants, int rangePrice, int rangeDistance, String campus) {
		int i = campusChecker(campus);
		for(int k = 1; k < 6; k++) {
			//rangeOccupants
			for(int j = 0; j < hey.BHPeople[i].length; j++) {
				if((Integer.parseInt(hey.BHPeople[i][j]) >= 1 && Integer.parseInt(hey.BHPeople[i][j]) <= 2) && ((k == rangeOccupants)&&(1 == rangeOccupants))) {
					tempBH[j][0] = hey.BHPeople[i][j];
				}
				else if((Integer.parseInt(hey.BHPeople[i][j]) >= 3 && Integer.parseInt(hey.BHPeople[i][j]) <= 4) && ((k == rangeOccupants)&&(2 == rangeOccupants))) {
					tempBH[j][0] = hey.BHPeople[i][j];
				}
				else if((Integer.parseInt(hey.BHPeople[i][j]) >= 5&& Integer.parseInt(hey.BHPeople[i][j]) <= 6) && ((k == rangeOccupants)&&(3 == rangeOccupants))) {
					tempBH[j][0] = hey.BHPeople[i][j];
				}
				else if((Integer.parseInt(hey.BHPeople[i][j]) >= 7) && ((k == rangeOccupants)&&(4 == rangeOccupants))) {
					tempBH[j][0] = hey.BHPeople[i][j];
				}
				else if(rangeOccupants==0) {
					tempBH[j][0] = hey.BHPeople[i][j];
				}
				//System.out.println("H");
			}
			//rangePrice
			for(int j = 0; j < hey.BHPrice[i].length; j++) {
				if((Integer.parseInt(hey.BHPrice[i][j]) >= 500 && Integer.parseInt(hey.BHPrice[i][j]) <= 1000) && ((k == rangePrice)&&(1 == rangePrice))) {
					tempBH[j][1] = hey.BHPrice[i][j];
				}
				else if((Integer.parseInt(hey.BHPrice[i][j]) >= 1001 && Integer.parseInt(hey.BHPrice[i][j]) <= 2000) && ((k == rangePrice)&&(2 == rangePrice))) {
					tempBH[j][1] = hey.BHPrice[i][j];
				}
				else if((Integer.parseInt(hey.BHPrice[i][j]) >= 2001&& Integer.parseInt(hey.BHPrice[i][j]) <= 3000) && ((k == rangePrice)&&(3 == rangePrice))) {
					tempBH[j][1] = hey.BHPrice[i][j];
				}
				else if((Integer.parseInt(hey.BHPrice[i][j]) >= 3001&& Integer.parseInt(hey.BHPrice[i][j]) <= 4000) && ((k == rangePrice)&&(4 == rangePrice))) {
					tempBH[j][1] = hey.BHPrice[i][j];
				}
				else if((Integer.parseInt(hey.BHPrice[i][j]) >= 4001) && ((k == rangePrice)&&(5 == rangePrice))) {
					tempBH[j][1] = hey.BHPrice[i][j];
				}
				else if(rangePrice==0) {
					tempBH[j][1] = hey.BHPrice[i][j];
				}
			}
			//rangeDistance
			for(int j = 0; j < hey.BHDistance[i].length; j++) {
				if((Integer.parseInt(hey.BHDistance[i][j]) >= 0 && Integer.parseInt(hey.BHDistance[i][j]) <= 9) && ((k == rangeDistance)&&(1 == rangeDistance))) {
					tempBH[j][2] = hey.BHDistance[i][j];
				}
				else if((Integer.parseInt(hey.BHDistance[i][j]) >= 10 && Integer.parseInt(hey.BHDistance[i][j]) <= 19) && ((k == rangeDistance)&&(2 == rangeDistance))) {
					tempBH[j][2] = hey.BHDistance[i][j];
				}
				else if((Integer.parseInt(hey.BHDistance[i][j]) >= 20&& Integer.parseInt(hey.BHDistance[i][j]) <= 29) && ((k == rangeDistance)&&(3 == rangeDistance))) {
					tempBH[j][2] = hey.BHDistance[i][j];
				}
				else if((Integer.parseInt(hey.BHDistance[i][j]) >= 30&& Integer.parseInt(hey.BHDistance[i][j]) <= 39) && ((k == rangeDistance)&&(4 == rangeDistance))) {
					tempBH[j][2] = hey.BHDistance[i][j];
				}
				else if((Integer.parseInt(hey.BHDistance[i][j]) >= 40) && ((k == rangeDistance)&&(5 == rangeDistance))) {
					tempBH[j][2] = hey.BHDistance[i][j];
				}
				else if(rangeDistance==0) {
					tempBH[j][2] = hey.BHDistance[i][j];
				}
			}
		}
		return tempBH;
	}
	
	public static void displayBH(String[][] tempBH, String campus) {
		int i = campusChecker(campus);
		int k=1;
		for(int j = 0; j < tempBH[0].length; j++) {
			boolean occupantsT= (tempBH[j][0] != null);
			boolean priceT= (tempBH[j][1] != null);
			boolean distanceT= (tempBH[j][2] != null);
			
			if(occupantsT&&rangeOccupants!=0 && priceT && distanceT) {
				System.out.println((k) + ". " + hey.BHouses[i][j] + ": " 
						+ "\nTotal Occupants in a Room: " + tempBH[j][0]
						+ "\nPrice: " + tempBH[j][1] 
						+ "\nDistance: "+ tempBH[j][2] + "\n");
				k++;
				continue;
			}
			if(priceT&&rangePrice!=0 && occupantsT && distanceT) {
				System.out.println((k) + ". " + hey.BHouses[i][j] + ": " 
						+ "\nTotal Occupants in a Room: " + tempBH[j][0]
						+ "\nPrice: " + tempBH[j][1] 
						+ "\nDistance: "+ tempBH[j][2] + "\n");
				k++;
				continue;
			}
			if(distanceT&&rangeDistance!=0 && priceT && occupantsT) {
				System.out.println((k) + ". " + hey.BHouses[i][j] + ": " 
						+ "\nTotal Occupants in a Room: " + tempBH[j][0]
						+ "\nPrice: " + tempBH[j][1] 
						+ "\nDistance: "+ tempBH[j][2] + "\n");
				k++;
				continue;
			}
		}
		if(k==1)
			System.out.println("NO BOARDING HOUSE AVAILABLE");
		
	}
	public static void displayBH0(String[][] tempBH, String campus) {
		int i = campusChecker(campus);
		for(int j = 0; j < tempBH[0].length; j++) {
			if(tempBH[j][0] != null) {
				System.out.println((j+1) + ". " + hey.BHouses[i][j] + ": " 
						+ "\nTotal Occupants in a Room: " + tempBH[j][0]
						+ "\nPrice: " + tempBH[j][1] 
						+ "\nDistance: "+ tempBH[j][2] + "\n");
			}
		}		
	}
	public static void resetBH() {
		String tempArr[][]=new String[hey.arrnum][hey.BHPeople[0].length];
		tempBH = tempArr;
		rangeOccupants = 0;
		rangePrice = 0;
		rangeDistance = 0;
		usedFilter= false;
	}
	
	public static int campusChecker(String campus) {
		int i = 0;
		for(boolean a = true; i < hey.BUCampuses.length && a;i++) {
			if(campus.equalsIgnoreCase(hey.BUCampuses[i]) || campus.equalsIgnoreCase(hey.lowerBUCampuses[i])) {
				i--;
				a = false;
			}
		}
		return i;
	}
	
	public static void occupantsBubbleSort(String campus) {
		int x = campusChecker(campus);
		String tempBHPeople[] = new String[hey.BHPeople[x].length];
		for(int i = 0; i < hey.BHPeople[x].length; i++) {
			tempBHPeople[i] = hey.BHPeople[x][i];
		}
		
		for(int i = 0; i < hey.arrnum; i++) {
			arrIndex[x][i] = i;
		}
		
		boolean sorted = false;
		while(!sorted) {
			boolean swapped = false;
			for(int i = 1; i < tempBHPeople.length; i++) {
				if(Integer.parseInt(tempBHPeople[i]) < Integer.parseInt(tempBHPeople[i-1])) {
					String temp = tempBHPeople[i-1];
					int tempnum = arrIndex[x][i-1];
					tempBHPeople[i-1] = tempBHPeople[i];
					arrIndex[x][i-1] = arrIndex[x][i];
					tempBHPeople[i] = temp;
					arrIndex[x][i] = tempnum;
					swapped = true;
				}
			}
			if(!swapped) {
				sorted = true;
			}
		}
		
		for(int k = 0; k < hey.BHouses[x].length; k++) {
			System.out.println((k+1) + ". " + hey.BHouses[x][arrIndex[x][k]] + ": " 
					+ "\nTotal Occupants in a Room: " + tempBHPeople[k]
					+ "\nPrice: " + hey.BHPrice[x][arrIndex[x][k]] 
					+ "\nDistance: "+ hey.BHDistance[x][arrIndex[x][k]] + "\n");
		}

	}
	
	public static void priceInsertionSort(String campus) {
		int x = campusChecker(campus);
		String tempBHPrice[] = new String[hey.BHPrice[x].length];
		for(int i = 0; i < hey.BHPrice[x].length; i++) {
			tempBHPrice[i] = hey.BHPrice[x][i];
		}
		
		for(int i=0; i < hey.arrnum; i++)
			arrIndex[x][i] = i;
		
		for(int i=1; i < hey.BHPrice.length-1; i++)
		{
			String temp = tempBHPrice[i];
			int tempNum = arrIndex[x][i];
			int j = i-1;
			while(j >= 0 && Integer.parseInt(tempBHPrice[j]) > Integer.parseInt(temp))
			{
				arrIndex[x][j+1] = arrIndex[x][j];
				tempBHPrice[j+1] = tempBHPrice[j];
				j--;		
			}
			tempBHPrice[j+1] = temp;
			arrIndex[x][j+1] = tempNum;
		}	
		
		for(int j = 0; j < hey.BHouses[x].length; j++) {
				System.out.println((j+1) + ". " + hey.BHouses[x][arrIndex[x][j]] + ": " 
						+ "\nTotal Occupants in a Room: " + hey.BHPeople[x][arrIndex[x][j]]
						+ "\nPrice: " + tempBHPrice[j]
						+ "\nDistance: "+ hey.BHDistance[x][arrIndex[x][j]]+ "\n");
		}	
		
	}
	
	public static void distanceSelectionSort(String campus) {
		int x = campusChecker(campus);
		String tempBHDistance[] = new String[hey.BHDistance[x].length];
		for(int i = 0; i < hey.BHDistance[x].length; i++) {
			tempBHDistance[i] = hey.BHDistance[x][i];
		}
		
		for(int i = 0; i < hey.arrnum; i++)
			arrIndex[x][i] = i;
		
		
		for(int i = 0; i < tempBHDistance.length-1; i++) {
			for(int j = i+1; j <= tempBHDistance.length-1; j++) {
				if(Integer.parseInt(tempBHDistance[i]) < Integer.parseInt(tempBHDistance[j])) {
					continue;
				} else {
					String temp = tempBHDistance[i];
					int tempNum = arrIndex[x][i];
					tempBHDistance[i] = tempBHDistance[j];
					arrIndex[x][i] = arrIndex[x][j];
					tempBHDistance[j] = temp;
					arrIndex[x][j] = tempNum;
				}
			}
		}
			
		
		for(int j = 0; j < hey.BHouses[x].length; j++) {
				System.out.println((j+1) + ". " + hey.BHouses[x][arrIndex[x][j]] + ": " 
						+ "\nTotal Occupants in a Room: " + hey.BHPeople[x][arrIndex[x][j]]
						+ "\nPrice: " + hey.BHPrice[x][arrIndex[x][j]]
						+ "\nDistance: "+ tempBHDistance[j]+ "\n");
		}	
		
	}
}
