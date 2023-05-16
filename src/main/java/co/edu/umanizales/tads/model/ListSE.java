package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;


    public void add(Kid kid) throws ListSEException {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public void deleteKidByIdentification(String identification) {
        Node temp = head;
        Node Nodeanterior = null;
        if (temp != null) {
            while (!temp.getData().getIdentification().equals(identification)) {
                Nodeanterior = temp;
                temp = temp.getNext();
            }
            if (Nodeanterior == null) {
                head = temp.getNext();
            } else {
                Nodeanterior.setNext(temp.getNext());
            }
        }
    }

    public void invert() throws ListSEException {
        if (this.head == null) {
            throw new ListSEException("No hay niños para poder invertir la lista");
        } else {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart() throws ListSEException {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = head;

            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }
                temp = temp.getNext();
            }
            head = listCp.getHead();
        } else {
            throw new ListSEException("No hay niños para completar esta operacion");
        }
    }

    public void alternateKids() throws ListSEException {
        ListSE alternateList = new ListSE();

        ListSE listBoys = new ListSE();
        ListSE listGirls = new ListSE();

        Node temp = head;

        if (this.head == null && this.head.getNext() == null) {
            throw new ListSEException("No existen niños o no hay suficientes para alternar");
        } else {
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listBoys.add(temp.getData());
                } else {
                    if (temp.getData().getGender() == 'F') {
                        listGirls.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }

            Node boysNode = listBoys.getHead();
            Node girlsNode = listGirls.getHead();

            while (boysNode != null) {
                if (boysNode != null) {
                    alternateList.add(boysNode.getData());
                    boysNode = boysNode.getNext();
                }
                if (girlsNode != null) {
                    alternateList.add(girlsNode.getData());
                    girlsNode = girlsNode.getNext();
                }
            }
            this.head = alternateList.getHead();
        }
    }

    public void deleteKidByAge(Byte age) throws ListSEException {
        Node temp = head;
        ListSE listcopy = new ListSE();
        if (age <= 0) {
            throw new ListSEException("La edad debe ser mayor que cero");
        } else {
            if (this.head == null) {
                throw new ListSEException("No existen niños para realizar la operación");
            } else {

                while (temp != null) {
                    if (temp.getData().getAge() != age) {
                        listcopy.addToStart(temp.getData());
                    }
                    temp = temp.getNext();
                }
                this.head = listcopy.getHead();
            }
        }
    }

    public float getAverageAge() throws ListSEException {
        if (head != null) {
            Node temp = head;
            int count = 0;
            int age = 0;
            while (temp.getNext() != null) {
                count++;
                age = age + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) age / count;
        } else {
            throw new ListSEException("No hay niños para poder hacer el promedio de edades");
        }
    }

    public int getReportKidsByLocationCode(String code) throws ListSEException{
        int count =0;
        if(this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }else {
            throw new ListSEException("No existen niños para poder realizar la operación");
        }
        return count;
    }

    public int getReportKidsByDeptCode(String code) throws ListSEException{
        int count =0;
        if(this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }else {
            throw new ListSEException("No existen niños para poder realizar la operación");
        }
        return count;
    }

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report) throws ListSEException {
        if (head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() > age) {
                    report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        } else {
            throw new ListSEException("No existen niños para poder realizar la función");
        }
    }

    public void winPositionKid(String id, int position, ListSE listSE) throws ListSEException {
        if (head != null) {
            Node temp = this.head;
            int count = 1;

            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                temp = temp.getNext();
                count++;
            }

            int newPosition = count - position;
            Kid listCopy = temp.getData();
            listSE.deleteKidByIdentification(temp.getData().getIdentification());
            listSE.addKidsByPosition(listCopy, newPosition);

        } else {
            throw new ListSEException("No existen niños para poder realizar la función");
        }
    }

    public void lostPositionKid(String id, int position, ListSE listSE) throws ListSEException {
        Node temp = this.head;
        int count = 1;

        if (head != null) {
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                temp = temp.getNext();
                count++;
            }
            int newPosition = position + count;
            Kid listCopy = temp.getData();
            listSE.deleteKidByIdentification(temp.getData().getIdentification());
            listSE.addKidsByPosition(listCopy, newPosition);

        } else {
            throw new ListSEException("No existen niños para poder realizar la función");
        }
    }

    public int getReportByRangeAge(int letter, int last) throws ListSEException {
        Node temp = head;
        int count = 0;

        if (this.head == null) {
            throw new ListSEException("No existen niños para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getAge() >= letter && temp.getData().getAge() <= last) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        }
    }

    public void sendKidToTheEndByLetter(char letter) throws ListSEException {
        ListSE listCopy = new ListSE();
        Node temp = this.head;

        if (this.head == null) {
            throw new ListSEException("No existen niños para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getName().charAt(0) != Character.toUpperCase(letter)) {
                    listCopy.addToStart(temp.getData());
                }
            }
            temp = temp.getNext();
        }

        temp = this.head;

        if (this.head == null) {
            throw new ListSEException("No existen niños para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getName().charAt(0) == Character.toUpperCase(letter)) {
                    listCopy.add(temp.getData());
                }
                temp = temp.getNext();
            }
        }
        this.head = listCopy.getHead();
    }

    public void changeExtremes() throws ListSEException{
        if(this.head !=null && this.head.getNext() !=null) {
            Node temp = this.head;
            while(temp.getNext()!=null) {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);

        }else {
            throw new ListSEException("No existen niños para poder realizar la operación");
        }
    }

    public void addKidsByPosition(Kid kid, int pos) throws ListSEException{
        if (head != null) {
            Node newNode = new Node(kid);
            if (pos == 0) {
                newNode.setNext(head);
                head = newNode;
            } else {
                Node current = head;
                for (int i = 1; i < pos - 1; i++) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }

        } else {
            throw new ListSEException("No existen niños para poder realizar la función");
        }
    }
} // fin class