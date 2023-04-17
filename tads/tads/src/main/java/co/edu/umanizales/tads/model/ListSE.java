package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class ListSE {
    private Node head;
    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid){
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
    }

    /* Adicionar en posición
    primero tengo que recorrer la lista para obtener al niño que se encuentra en esa posicion
    ademas de obtener al niño que se encuentra atras de la posicion indicada
    para luego con esto datos, preguntar:
    -si exite un niño en la posicion anterior y en la posicion indicada
        meto al niño en un costal y lo asigno en el .next del niño anterior a la posicion
        luego al nuevo niño le aigno el punto next o el niño siguiente
    -sino existe un niño en la posicion anterior o en la indicada
        significa que vuelvo al nuevo niño en cabeza o lo asigno al final de la lista
    */
    public int size() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();
        }
        return size;
    }

    public void addKidPos(int pos, Kid kid) {
        if (size() >= pos) {
            if (pos == 0) {
                addToStart(kid);
            } else {
                Node temp = head;
                for (int i = 0; i < pos - 1; i++) {
                    temp = temp.getNext();
                }
                Node newNode = new Node(kid);
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
        } else {
            add(kid);
        }
    }

    public void DeleteKidByIdentification(String identification) {
        Node temp = head;
        Node Nodeanterior = null;
        while (temp != null && temp.getData().getIndentification()!=(identification)) {
            Nodeanterior = temp;
            temp= temp.getNext();
        }
        if (temp != null){
            if (Nodeanterior== null){
                head=temp.getNext();
            }
            else {
                Nodeanterior.setNext(temp.getNext());
            }
        }
    }

} // fin clase