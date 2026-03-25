/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

/**
 * Representa un documento creado por un usuario dentro de la simulación del sistema operativo.
 * Almacena la información básica del archivo y los datos necesarios
 * para su gestión en la cola de impresión (Montículo Binario), como su 
 * etiqueta de tiempo y si fue marcado como prioritario al momento de enviarse a imprimir.
 * @author Emily Rodriguez y Daniel Saracual
 * */

public class Documento {
    public String nombre;
    public int tamaño;
    public String tipo;   // <--- NUEVO: "pdf", "docx", etc.
    public int tiempo;    // Etiqueta de tiempo (prioridad en el Heap)
    public String dueño;  
    public boolean esPrioritario; // <--- NUEVO: para saber si se le aplicó prioridad

    public Documento(String nombre, int tamaño, String tipo, int tiempo, String dueño, boolean esPrioritario) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.dueño = dueño;
        this.esPrioritario = esPrioritario;
    }
}

