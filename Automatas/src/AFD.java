package AutomatasB;
import java.util.Arrays;
import java.util.Scanner;

public class AFD {

	public static void main(String[] args)
	{
		
		Scanner scan = new Scanner(System.in);
		
		AFD evaluarCadenas = new AFD();
		
		String[] language = {"a","b"};
		byte[][] transition = {{1,2},{3,5},{5,4},{3,5},{5,4},{6,6},{6,6}};;
		byte[] acceptedStates = {1,2,5};;
		System.out.println("Do you want to use the default automata?");
		System.out.println("If not you'll have to enter your own one (y/n) ");
		
		
		String automataDecision = scan.next();
		if ((automataDecision.toLowerCase()).equals("n"))
		{
			System.out.println("Enter the symbols of the language without spaces between them");
			System.out.print("(For example \"abcd\") ");
			String dumbLanguage = scan.next();
			language = dumbLanguage.split(""); 
			
			System.out.print("How many states does the automata have ");
			byte numberOfStates = scan.nextByte();
			byte[] states = new byte[numberOfStates];
			for (int k = 0 ; k < numberOfStates ; k++)
				states[k] = (byte)k;
			
			transition = new byte[states.length][language.length];
			System.out.println("Fill the transition table for the automata, row by row, leaving spaces between entries:");
			System.out.println("  "+Arrays.toString(language));
			for (int k = 0 ; k < states.length ; k++) {
				
				System.out.print("q" + k + " ");
				for (int l = 0 ; l < language.length ; l++)					
					transition[k][l] = scan.nextByte();	
			}
			
			System.out.print("How many acepted states are there?: ");
			byte numberOfAcceptedStates = scan.nextByte();
			acceptedStates = new byte[numberOfAcceptedStates];
			System.out.print("Which states are acepted?: ");
			for (int k = 0 ; k < acceptedStates.length ; k++)
				acceptedStates[k] = scan.nextByte();
		}

		System.out.print("Enter the string to be evaluated: ");
		//Loop the program to evaluate more than one string with the same automata.
		while (true)
		{
			String dumbChain = scan.next();
			
			if (dumbChain.equals("#"))
				break;
			
			//Transforms the string in an array
			String[] chain = dumbChain.split("");
			
			byte finalState = evaluarCadenas.stringSolver(chain, language, transition);
			int nonAccepted = 0;
			
			for (int i = 0; i < acceptedStates.length; i++)
			{
				if (acceptedStates[i] == finalState)
				{
					System.out.println("The string \"" + dumbChain + "\" was succesfuly accepted by the automata!");
					break;
				}
				nonAccepted++;
			}
			
			if (nonAccepted == acceptedStates.length)
				System.out.println("The string \"" + dumbChain + "\" was NOT accepted by the automata.");
			System.out.println("Do you want to evaluate another string?");
			System.out.print("(Enter \"#\" to exit the program) ");
		}
	}
	
	//Use an array of indices to use easily the transition function.
	//Arguments of the method are: 1. The string being evaluated. 2. The language array.
	public byte[] indexFinder(String x[], String y[])
	{
		byte[] indexFound = new byte[x.length];
		byte i = 0;
		for (int k = 0; k < x.length; k++)
		{
			while (!(x[k].equals(y[i]))) {
				i++;
			}
			indexFound[k] = i;
			i = 0;
		}
		return indexFound;
	}
	//Use the collected or default info to create a decision value for the string being evaluated.
	//Arguments of the method are:
	//1. String being evaluated.
	//2. Language array.
	//3. Transition matrix.
	public byte stringSolver(String x[], String y[], byte t[][])
	{
		byte decision = 0;
		byte[] index = indexFinder(x, y);
		for (int k = 0; k < index.length; k++) {
			decision = t[decision][index[k]];
		}
		return decision;
	}

		
}