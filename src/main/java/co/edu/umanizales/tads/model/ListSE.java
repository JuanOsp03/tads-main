package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportDTO;
import co.edu.umanizales.tads.services.LocationService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListSE {
    private Node head;

    List<Kid> kids;

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

    public int size() {
        int sizeList = 0;
        Node temp = head;
        while (temp != null) {
            temp = temp.getNext();
            sizeList++;
        }
        return sizeList;
    }
    public Node addKidPos(int pos, Kid kid) {
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
        return null;
    }
    public void DeleteKidByIdentification(String identification) {
        Node temp = head;
        Node Nodeanterior = null;
        while ((temp != null) && (temp.getData().getIdentification() != identification)) {
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
    public void invert(){
        if (this.head != null){
            ListSE copiaList = new ListSE();
            Node temporal = this.head;
            while(temporal != null){
                copiaList.addToStart(temporal.getData());
                temporal = temporal.getNext();
            }
            this.head = copiaList.getHead();
        }
    }

    public  void sendKidFinalByLetter(char le){
        char Ini;
        char let;
        if (this.head != null){
            ListSE copyList = new ListSE();
            Node temporal = this.head;
            while (temporal != null){
                Ini = temporal.getData().getName().toLowerCase().charAt(0);
                let = Character.toLowerCase(le);
                if(Ini==let){
                    copyList.add(temporal.getData());
                }else{
                    copyList.addToStart(temporal.getData());
                }
                temporal.getNext();
            }
            this.head = copyList.getHead();
        }
    }

    public void orderKidsToStart() {
        if (this.head != null) {
            ListSE listSE = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listSE.addToStart(temp.getData());
                }else {
                    listSE.add(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listSE.getHead();
        }
    }


    public void changeExtremes(){
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
    }

    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,8).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public int getCountKidsByDeptCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public ReportDTO getReport(int age) {
        int i = 0;
        List<Location> list = new ArrayList<>();
        //List<ReportDTO> reportList = new ArrayList<>();
        while (this.head != null) {
            Node temp = this.head;
            if (temp.getData().getAge() > age) {
                list.add(temp.getData().getLocation());

                //list.get(i)         //Quisiera acceder a ese indice del arreglo pero no se como hacerlo
                                      // para poder agregar un valor el cual me de la cantidad de niños que se encuentran
                i++;
                temp.getNext();
            }
            else {
                temp.getNext();
            }
        }
        return null;
    }

} // fin clase