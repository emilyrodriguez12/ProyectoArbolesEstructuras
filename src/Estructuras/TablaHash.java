/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Estructuras.Documento;

/**
 *
 * @author MARYCRIS
 */
public class TablaHash {
    Usuario[] tabla;
    int tamaño;

    public TablaHash() {
        this.tamaño = 101; 
        this.tabla = new Usuario[tamaño];
    }

    // El mismo método que ya conoces
    public int funcionHash(String nombre) {
        int suma = 0;
        for (int i = 0; i < nombre.length(); i++) {
            suma += nombre.charAt(i);
        }
        return suma % tamaño;
    }

    public void insertar(Usuario nuevo) {
        int pos = funcionHash(nuevo.nombre);
        if (tabla[pos] == null) {
            tabla[pos] = nuevo;
        } else {
            nuevo.sig = tabla[pos];
            tabla[pos] = nuevo;
        }
    }

    public Usuario buscarUsuario(String nombre) {
        int pos = funcionHash(nombre);
        Usuario aux = tabla[pos];
        while (aux != null) {
            if (aux.nombre.equals(nombre)) return aux;
            aux = aux.sig;
        }
        return null;
    }

    /**
     * MÉTODO NUEVO: Buscar un documento específico de un usuario.
     * Esto permite cumplir con el requisito de "Eliminar documento de la cola".
     */
    public Documento buscarDocumentoEnCola(String nombreU, String nombreDoc) {
        Usuario u = buscarUsuario(nombreU);
        if (u != null) {
            // Buscamos en los documentos que el usuario TIENE (ya sea en cola o no)
            for (int i = 0; i < u.cantidadDocs; i++) {
                if (u.misDocumentos[i].nombre.equals(nombreDoc)) {
                    return u.misDocumentos[i];
                }
            }
        }
        return null;
    }

    public void eliminarUsuario(String nombre) {
        int pos = funcionHash(nombre);
        Usuario aux = tabla[pos];
        Usuario anterior = null;
        while (aux != null) {
            if (aux.nombre.equals(nombre)) {
                if (anterior == null) tabla[pos] = aux.sig;
                else anterior.sig = aux.sig;
                return;
            }
            anterior = aux;
            aux = aux.sig;
        }
    }
}
