package EntityClasses;

public class IDGenerator {
	
	/*|-----------------------------------------------------------------------------------
	 |  AUTHOR: Emma
	 |  DATE: 25/03/2017
	 |
	 |-----------------------------------------------------------------------------------
	 |
	 | DESCRIPTION: Singleton class which outputs unique IDs to newly created entities
	 |
	 | OUTPUT: Sequential integers when getNextId() is called
	 |
	 | KNOWN ISSUES: I think this is probably not a good way to handle this. The entity class could
	 |		probably do it without the need for this class to exist. Something to refactor later
	 ------------------------------------------------------------------------------------*/
	
	public static IDGenerator get(){
		return single;
	}
	
	public int getNextId(){
		int returnValue = nextId;
		nextId++;
		return returnValue;
	}
	
	private static IDGenerator single = new IDGenerator();
	private int nextId = 1;
}
