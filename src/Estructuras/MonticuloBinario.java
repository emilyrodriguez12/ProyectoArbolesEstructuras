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
public class MonticuloBinario {
    Documento[] datos; // El arreglo que guarda los documentos
    int tamañoActual;  // Cuántos hay en la cola ahora
    int capacidadMax;  // El límite del arreglo

    public MonticuloBinario(int limite) {
        this.capacidadMax = limite;
        this.datos = new Documento[limite];
        this.tamañoActual = 0;
    }

    /**
     * REQUERIMIENTO: Primitiva INSERTAR.
     * Mete un documento al final y lo hace "trepar" hasta su lugar.
     */
    public void insertar(Documento nuevo) {
        if (tamañoActual < capacidadMax) {
            datos[tamañoActual] = nuevo;
            subirNodo(tamañoActual);
            tamañoActual++;
        }
    }

    private void subirNodo(int indice) {
        int padre = (indice - 1) / 2;
        
        // Si el tiempo del documento es menor que el de su padre, debe subir
        if (indice > 0 && datos[indice].tiempo < datos[padre].tiempo) {
            intercambiar(indice, padre);
            subirNodo(padre); // Sigue subiendo hasta que el padre sea menor
        }
    }

    /**
     * REQUERIMIENTO: Primitiva ELIMINAR_MIN.
     * Saca el documento con el tiempo más pequeño (el de arriba).
     */
    public Documento eliminarMinimo() {
        if (tamañoActual == 0) return null;

        Documento elPrimero = datos[0]; // El que se va a imprimir
        datos[0] = datos[tamañoActual - 1]; // Ponemos el último de todos arriba
        tamañoActual--;
        hundirNodo(0); // Lo bajamos hasta que recupere el orden
        return elPrimero;
    }

    private void hundirNodo(int indice) {
        int hijoIzquierdo = 2 * indice + 1;
        int hijoDerecho = 2 * indice + 2;
        int elMasPequeño = indice;

        // Comparamos con el hijo izquierdo
        if (hijoIzquierdo < tamañoActual && datos[hijoIzquierdo].tiempo < datos[elMasPequeño].tiempo) {
            elMasPequeño = hijoIzquierdo;
        }
        // Comparamos con el hijo derecho
       if (hijoDerecho < tamañoActual && datos[hijoDerecho].tiempo < datos[elMasPequeño].tiempo) {
            elMasPequeño = hijoDerecho;
        }

        // Si uno de los hijos era menor, intercambiamos y seguimos hundiendo
        if (elMasPequeño != indice) {
            intercambiar(indice, elMasPequeño);
            hundirNodo(elMasPequeño);
        }
    }

    /**
     * REQUERIMIENTO: Eliminar documento de la cola (El truco especial).
     */
    public void forzarEliminacion(String nombreDueño, String nombreArchivo) {
        for (int i = 0; i < tamañoActual; i++) {
            if (datos[i].dueño.equals(nombreDueño) && datos[i].nombre.equals(nombreArchivo)) {
                // Truco: Le ponemos un tiempo negativo para que sea el de mayor prioridad
                datos[i].tiempo = -1; 
                subirNodo(i);      // Sube a la posición 0 (la raíz)
                eliminarMinimo();  // Lo sacamos de la cola
                return;
            }
        }
    }

    // Método auxiliar para no repetir código de intercambio
    private void intercambiar(int i, int j) {
        Documento temporal = datos[i];
        datos[i] = datos[j];
        datos[j] = temporal;
    }
}
