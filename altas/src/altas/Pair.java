/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package altas;


/**
 * 
 * @author rafaeling
 * 
 * Clase para guardar coordenadas.
 * 
 * @param <F>
 * @param <S> 
 */
public class Pair <F,S>{
    public F first;
    public S second;

    
    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }
}
