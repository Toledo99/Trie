/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TriePack;

import java.util.Arrays;

/**
 *
 * @author tonotoledo
 */
public class Trie<T> {
    private NodoTrie raiz;
    protected Character[] simbolos;

    public Trie(Character[] simbolos) {
        Arrays.sort(simbolos);
        this.simbolos = simbolos.clone();
        raiz=new NodoTrie();
    }
    
    public Character[] getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(Character[] simbolos) {
        this.simbolos = simbolos;
    }
    
    public boolean busca(String llave){
        return busca(llave, raiz);
    }
    
    private boolean busca(String llave, NodoTrie actual){
        if(actual==null)
            return false;
        if(llave.compareTo("")==0)
            return actual.isFin();
        int pos=buscaCar(llave.charAt(0));
        
        return busca(llave.substring(1), actual.getSig(pos));
    }
    
    public NodoTrie buscaFin(String llave){
        return buscaFin(llave, raiz);
    }
    
    private NodoTrie buscaFin(String llave, NodoTrie actual){
        if(actual==null)
            return null;
        if(llave.compareTo("")==0)
            return actual;
        int pos=buscaCar(llave.charAt(0));
        
        return buscaFin(llave.substring(1), actual.getSig(pos));
    }
    
    public void insertar(String pal){
        if(!busca(pal))
            insertar(pal, raiz);
    }
    
    private void insertar(String llave, NodoTrie actual){
        if(actual==null)
            return;
        if(llave.compareTo("")==0){
            actual.setFin(true);
            return;
        }
        int pos=buscaCar(llave.charAt(0));
        NodoTrie temp=actual.getSig(pos);
        if(temp==null){
            temp=new NodoTrie();
            temp.setPapa(actual);
            actual.getHijos()[pos]=temp;
        }
        insertar(llave.substring(1), temp);
    }
    
    private boolean eliminar(String llave){
        boolean res=false, ban;
        NodoTrie act=buscaFin(llave);
        ban=busca(llave);
        if(ban && llave!=null && llave.compareTo("")!=0){
            act.setFin(false);
            ban=act.estaVacio();
            res=!ban;
            if(ban)
                res=eliminar(llave, act.getPapa());
        }
        return res;
    }
    
    private boolean eliminar(String llave, NodoTrie actual){
        if(actual==null)
            return false;
        int pos=buscaCar(llave.charAt(llave.length()-1));
        actual.getHijos()[pos]=null;
        if(actual.isFin())
            return true;
        if(!actual.estaVacio())
            return true;
        if(actual.getPapa()==null)
            return true;
        return eliminar(llave.substring(0, llave.length()-1), actual.getPapa());
    }
    
    private int buscaCar(Character c){
        int i=0;
        while(i<simbolos.length && simbolos[i].compareTo(c)!=0){
            i++;
        }
        if(i==simbolos.length){
            throw new RuntimeException("No existe el simbolo");
        }
        return i;
    }
    
    public String toString(){
        StringBuilder cad=new StringBuilder();
        NodoTrie<T> act=raiz;
        String res=toString(act, cad, "");
        return res;
    }
    
    private String toString(NodoTrie actual, StringBuilder cad, String pal){
        if(actual==null){
            return cad.toString();
        }
        if(actual.isFin()){
            cad.append(pal);
            cad.append("\n");
        }
        for(int i=0; i<actual.getHijos().length; i++){
            NodoTrie[] arr=actual.getHijos();
            
            if(arr[i]!=null){
                if(actual==raiz){
                    pal="";
                }
                Character car=simbolos[i];
                pal+=car;
                toString(arr[i], cad, pal);
            }
        }
        return cad.toString();
    }
    
    public static void main(String[] args){
        Character arr[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z'};
        Trie t=new Trie(arr);
        
        t.insertar("teo");
        t.insertar("teodoro");
        t.insertar("t");
        t.insertar("teodo");
        t.insertar("ab");
        t.insertar("pau");
        System.out.println(t.busca("t"));
        System.out.println(t.toString());
        System.out.println(t.eliminar("t"));
        System.out.println(t.eliminar("teo"));
        System.out.println(t.eliminar("ab"));
        System.out.println(t.eliminar("pau"));
        System.out.println(t.eliminar("teodoro"));
        System.out.println(t.toString());
    }
}
