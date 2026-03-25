/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Estructuras.Documento;

/**
 * Representa a un usuario dentro de la simulación del sistema operativo.
 * Esta clase cumple un doble propósito: por un lado, actúa como un nodo dentro 
 * de la Tabla de Dispersión (utilizando el atributo {@code sig} para manejar colisiones), 
 * y por otro, funciona como un contenedor local para gestionar los documentos 
 * que el usuario ha creado pero que aún no han sido enviados a la cola de impresión[cite: 32, 41].
 * * @author Emily Rodriguez y Daniel Saracual
 */
public class Usuario {
    
    String nombre;
    String prioridad; // "prioridad_alta", "prioridad_media", "prioridad_baja"
    Usuario sig; // Puntero para Tabla Hash (como en tu proyecto de grafos)
    
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
     * Crea un nuevo documento y lo guarda en la lista personal del usuario.
     * El documento se inicializa con un tiempo de 0, ya que aún no ha interactuado 
     * con el reloj del sistema ni ha ingresado a la cola de impresión.
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
     * REQUERIMIENTO: Eliminar un documento no encolado.
     * Busca y elimina un documento específico del almacenamiento local del usuario, 
     * cumpliendo con la regla de que solo se pueden eliminar localmente aquellos 
     * que no han sido enviados a la cola de impresión.
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
     * Genera una lista con los nombres y tipos de todos los documentos locales 
     * del usuario. Este método está diseñado para facilitar el llenado de 
     * componentes en la interfaz gráfica, como JLists o JComboBoxes.
     */
    public String[] listarDocumentos() {
        String[] nombres = new String[cantidadDocs];
        for (int i = 0; i < cantidadDocs; i++) {
            nombres[i] = misDocumentos[i].nombre + " (" + misDocumentos[i].tipo + ")";
        }
        return nombres;
    }
}
