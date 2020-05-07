package Herramientas;

public class StringTuple{
	public final String x; 
	public final String y;
	
	public StringTuple(String symbol, String finalStates) { 
		this.x = symbol;
		this.y = finalStates;
	}
	
	public String getSymbol() {
		return this.x;
	}
	public String getFinalStates() {
		return this.y;
	}
}
