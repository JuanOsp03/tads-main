package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;

    //List<Kid> kids;

    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
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
        size++;
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
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart() throws ListSEException{
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
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
                if(temp.getData().getLocation().getCode().equals(code)){
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

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge()>age){
                    report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

    /*
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

    public Node addKidPos(int pos, Kid kid) {
        if (size >= pos) {
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
     */
} // fin clase