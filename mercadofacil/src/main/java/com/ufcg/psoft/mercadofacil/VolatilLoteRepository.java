package com.ufcg.psoft.mercadofacil;

import java.util.List;
import java.util.ArrayList;

public class VolatilLoteRepository implements LoteRepository<Lote, Long>{
	
	List<Lote> lotes = new ArrayList<>();

	@Override
	public Lote save(Lote lote) {
		lotes.add(lote);
	    return lotes.stream().findFirst().get();
	}

	@Override
	public Lote find(Long id) {
		return lotes.get(Integer.parseInt("" + id));
	}

	@Override
	public List<Lote> findAll() {
		return lotes;
	}

	@Override
	public Lote update(Lote lote) {
		lotes.clear();
		lotes.add(lote);
		return lotes.stream().findFirst().orElse(null);
	}

	@Override
	public void delete(Lote lote) {
		lotes.clear();
		
	}

	@Override
	public void deleteAll() {
		lotes.clear();
	}
	
}
