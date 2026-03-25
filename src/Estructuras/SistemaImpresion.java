/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Estructuras.MonticuloBinario;
import Estructuras.Documento;

/**
 * Representa el núcleo del sistema operativo encargado de administrar la simulación 
 * de la cola de impresión.
 * Esta clase centraliza e integra las estructuras de datos requeridas: 
 * la Tabla de Dispersión para la búsqueda O(1) de los usuarios y el 
 * Montículo Binario para la gestión de prioridades de los documentos.
 * * @author Emily Rodriguez y Daniel Saracual
 */
public class SistemaImpresion {
    public TablaHash todosLosUsuarios; // El almacén de usuarios (O(1))
    public MonticuloBinario colaDeImpresion; // El árbol de prioridad
    public int reloj; // El tiempo de la simulación

    public SistemaImpresion() {
        this.todosLosUsuarios = new TablaHash();
        this.colaDeImpresion = new MonticuloBinario(100); 
        this.reloj = 0;
    }

    /**
     * Incrementa el tiempo actual del sistema.
     */
    public void avanzarReloj() {
        this.reloj++;
    }

    /**
     * REQUERIMIENTO: Cargar usuarios desde archivo.
     * Lee un archivo de texto con formato ".CSV" línea por línea para instanciar 
     * y registrar usuarios en el sistema. Se espera que el archivo tenga una 
     * estructura de "usuario, tipo" omitiendo la primera línea de encabezado.
     * * @param ruta La ruta absoluta o relativa donde se encuentra el archivo CSV.
     */
    
    public void leerArchivoUsuarios(String ruta) {
        try (java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader(ruta))) {
            String linea;
            lector.readLine(); // Saltamos la primera línea de títulos
            
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String nombreU = datos[0].trim();
                    String prioridadU = datos[1].trim();
                    
                    Usuario nuevo = new Usuario(nombreU, prioridadU);
                    todosLosUsuarios.insertar(nuevo);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo CSV");
        }
    }

    /**
     * REQUERIMIENTO: Enviar documento a la cola de impresión.
     * <p>
     * Toma un documento creado por un usuario y lo introduce en la cola de prioridad.
     * Si el documento es marcado como prioritario, su etiqueta de tiempo generada 
     * por el reloj es reducida mediante una división (aplicando el nivel de prioridad 
     * del usuario), lo que le permite escalar posiciones en el montículo.
     */
    public void enviarADocACola(String nombreUsuario, String nombreDoc, boolean esPrioritario) {
        Usuario user = todosLosUsuarios.buscarUsuario(nombreUsuario);
        
        if (user != null) {
            Documento doc = user.buscarDocumento(nombreDoc);
            
            if (doc != null) {
                int etiquetaFinal = this.reloj;

                // Si se marca como prioritario, alteramos el tiempo según el tipo de usuario
                if (esPrioritario) {
                    if (user.prioridad.equals("prioridad_alta")) {
                        etiquetaFinal = this.reloj / 3;
                    } else if (user.prioridad.equals("prioridad_media")) {
                        etiquetaFinal = this.reloj / 2;
                    }
                }

                doc.tiempo = etiquetaFinal; // Asignamos su prioridad real
                colaDeImpresion.insertar(doc); // Entra al árbol
                avanzarReloj(); // Cada envío consume tiempo
            }
        }
    }

    /**
     * REQUERIMIENTO: "Liberar impresora".
     * Saca el que tiene menor tiempo (mayor prioridad).
     */
    public Documento imprimirSiguiente() {
        avanzarReloj();
        return colaDeImpresion.eliminarMinimo();
    }
}
