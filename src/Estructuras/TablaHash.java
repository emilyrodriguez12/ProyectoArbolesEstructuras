/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Estructuras.Documento;

/**
 * Representa la Tabla de Dispersión (Hash Table) del sistema operativo.
 * Esta estructura es fundamental para solventar la limitación del montículo binario, 
 * el cual no permite búsquedas secuenciales. Almacena a los usuarios registrados 
 * y permite acceder a ellos (y a sus documentos) con una complejidad de tiempo 
 * cercana a O(1). Las colisiones se manejan mediante encadenamiento.
 * * @author Emily Rodriguez y Daniel Saracual
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

    /**
     * Inserta un nuevo usuario en la tabla hash. 
     * Si ocurre una colisión (dos nombres generan el mismo índice), se resuelve 
     * insertando el nuevo usuario al inicio de la lista enlazada en esa posición.
     * * @param nuevo El objeto Usuario que se desea registrar en el sistema.
     */
    public void insertar(Usuario nuevo) {
        int pos = funcionHash(nuevo.nombre);
        if (tabla[pos] == null) {
            tabla[pos] = nuevo;
        } else {
            nuevo.sig = tabla[pos];
            tabla[pos] = nuevo;
        }
    }

    /**
     * Busca un usuario registrado en el sistema a través de su nombre.
     * Calcula su índice mediante la función hash y recorre la lista enlazada 
     * correspondiente en caso de colisiones.
     */
    
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

    /**
     * REQUERIMIENTO: Buscar un documento específico de un usuario.
     * Este método es crucial para permitir la cancelación de documentos en la 
     * cola de impresión sin necesidad de recorrer el montículo binario.
     */
    
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
