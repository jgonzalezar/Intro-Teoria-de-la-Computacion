package Herramientas;

public class Tuple{
	private final String x; 
	private final Integer y; 
	private final Integer z; 
	/**
        * Constructor, inicializa los atributos cuando se tiene el símbolo de transición, el estado inicial y el final.
        * @param symbol Símbolo de transición
        * @param initialState Estado inicial
        * @param finalState Estado final
        */
	public Tuple(String symbol, Integer initialState, Integer finalState) { 
		this.x=symbol;
		this.y=initialState;
		this.z=finalState;
	}
	
        /**
        * Constructor, inicializa los atributos cuando se tiene el símbolo de transición, y el estado final.
        * @param symbol Símbolo de transición
        * @param finalState Estado final
        */
	public Tuple(String symbol, Integer finalState) { 
		this.x=symbol;
		this.y = null;
		this.z=finalState;
	}
	
        /**
        * @return Símbolo de transición
        */
	public String getSymbol() {
		return this.x;
	}
        /**
        * @return Estado inicial
        */
	public Integer getInitialState() {
		return this.y;
	}
        /**
        * @return Estado final
        */
	public Integer getFinalState() {
		return this.z;
	}
}
