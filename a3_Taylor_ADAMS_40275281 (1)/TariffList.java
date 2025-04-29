//------------------------------------------------------------
// Assignment 3
// Written by: Taylor Adams 40275281
//---------------------------------------------------------
// This class implements a linked list of TariffNode objects.

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy {

    // (b) Private attribute head pointing to the first TariffNode
    private TariffNode head;

    // c) Private attribute size indicating the current size of the list
    private int size;

    // (d) Default constructor that creates an empty list
    public TariffList() {
        this.head = null;
        this.size = 0;
    }

    // (e) Copy constructor that creates a deep copy of a TariffList
    public TariffList(TariffList list) {
        this.head = null;
        this.size = 0;
        TariffNode current = list.head;
        while (current != null) {
            addToStart(new Tariff(current.getTariff())); // Deep copy of Tariff
            current = current.getNext();
        }
    }


    // (f) Method to add a TariffNode at the start of the list
    public void addToStart(Tariff tariff) {
        TariffNode newNode = new TariffNode(tariff, head);
        head = newNode;
        size++;
    }

    // (g) Method to insert a TariffNode at a specific index
    public void insertAtIndex(Tariff tariff, int index) {
        if (index < 0 || index > size) {
            throw new NoSuchElementException("Invalid index");
        }
        if (index == 0) {
            addToStart(tariff);
        } else {
            TariffNode current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            TariffNode newNode = new TariffNode(tariff, current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    // (h) Method to delete a TariffNode at a specific index
    public void deleteFromIndex(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Invalid index");
        }
        if (index == 0) {
            deleteFromStart();
        } else {
            TariffNode current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            current.setNext(current.getNext().getNext());
            size--;
        }
    }

    // (i) Method to delete the first TariffNode in the list
    public void deleteFromStart() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        head = head.getNext();
        size--;
    }

    // (j) Method to replace a TariffNode at a specific index
    public void replaceAtIndex(Tariff tariff, int index) {
        if (index < 0 || index >= size) {
            return;
        }
        TariffNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        current.setTariff(tariff);
    }

    // (k) Method to find a TariffNode based on origin, destination, and category
    public TariffNode find(String origin, String destination, String category) {
        TariffNode current = head;
        int iterations = 0;
        
        while (current != null) {
            iterations++;
            if (current.getTariff().getOriginCountry().equals(origin) &&
                current.getTariff().getDestinationCountry().equals(destination) &&
                current.getTariff().getProductCategory().equals(category)) {
                System.out.println("Found after " + iterations + " iterations.");
                return current;
            }
            current = current.getNext();
        }
        
        System.out.println("Not found after " + iterations + " iterations.");
        return null;
    }

    // (l) Method to check if a TariffNode with matching info exists in the list
    public boolean contains(String origin, String destination, String category) {
        return find(origin, destination, category) != null;
    }

    // (m) Method to compare two TariffLists for equality
    public boolean equals(TariffList list) {
        if (this.size != list.size) {
            return false;
        }
        TariffNode current1 = this.head;
        TariffNode current2 = list.head;
        while (current1 != null && current2 != null) {
            if (!current1.getTariff().equals(current2.getTariff())) {
                return false;
            }
            current1 = current1.getNext();
            current2 = current2.getNext();
        }
        return true;
    }

    // (a) Inner class TariffNode
    class TariffNode {
        // (i) Two private attributes
        private Tariff tariff;
        private TariffNode next;

        // (ii) Default constructor
        public TariffNode() {
            this.tariff = null;
            this.next = null;
        }

        // (iii) Parameterized constructor
        public TariffNode(Tariff tariff, TariffNode next) {
            this.tariff = new Tariff(tariff); // Deep copy of Tariff
            this.next = next;
        }

        // (iv) Copy constructor
        public TariffNode(TariffNode node) {
            this.tariff = new Tariff(node.getTariff()); // Deep copy of Tariff
            this.next = null; // Assume no next node for deep copy
        }

        // (v) Clone method
        public TariffNode clone() {
            return new TariffNode(this);
        }

        // (vi) Equals method
        public boolean equals(TariffNode node) {
            if (this == node) {
                return true;
            }
            if (node == null || getClass() != node.getClass()) {
                return false;
            }
            return this.tariff.equals(node.getTariff());
        }

        // (vii) Mutator and accessor methods
        public Tariff getTariff() {
            return tariff;
        }

        public void setTariff(Tariff tariff) {
            this.tariff = new Tariff(tariff); // Deep copy of Tariff
        }

        public TariffNode getNext() {
            return next;
        }

        public void setNext(TariffNode next) {
            this.next = next;
        }
    }

    @Override
    public String EvaluateTrade(double proposedTariff, double minimumTariff) {
        if (proposedTariff >= minimumTariff) {
            return "Accepted";
        } else if (proposedTariff >= minimumTariff * 0.8) {
            return "Conditionally Accepted";
        } else {
            return "Rejected";
        }
    }

    public TariffNode getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }
}

