package Herramientas;

public class Tuple{
	public final String x; 
	public final Integer y; 
	public final Integer z; 
	
	public Tuple(String symbol, Integer initialState, Integer finalState) { 
		this.x=symbol;
		this.y=initialState;
		this.z=finalState;
	}
	
	public Tuple(String symbol, Integer finalState) { 
		this.x=symbol;
		this.y = null;
		this.z=finalState;
	}
	
	public String getSymbol() {
		return this.x;
	}
	public Integer getInitialState() {
		return this.y;
	}
	public Integer getFinalState() {
		return this.z;
	}
}
