package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDECircular {
    private NodeDE headCircular;
    private int size;
    List<Pet> pets = new ArrayList<>();

    //Method getPets
    public Pet[] getCircular() {
        Pet[] petList = new Pet[size];
        int num = 0;
        NodeDE temp = headCircular;

        if (size == 0){
            return petList;
        }
        do {
            petList[num] = temp.getData();
            temp = temp.getNextDE();
            num++;
        } while (temp != headCircular);
        return petList;
    }

    //Method addEnd
    public void addPetCircular(Pet pet) throws ListDEException{
        NodeDE newNode = new NodeDE(pet);
        if (headCircular == null){
            headCircular = newNode;
            headCircular.setPreviousDE(headCircular);
            headCircular.setNextDE(headCircular);
        }
        if (size == 1){
            headCircular.setPreviousDE(newNode);
            headCircular.setNextDE(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPreviousDE(headCircular);
        }
        if (size > 1){
            newNode.setPreviousDE(headCircular.getPreviousDE());
            headCircular.setPreviousDE(newNode);
            newNode.setNextDE(headCircular);
        }
        size++;
    }

    //Method addToStart
    public void addPetToStartCircular(Pet pet) throws ListDEException{
        NodeDE newNode = new NodeDE(pet);
        NodeDE temp = headCircular.getPreviousDE(); //Ayudante parado en el ultimo
        if (size == 0){
            headCircular = newNode;
            headCircular.setPreviousDE(headCircular);
            headCircular.setNextDE(headCircular);
        }
        if (size > 0){
            temp.setNextDE(newNode);
            newNode.setPreviousDE(temp);
            headCircular.setPreviousDE(newNode);
            headCircular = newNode;
        }
        size++;
    }

    //Method addPosition


    public void addPosition(Pet pet , int pos) throws ListDEException {
        NodeDE newNode = new NodeDE(pet);
        NodeDE temp = headCircular;
        while (size != 0) {
            if (pos <= size && pos > 0) {
                if (pos == 1) {
                    newNode.setPreviousDE(headCircular.getPreviousDE());
                    headCircular.setPreviousDE(newNode);
                    newNode.setNextDE(headCircular);
                    headCircular = newNode;
                }
                if (pos > size) {
                    newNode.setPreviousDE(headCircular.getPreviousDE());
                    newNode.setNextDE(headCircular);
                    headCircular.setPreviousDE(newNode);
                } else {
                    while (temp.getNextDE() != headCircular) {
                        temp = temp.getNextDE();
                    }
                    newNode.setNextDE(temp.getNextDE());
                    temp.setNextDE(newNode);
                    newNode.setPreviousDE(temp);
                }
            } else {
                addPetCircular(pet);
            }
            size++;
        }
    }

/*
    public void addPetPositionCircular(Pet pet, int position) throws ListDEException{
        if (position == 1) {
            addPetToStartCircular(pet);
        }
        NodeDE newNode = new NodeDE(pet);
        if (position>size){
            addPetCircular(newNode.getData());
        }else {
            NodeDE temp = headCircular;
            for (int i = 0; i < position; i++) {
                temp = temp.getNextDE();
            }
            newNode.setNextDE(temp.getNextDE());
            temp.setNextDE(newNode);
            newNode.setPreviousDE(temp);
        }
        size++;
    }*/

    //Method takeAShower
    /*
    public int takeShower(char letter) {
        char start = Character.toLowerCase(letter);
        NodeDE temp = head;

        if (temp == null) {
            // No hay perros para bañar
            return 0;
        }

        if (start != 'd' && start != 'i') {
            // Debe ingresar 'd' (derecha) o 'i' (izquierda)
            return 0;
        }

        Random rand = new Random();
        int num = rand.nextInt(size) + 1;
        if (num == 1) {
            if (temp.getData().isDirty()) {
                temp.getData().setDirty(false);
            } else {
                // La mascota ya está bañada
                return 0;
            }
        } else {
            int count = 1;
            if (start == 'd') {
                while (count != num) {
                    temp = temp.getNext();
                    count++;
                }
            } else {
                while (count != num) {
                    temp = temp.getPrev();
                    count++;
                }
            }

            if (temp.getData().isDirty()) {
                temp.getData().setDirty(false);
            } else {
                // La mascota ya está bañada
                return 0;
            }
        }

        return num;
    }*/
} //  end class_ListDECircular