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
public class Usuario {
    
    String nombre;
    String prioridad; // "prioridad_alta", "prioridad_media", "prioridad_baja"
    Usuario sig; // Puntero para la Tabla Hash (como en tu proyecto de grafos)
    
    // Arreglo para los documentos que el usuario crea pero NO ha mandado a imprimir
    Documento[] misDocumentos; 
    int cantidadDocs;

    public Usuario(String nombre, String prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.sig = null;
        this.misDocumentos = new Documento[100]; // Capacidad inicial
        this.cantidadDocs = 0;
    }

    /**
     * Agrega un nuevo documento a la lista personal del usuario.
     */
    public void crearDocumento(String nombreDoc, int tamaño, String tipo) {
        if (cantidadDocs < misDocumentos.length) {
            // El tiempo inicial es 0 porque aún no entra a la cola de impresión
            Documento nuevo = new Documento(nombreDoc, tamaño, tipo, 0, this.nombre, false);
            misDocumentos[cantidadDocs] = nuevo;
            cantidadDocs++;
        } else {
            System.out.println("Error: El usuario ya tiene demasiados documentos.");
        }
    }

    /**
     * REQUERIMIENTO: "Un usuario podrá eliminar un documento que 
     * aún no ha sido enviado a la cola de impresión".
     */
    public void eliminarDocumentoLocal(String nombreDoc) {
        for (int i = 0; i < cantidadDocs; i++) {
            if (misDocumentos[i].nombre.equals(nombreDoc)) {
                // Al encontrarlo, movemos todos los de la derecha una posición a la izquierda
                for (int j = i; j < cantidadDocs - 1; j++) {
                    misDocumentos[j] = misDocumentos[j + 1];
                }
                misDocumentos[cantidadDocs - 1] = null; // Limpiamos el último espacio
                cantidadDocs--;
                return; 
            }
        }
    }

    /**
     * Busca un documento por su nombre dentro de la lista del usuario.
     * Útil para cuando el usuario decide darle al botón "Mandar a imprimir".
     */
    public Documento buscarDocumento(String nombreDoc) {
        for (int i = 0; i < cantidadDocs; i++) {
            if (misDocumentos[i].nombre.equals(nombreDoc)) {
                return misDocumentos[i];
            }
        }
        return null;
    }

    /**
     * Método para facilitar el llenado de JLists o JComboBox en la Interfaz.
     */
    public String[] listarDocumentos() {
        String[] nombres = new String[cantidadDocs];
        for (int i = 0; i < cantidadDocs; i++) {
            nombres[i] = misDocumentos[i].nombre + " (" + misDocumentos[i].tipo + ")";
        }
        return nombres;
    }
}
