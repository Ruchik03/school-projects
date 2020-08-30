import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Programming Project 2
 * Part A - ArrayLists
 * Part B - Sets/Maps

 * This project reads a list of total members from a given file in two ways;
 * the first is using the ArrayList and second one is using Sets and Maps
 * and then adds and removes the names of the members as required.
 * 
 * @author Ruchik Chaudhari 
 * 05/19/2020
 */

/**
 * The Membership class adds and removes members from the list in two different 
 * ways. Part A performs the task using ArrayList. In part B we use Sets, Maps
 * and set operations to perform the task. 
 */
public class Membership {

    /**
     * Runs PartA and PartB
     */
    public static void main(String[] args) throws FileNotFoundException  {
        PartA();
        PartB();
    }

    /**
     * Makes an ArrayList of Person called membership and reads all the 
     * members from the members.csv file into the membership list. Reads the 
     * members in the removes.csv and adds.csv in the ArrayList called removes and
     * adds respectively. Removes the members of all members in the removes list 
     * from membership list and if the last name of members in add file are found
     * in membership list then they are added in the membership list.
     */
    public static void PartA() throws FileNotFoundException {
    	ArrayList<Person> membership = readNames("members.csv");
    	System.out.println(String.format("Read Total of %d names", membership.size()));

    	ArrayList<Person> removes = readNames("removes.csv");
    	System.out.println(String.format("Read Total of %d removes", removes.size()));
    	
    	//Get the Person's email id from the removes list    	
    	for (Person removePerson : removes) {
    		boolean emailFound = false;
    		
    		//Go through the list of membership to find the same email address
    		for (int listedMember = 0; listedMember < membership.size();listedMember++) {
    			//if the Person with the same email id exists
    			if ((removePerson.compareTo(membership.get(listedMember)))==0) {
    				//remove that person
    				membership.remove(listedMember);
    			
    				listedMember --;
    				emailFound = true;
    			}
    		}
    		//If the Person doesn't exist with the email id.
    		if (emailFound == false) {
    			System.out.println("Cannot find the email id"+removePerson.getEmail());
    		}
    	}
    	
    	System.out.println(String.format("Total of %d names after removals",membership.size()));
    	
    	ArrayList<Person> adds = readNames("adds.csv");
    	System.out.println(String.format("Read Total of %d adds", adds.size()));
    	
    	//Get the Person from the adds list
    	for (Person addPerson : adds) {
    		boolean lastNameFound = false;
    		
    		//Compare the last name to all the members in the membership list.
    		for (int membershipIndex = 0; membershipIndex < membership.size();membershipIndex++) {
    			//if the last name is equal add the person after the member

    			if (addPerson.getLastName().equals(membership.get(membershipIndex).getLastName())) {
    				membership.add(membershipIndex + 1, addPerson);
    				membershipIndex++;
    				lastNameFound = true;
    				break;
    			}
    		}
    		if (lastNameFound = false) {
    			System.out.println("Can't find a name with email id: "+addPerson.getEmail());
    		}
    	}
    	System.out.println(String.format("Total of %d names after adding",membership.size()));
    	
    	//System.out.println(membership);
		Collections.sort(membership);
		System.out.println(membership);
    	testPartA(membership);
    }
    

