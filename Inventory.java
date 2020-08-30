/*
 *Ruchik Chaudhari
 *04/23/2020
 *CSC 143
 *This class contains two classes named Inventory and FoodItem. Class Inventory reads a file 
 *into an array of objects which are made through class FoodItem. Class Inventory gets input from the 
 *user and filters the inventory file and prints if any of the user's field match with the given file.
 *
 */
import java.io.*;
import java.util.*;


public class Inventory {

	public static void main(String[] args)throws FileNotFoundException {

		FoodItem [] itemList = readFile("Inventory.txt");
		printManufacturers(itemList);
		checkInventory(itemList);
	}

	/**
	 * Takes a file name as a parameter and reads the file
	 * into an array of data type FoodItem, prints all the item
	 * in the itemList and returns that array
	 * @param fileName
	 * @return Array of data type FoodItem
	 * @throws FileNotFoundException
	 */
	public static FoodItem[] readFile(String fileName) throws FileNotFoundException{
		//Open the file
		Scanner input = new Scanner(new File (fileName));
		input.nextLine(); //Discard the first row 

		FoodItem[] itemList = new FoodItem[25];
		int count = 0;

		//Start reading the file
		while (input.hasNextLine()) {
			String row = input.nextLine();
			Scanner line = new Scanner(row);

			//Start reading the line
			while(line.hasNext()) {
				//Assign the values from the line
				String manufacturer = line.next();
				String product = line.next();
				int weight = line.nextInt();
				int quantity = line.nextInt();

				//Make a new object from each line
				FoodItem item = new FoodItem(manufacturer, product, weight, quantity);
				//Fill in the array with the objects
				itemList[count] = item;
				count++;
			}
			line.close();
		}
		input.close();
		//Print all the items in itemList before returning it
				for (int i = 0; i < itemList.length;i++) {
					if(itemList[i] == null) {
						break;
					}
					System.out.println(itemList[i]);
				}
				System.out.println();
		
		return itemList;
	}
	/**
	 * Prints the manufacturer's names
	 * @param itemList
	 */
	public static void printManufacturers(FoodItem[] itemList ) {
		
		FoodItem sampleObject = itemList[0];
		System.out.print("Manufacturers are: ");
		System.out.print(sampleObject.getManufacturer());
		//Prints the other names
		for (int i = 1; i < itemList.length; i++) {
			sampleObject =itemList[i];
			if(sampleObject == null) {
				break;
			}
			System.out.print(", "+sampleObject.getManufacturer());
		}
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Gets the selection from the user and then
	 * calls printInventory to print list of the items which match
	 * with the user's input.
	 * @param itemList
	 */
	public static void checkInventory(FoodItem [] itemList) {
		// Get the user's selection
		Scanner console = new Scanner(System.in);
		System.out.print("Which field to filter on: ");
		String userInput = console.next();

		//Keep asking for the user's input until the user inputs done.
		while(!userInput.equalsIgnoreCase("done")) {
			//Create empty fields for the upcoming Selection object 
			String selectedManufacturer = "";
			String selectedProduct = "";
			int selectedWeight = -1;
			int selectedQuantity = -1;
			//Check if the user inputs correct fields and assign it to the appropriate field member.
			if(userInput.equalsIgnoreCase("Manufacturer")||userInput.equalsIgnoreCase("m")) {
				System.out.print("Please enter the Manufacturer's name: ");
				selectedManufacturer = console.next();
			}
			else if(userInput.equalsIgnoreCase("product")||userInput.equalsIgnoreCase("p")) {
				System.out.print("Please enter the product type: ");
				selectedProduct = console.next();
			}
			else if(userInput.equalsIgnoreCase("weight")||userInput.equalsIgnoreCase("w")) {
				System.out.print("Please enter the weight (in grams) of the item: ");

				if((console.hasNextInt())) {
					selectedWeight = console.nextInt();
				}
				else {
					System.out.println("Please type an integer for this field");
					console.next();
				}
			}
			else if(userInput.equalsIgnoreCase("quantity")||userInput.equalsIgnoreCase("q")) {
				System.out.print("Please enter the quantity of the item: ");
				if(console.hasNextInt()) {
					selectedQuantity = console.nextInt();
				}
				else {
					System.out.println("Please enter an integer for this field.");
					console.next();
				}
			}
			else {
				System.out.println("Invalid input. Please try again");
			}
			//Create an object with the user's input
			FoodItem Selection = new FoodItem(selectedManufacturer, selectedProduct, selectedWeight, selectedQuantity);
			
			//Print the list of the objects if any Selection field matches with the list of the objects in itemList array.
			printInventory(Selection, itemList);

			System.out.print("Which field to filter on: ");
			userInput = console.next();
		}
		console.close();
	}
	/**
	 * Prints the objects from itemList which match with any of the fields created by the user.
	 * @param Selection
	 * @param itemList
	 */
	public static void printInventory(FoodItem Selection, FoodItem [] itemList) {
		for (int i = 0; i < itemList.length;i++) {
			FoodItem item = itemList[i];
			if(item == null) {
				break;
			}
			if (item.isMatching(Selection)){
				System.out.println(itemList[i]);
			}
		}
	}
}

class FoodItem{
	private String manufacturer;
	private String  product;
	private int weight;
	private int quantity;

	//Constructors
	/**
	 * Constructs class FoodItem with parameters
	 * @param String manufacturer
	 * @param String product
	 * @param int weight
	 * @param int quantity
	 */
	public FoodItem(String manufacturer, String product, int weight, int quantity){
		setManufacturer(manufacturer);
		setProduct(product);
		setWeight(weight);
		setQuantity(quantity);
	}
	/**
	 * Constructs class FoodItem without parameters
	 */
	public FoodItem() {
		this(" "," ",0,0);
	}

	//getters
	/**
	 * Return's the name of the manufacturer
	 * @return String manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * Return's the type of product
	 * @return String product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * Return's the weight of the item
	 * @return int weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * Returns the quantity of the items
	 * @return int quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	//setters
	/**
	 * Set's the name of the manufacturer
	 * @param String manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * Sets's the product type
	 * @param String product
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * Sets the weight of the item
	 * @param int weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * Set's the quantity of the item
	 * @param int quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	//toString
	/**
	 * Prints the FoodItem
	 */
	public String toString() {
		return ("[Manufacturer = "+this.manufacturer+", Product = "+this.product+", Weight = "+this.weight+", Quantity = "+this.quantity+"]");
	}
	/**
	 * Compares the fields of two FoodItem objects
	 * and returns true if any of the field matches or
	 * false if none of the fields match.
	 * @param FoodItem Choice
	 * @return true or false
	 */
	public boolean isMatching(FoodItem Selection) {
		if (Selection.getManufacturer().equalsIgnoreCase(this.getManufacturer()) ||Selection.getProduct().equalsIgnoreCase(this.getProduct()) ) {
			return true;
		}
		if (Selection.getWeight() == this.getWeight() || Selection.getQuantity() == this.getQuantity()) {
			return true;
		}
		return false;
	}
}
