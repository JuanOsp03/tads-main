package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            newNode.setPreviousDE(newNode);
            newNode.setNextDE(newNode);
            headCircular = newNode;
        }
        if (size == 1) {
            headCircular.setPreviousDE(newNode);
            headCircular.setNextDE(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPreviousDE(headCircular);
        }
        NodeDE beforeNode = headCircular.getPreviousDE();
        if (size > 1) {
            headCircular.setPreviousDE(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPreviousDE(beforeNode);
            beforeNode.setNextDE(newNode);
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
        if (size == 1){
            headCircular.setPreviousDE(newNode);
            newNode.setNextDE(headCircular);
            headCircular.setNextDE(newNode);
            newNode.setPreviousDE(headCircular);
            headCircular = newNode;
        }
        if (size>1){
            newNode.setPreviousDE(temp);
            temp.setNextDE(newNode);
            headCircular.setPreviousDE(newNode);
            newNode.setNextDE(headCircular);
            headCircular = newNode;
        }
        size++;
    }

    //Method addPosition
    public void addPosition(Pet pet , int pos) throws ListDEException {
        NodeDE newNode = new NodeDE(pet);
        NodeDE temp = headCircular;
        int i = 0;
        if (headCircular == null){
            addPetCircular(pet);
        }else {
            if (pos == 1) {
                addPetToStartCircular(pet);
            } else {
                if (pos > size) {
                    addPetCircular(pet);
                } else {
                    if ((pos > 1) && (pos < size)) {
                        while (i < pos) {
                            temp = temp.getNextDE();
                            i++;
                        }
                        newNode.setNextDE(temp.getNextDE());
                        newNode.setPreviousDE(temp);
                        temp.setNextDE(newNode);
                    }
                }
            }
        }
        size++;
    }

    //Method takeAShower
    public Pet takePetShower(char letter) throws ListDEException{
        char direction = Character.toUpperCase(letter);
        NodeDE temp = headCircular;

        Random random = new Random();
        int num_random = random.nextInt(size+1);
        int cont = 0;

        if (headCircular == null){
            throw new ListDEException("La lista esta vacia, no se puede realizar la accion");
        }

        if (direction=='R'){
            while (cont != num_random){
                temp = temp.getNextDE();
                cont++;
            }
            if (!temp.getData().isBathed()){
                temp.getData().setBathed(true);
                return temp.getData();
            }else {
                throw new ListDEException("La mascota "+temp.getData().getName()+" identificada con el numero: "
                            + temp.getData().getIdentification() +" ya esta bañada");
            }
        }else {
            while (cont != num_random){
                temp = temp.getPreviousDE();
                cont++;
            }
            if (!temp.getData().isBathed()){
                temp.getData().setBathed(true);
                return temp.getData();
            }else {
                throw new ListDEException("La mascota "+temp.getData().getName()+" identificada con el numero: "
                        + temp.getData().getIdentification() +" ya esta bañada");
            }
        }
    }
} //  end class_ListDECircular