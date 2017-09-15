package br.ufpe.cin.if688.parsing.analysis;

import java.util.*;

import br.ufpe.cin.if688.parsing.grammar.*;


public final class SetGenerator {
    
    public static Map<Nonterminal, Set<GeneralSymbol>> getFirst(Grammar g) {
        
    	if (g == null) throw new NullPointerException("g nao pode ser nula.");
        
    	Map<Nonterminal, Set<GeneralSymbol>> first = initializeNonterminalMapping(g);    	
    	
    	for (Nonterminal nt: g.getNonterminals()) {     	
    		Set<GeneralSymbol> x = calculaFirst(nt, first,g);     		
    		first.get(nt).addAll(x);   		
    	}        	    	
        return first;    	
    }
    
    
    public static Map<Nonterminal, Set<GeneralSymbol>> getFollow(Grammar g, Map<Nonterminal, Set<GeneralSymbol>> first) {
        
        if (g == null || first == null)
            throw new NullPointerException();
                
        Map<Nonterminal, Set<GeneralSymbol>> follow = initializeNonterminalMapping(g);       
        
        for (Nonterminal nt: g.getNonterminals()) {    	
        	Set<GeneralSymbol> cal_f = calculaFollow(first, follow, g, nt);
        	follow.get(nt).addAll(cal_f);    		
    	}         
        
        return follow;
    }
    
    
    private static Set<GeneralSymbol> calculaFollow(Map<Nonterminal, Set<GeneralSymbol>> first, Map<Nonterminal, Set<GeneralSymbol>> follow,  Grammar g, Nonterminal nt) {
    	
	   
    	Set<GeneralSymbol> followSet = null;
    	GeneralSymbol atual, proximo;
	
    	followSet = follow.get(nt);
		if(nt.equals(g.getStartSymbol())) followSet.add(SpecialSymbol.EOF);			
		
		Iterator<Production> producoesIt = g.getProductions().iterator();	
		
		while(producoesIt.hasNext()) {
			
			Production produ = producoesIt.next();			
			
			Iterator<GeneralSymbol> produIt = produ.getProduction().iterator();
    			
    		while(produIt.hasNext()) {
    				
    			atual = produIt.next();
    			    
    			if(atual.equals(nt)) {
    				
        			if(!produIt.hasNext()) {	        
        				Set<GeneralSymbol> x = calculaFollow(first, follow,g,produ.getNonterminal());
        				followSet.addAll(x);        					
        				return followSet;
        			}
        			
        			proximo = produIt.next();
        				
        			if(proximo instanceof Nonterminal) {
        					
        				if(first.get(proximo).contains(SpecialSymbol.EPSILON)){
        					Set<GeneralSymbol> x = calculaFollow(first, follow,g,produ.getNonterminal());
            				followSet.addAll(x);
            			}
            			Iterator<GeneralSymbol> aux = first.get(proximo).iterator();
            				
            			while(aux.hasNext()) {
            				GeneralSymbol aux2 = aux.next();
            				if(!aux2.equals(SpecialSymbol.EPSILON)) followSet.add(aux2);
            			}        					
        			}else followSet.add(proximo);        			       				
    			}     			
    		}			
		}
		
		return followSet;
    }
    
   
    
    public static Set<GeneralSymbol> calculaFirst(GeneralSymbol nt, Map<Nonterminal, Set<GeneralSymbol>> first, Grammar g){
    	Iterator<Production> producoes;
    	Production producao;
    	
    	Set<GeneralSymbol> conjunto  =  new HashSet<GeneralSymbol>();   		 
		 
		producoes = g.getProductions().iterator();  
		
		if(nt instanceof Terminal) {conjunto.add(nt); return conjunto;}
		if(nt instanceof SpecialSymbol) {conjunto.add(nt); return conjunto;}
	    	
	    while(producoes.hasNext()) {
	    	producao = producoes.next();
	    	
	    	if(producao.getNonterminal().equals(nt)) {	    			
	    			
	    		GeneralSymbol primeiro = producao.getProduction().get(0);	    			
	    			
	    		if (primeiro instanceof Terminal) conjunto.add(primeiro); 
	    		else if (primeiro instanceof SpecialSymbol) conjunto.add(primeiro); 
	            else {	            
	    			for (GeneralSymbol s: producao.getProduction()) {	    				
	    				Set<GeneralSymbol> x = calculaFirst(s, first,g);	    				
	    				if(!x.contains(SpecialSymbol.EPSILON)) {
	    					conjunto.addAll(x); return conjunto;
	    				}else {
	    					conjunto.addAll(x);
	    					conjunto.remove(SpecialSymbol.EPSILON);	    					
	    				}	    	                	
	    	        }    			
	            }	    		
	    	}	
	    }    	
    	
	    return conjunto; 	
    	
    }   
    
    //método para inicializar mapeamento nãoterminais -> conjunto de símbolos
    private static Map<Nonterminal, Set<GeneralSymbol>>
    initializeNonterminalMapping(Grammar g) {
    Map<Nonterminal, Set<GeneralSymbol>> result = 
        new HashMap<Nonterminal, Set<GeneralSymbol>>();

    for (Nonterminal nt: g.getNonterminals())
        result.put(nt, new HashSet<GeneralSymbol>());

    return result;
}

} 