    /**
     * Gets the slave and master map from readNamesMap function. Makes a list
     * called changeList, which keeps a list of members who are in 
     * slave map but not in master map and of members who are in master map
     * but not in slave map. 
     */
    public static void PartB() throws FileNotFoundException {
    	
    	List <Person> changeList = new ArrayList<Person>();
    	
    	Map <String, Person> masterMap = readNamesMap("master.csv");
    	System.out.println(String.format("Read %d names from the Master Membership List",masterMap.size()));
    	
    	Map <String, Person> slaveMap = readNamesMap("slave.csv");
    	System.out.println(String.format("Read %d names from the Slave Membership List",slaveMap.size()));
    	
    	Iterator <String> itr = masterMap.keySet().iterator();
    	
    	while(itr.hasNext()) {
    		String masterKey = itr.next();
    		//Get the Person which is in the master map but not in the slave map
    		if(!slaveMap.containsKey(masterKey)) {
    			//Append +Slave at the end to the person
    			String firstname = masterMap.get(masterKey).getFirstName();
    			String lastname = masterMap.get(masterKey).getLastName();
    			String email = masterMap.get(masterKey).getEmail()+"+Slave";
    			Person addPerson = new Person (firstname,lastname,email);
    			//Add it to the changeList
    			changeList.add(addPerson);
    		}
    	}
    	
    	Set<String>cloneSlaveKey = new HashSet <String>(slaveMap.keySet());
    	
    	cloneSlaveKey.removeAll(masterMap.keySet());
    	//System.out.println(cloneSlaveKey);
    	
    	for (String slaveKey : cloneSlaveKey) {
    		//get the details from the person in the slave map
    		String firstname = slaveMap.get(slaveKey).getFirstName();
    		String lastname = slaveMap.get(slaveKey).getLastName();
    		String email = slaveMap.get(slaveKey).getEmail()+"-Slave";
    		
    		Person p = new Person(firstname,lastname,email);
    		//Add it to the change list
    		changeList.add(p);
    		
    	}
    	
    	System.out.println();
    	//Print out items in changeList on its own line
    	for (Person person : changeList) {
    		System.out.println(person);
    	}
    	
    	testPartB(masterMap,slaveMap,changeList);
    }
    /**
     * Gets a file name as a parameter and reads it into a Map where key is 
     * email id and value is type Person and returns the map.
     * @param filename
     * @return Map
     * @throws FileNotFoundException
     */
    public static Map<String, Person> readNamesMap(String filename)throws FileNotFoundException {
    
    	Map <String, Person>membersMap = new HashMap <String, Person>();
    	
    	//Open the file
    	File f = new File (filename);
    	Scanner input = new Scanner(f);
    	
    	//Discard the first line
    	input.nextLine();
    	
    	while(input.hasNextLine()) {
    		
    		//Get the next line
    		String line = input.nextLine();
    		Scanner scLine = new Scanner(line);
    		scLine.useDelimiter(",");

    		//Assign first name, last name and email address
    		String firstName = scLine.next();
    		String lastName = scLine.next();
    		String email = scLine.next();
    		//Insert it in the Map
    		Person p = new Person (firstName, lastName,email);
    		membersMap.put(p.getEmail(),p);
    	}
    	return membersMap;
    }

    /**
     * Reads a file who's file name is passed in as a parameter. Makes an 
     * ArrayList of type Person and adds all the people into the ArrayList called 
     * members from the given file and returns it.
     * @param String fileName
     * @return ArrayList of Person class
     * @throws FileNotFoundException
     */
    public static ArrayList<Person> readNames(String fileName) throws FileNotFoundException {
        // Open scanner on file "members.csv"
        File f = new File(fileName);
        Scanner sc = new Scanner(f);
        ArrayList<Person> members = new ArrayList<Person>();
        // Discard first line - it's the headers "first_name    last_name       email" etc
        sc.nextLine();

        // Loop through lines of the file
        while (sc.hasNextLine()) {
        	
            // Get the next line
            String line = sc.nextLine();
            Scanner scLine = new Scanner(line);

            // This allows us to use comma as the delimiter instead of whitespace
            scLine.useDelimiter(",");

            // Scan the line for the names & emails
            String first = scLine.next();
            String last = scLine.next();
            String email = scLine.next();
            //Add the person into the list.
            members.add(new Person(first, last, email));
        }
        //Return the members ArrayList
        return members;
    }

