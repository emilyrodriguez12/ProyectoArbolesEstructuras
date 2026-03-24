/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

/**
 *
 * @author MARYCRIS
 */
public class Documento {
    String nombre;
    int tamaño;
    String tipo;   // <--- NUEVO: "pdf", "docx", etc.
    int tiempo;    // Etiqueta de tiempo (prioridad en el Heap)
    String dueño;  
    boolean esPrioritario; // <--- NUEVO: para saber si se le aplicó prioridad

    public Documento(String nombre, int tamaño, String tipo, int tiempo, String dueño, boolean esPrioritario) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.dueño = dueño;
        this.esPrioritario = esPrioritario;
    }
}

