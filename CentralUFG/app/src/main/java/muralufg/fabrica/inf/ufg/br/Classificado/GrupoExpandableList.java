package br.ufg.inf.mobile2014.projetoufg;

import java.util.ArrayList;
import java.util.List;

public class GrupoExpandableList {
	
	public String string;
	public final List<String> children = new ArrayList<String>();
	
	public GrupoExpandableList(String string) {
		this.string = string;
	}
}