    /**
     * Testing code - do not modify
     * Tests functionality of Part B()
     * 
     * @param membership
     */
    public static void testPartA(ArrayList<Person> membership) {
        // Test for correct total # of names
        if (membership.size() != 445) {
            System.out.println("Wrong number of names.  There should be 445 after all removals and adds");
            return;
        }

        // Test all 10 names removed
        for (Person p: membership) {
            if (p.getEmail().contains("-")) {
                System.out.println(String.format("Oops - didn't remove person %s %s %s", p.getFirstName(), p.getLastName(), p.getEmail()));
                return;
            }
        }

        // Test that only 5 names added 
        int count = 0;
        for (Person p : membership) {
            if (p.getEmail().contains("*"))
                count++;
        }
        if (count != 5) {
            System.out.println("didn't add the right number of names");
            return;
        }

        // Check sorting & overall work at specific random items
        int[] memberIndexes = {0,10,20,30,40,400};
        String[] expectedEmails = {"Bill_frey@frey.com*", "alaine_bergesen@cox.net", "amber_monarrez@monarrez.org", 
                "apinilla@cox.net", "barrett.toyama@toyama.org", "tasia_andreason@yahoo.com"};
        for (int i = 0; i < memberIndexes.length; i++) {
            String memberEmail = membership.get(memberIndexes[i]).getEmail();
            if (!memberEmail.equals(expectedEmails[i]))
                System.out.println(String.format("Index %d expected %s but found %s",  memberIndexes[i], expectedEmails[i], memberEmail));
        }


        System.out.println("Congrats - you passed all tests");
    }

    /**
     * Testing code - do not modify
     * Tests functionality of Part B()
     * 
     * @param masterMap - map of Master names
     * @param slaveMap - map of Slave names
     * @param changesList - ArrayList of changes.   
     */
    public static void testPartB(Map<String, Person> masterMap, Map<String, Person> slaveMap, List<Person> changesList) {
        Boolean passed = true;

        // Did Master map email get modified?
        if (masterMap.size() != 100 || !masterMap.get("+E*louisa@cronauer.com").getEmail().equalsIgnoreCase("+E*louisa@cronauer.com")) {
            passed = false;
            System.out.println("Oops - did you forgot to copy the Person before appending to the email name?");
        }

        // Did slaveMap get modified?
        if (slaveMap.size() != 101) {
            passed = false;
            System.out.println("Oops - did you forgot to copy the Map before performing set Operations?");
        }

        String[] expectedEmails = {
                "+E*louisa@cronauer.com+Slave",
                "+E*alease@buemi.com+Slave",
                "-E*oretha_menter@yahoo.com-Slave",
                "-E*tsmith@aol.com-Slave",
                "-E*kris@gmail.com-Slave",
        };

        // Check total # of changes
        if (changesList.size() != expectedEmails.length) {
            passed = false;
            System.out.println(String.format("Expected %d changes, received %d changes", expectedEmails.length, changesList.size()));
        }

        // Check for each change
        for (String sExpectedEmail : expectedEmails) {
            Boolean found = false;
            Iterator<Person> iter = changesList.iterator();
            while(iter.hasNext()) {
                Person p = iter.next();
                if (p.getEmail().equals(sExpectedEmail)) {
                    found = true;
                    // remove from list so we can see what's leftover accidentally
                    iter.remove();
                }
            }// while iter
            // Output any missing changes
            if (!found) {
                passed = false;
                System.out.println(String.format("Expected to find email: %s",  sExpectedEmail));
            }
        } // for-each

        // print any extras
        for (Person pChange : changesList) {
            System.out.printf("Not expected but found change %s\n", pChange.getEmail());
        }

        if (passed)
            System.out.println("Congratulations - passed Part B");
        else
            System.out.println("Oops - failed Part B");
    }
}



/**
 * Represents a person - first, last, email
 *
 */
class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;
    private String email;


    public Person(String firstName, String lastName, String email) {
        super();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Print out the name
     */
    @Override
    public String toString() {
        return "Person [firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + "]";
    }
    /**
     * Compare the email id of two Person objects by calling the compareTo function
     */
    public int compareTo(Person otherMember) {
    	
    	return this.getEmail().compareTo(otherMember.getEmail());
    }  
}

