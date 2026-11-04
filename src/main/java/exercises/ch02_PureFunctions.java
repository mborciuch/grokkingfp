package exercises;

import java.util.ArrayList;
import java.util.List;

public class ch02_PureFunctions {
    class TipCalculatorBad { // named TipCalculator in the book
        private List<String> names = new ArrayList<>();
        private int tipPercentage = 0;

        public void addPerson(String name) {
            names.add(name);
            if(names.size() > 5) {
                tipPercentage = 20;
            } else if(names.size() > 0) {
                tipPercentage = 10;
            }
        }

        public List<String> getNames() {
            return names;
        }

        public int getTipPercentage() {
            return tipPercentage;
        }
    }

    class TipCalculatorBad1 { // named TipCalculator in the book
        private List<String> names = new ArrayList<>();
        private int tipPercentage = 0;

        public void addPerson(String name) {
            names.add(name);
            if(names.size() > 5) {
                tipPercentage = 20;
            } else if(names.size() > 0) {
                tipPercentage = 10;
            }
        }

        public List<String> getNames() {
            return new ArrayList<>(names);
        }
        
        public void removeName(String name) {
            this.names.remove(name);
        }

        public int getTipPercentage() {
            return tipPercentage;
        }
    }

    class TipCalculatorBad2 { // named TipCalculator in the book
        private List<String> names = new ArrayList<>();

        public void addPerson(String name) {
            names.add(name);
        }

        public List<String> getNames() {
            return new ArrayList<>(names);
        }

        public int getTipPercentage() {
            if(names.size() > 5) {
               return    20;
            } else if(names.size() > 0) {
                return  10;
            }
            return 0;
        }
    }

    class TipCalculatorFunctional { // named TipCalculator in the book
        
        public static int getTipPercentage(List<String> names) {
            if(names.size() > 5) {
                return    20;
            } else if(!names.isEmpty()) {
                return  10;
            }
            return 0;
        }
    }
    
    
    
    public static void main(String[] args) {} }
