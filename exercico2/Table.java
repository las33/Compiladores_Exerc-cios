package br.ufpe.cin.if688.table;


import br.ufpe.cin.if688.parsing.analysis.*;
import br.ufpe.cin.if688.parsing.grammar.*;
import java.util.*;


public final class Table {
	private Table() {    }

	public static Map<LL1Key, List<GeneralSymbol>> createTable(Grammar g) throws NotLL1Exception {
        if (g == null) throw new NullPointerException();

        Map<Nonterminal, Set<GeneralSymbol>> first =
            SetGenerator.getFirst(g);
        Map<Nonterminal, Set<GeneralSymbol>> follow =
            SetGenerator.getFollow(g, first);

        Map<LL1Key, List<GeneralSymbol>> parsingTable = 
            new HashMap<LL1Key, List<GeneralSymbol>>();
        
        
    	Iterator<Production> producoesIt = g.getProductions().iterator();
    	
    	while(producoesIt.hasNext()) {
    		
    		Production producao = producoesIt.next();
    		
    		List<LL1Key> ll1keyList = calculaTableProducao(producao,first, follow,g);
    		
    		
			for(LL1Key ll1key:ll1keyList ) {
				parsingTable.put(ll1key, producao.getProduction());
    		}    		
    	}

        /*
         * Implemente aqui o método para retornar a parsing table
         */
        
        return parsingTable;
    }
	
    
    static private List<LL1Key> calculaTableProducao(Production producao, Map<Nonterminal, Set<GeneralSymbol>> first, Map<Nonterminal, Set<GeneralSymbol>> follow,Grammar g){
    	
    	
    	List<LL1Key> ll1key = new ArrayList<LL1Key>();
    
    	Set<GeneralSymbol> conjunto  =  new HashSet<GeneralSymbol>(); 	     	
	   	    			
			
	   GeneralSymbol primeiro = producao.getProduction().get(0);	
	   
	   if(primeiro.equals(SpecialSymbol.EPSILON) || primeiro.equals(SpecialSymbol.EOF)) {
		   
		 Set<GeneralSymbol> follows = follow.get(producao.getNonterminal());
		 
		 Iterator<GeneralSymbol> followsIt = follows.iterator();
		 
		 while(followsIt.hasNext()) {
			 GeneralSymbol atual = followsIt.next();
			 ll1key.add(new LL1Key(producao.getNonterminal(), atual));				 
		 }
		   
	   }
	   
	   
	   else {		   
           
			for (GeneralSymbol s: producao.getProduction()) {	
				
				if (s instanceof Terminal) {ll1key.add(new LL1Key(producao.getNonterminal(), s)); return ll1key;}
				
				Set<GeneralSymbol> x = first.get(s);	    				
				
				conjunto.addAll(x); 
				
				Iterator<GeneralSymbol> conjuntoIt = conjunto.iterator();
				
				while(conjuntoIt.hasNext()) {
					
					GeneralSymbol atual = conjuntoIt.next();
					
					if(!atual.equals(SpecialSymbol.EPSILON)) {
						ll1key.add(new LL1Key(producao.getNonterminal(), atual));
					}						
				}					
				if(!(conjunto.size() == 1 && conjunto.contains(SpecialSymbol.EPSILON))) {
					return ll1key;
				}
	        }		   
	   }	    
	
    return ll1key; 	
    	
    }   
}
