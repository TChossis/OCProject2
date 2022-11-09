import java.io.*;
import java.util.*;

public class Main {

    public static class ReadDataResult {//Object from this class use as return on ReadDataFromFile

        private Boolean check;
        private List<String> l = new ArrayList();

        public ReadDataResult(Boolean check, List<String> l) {
            this.check= check;
            this.l = l;
        }

    }

    public static ReadDataResult ReadDataFromFile(String path) throws Exception {// Return an object containing a List with each listed diseases from original file (including duplicates) and a boolean to check if any error occured
        List<String> l = new ArrayList();
        ReadDataResult result = new ReadDataResult(true,l);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path)); //*
            String line = reader.readLine();//Read each line from source file and implement each String in List l
            while (line != null) {//*
                l.add(line);//*
                line = reader.readLine();//*
            }
            result.l=l;
            result.check = true; // Set check attribute on true because no error occurred
        }
        catch (Exception e) {
            result.l=l;
            result.check=false;// Set check attribute on false because an error occurred
            System.out.println("Source file not found");
        }
        return result;
    }

    public static HashMap<String, Integer> CreateDiseases(List<String> l) throws Exception { //Return a Hashmap containing all unique diseases with Value : 0. In parameter : List of all diseases from source folder
        HashMap<String, Integer> diseases = new HashMap<String, Integer>();
        l.forEach((disease) ->{ // Parse all list to fill the Hasmap and create all unique diseases
            if (!diseases.containsKey(disease)){
                diseases.put(disease,0);
            }
        });
        return diseases;
    }

    public static HashMap<String, Integer> CountDiseases(List<String> l,HashMap<String, Integer> diseases) throws IOException { //Return a Hashmap containing all unique diseases with Value =+1 for each occurrence in source folder. In parameter : List of all diseases from source folder, HashMap containing all unique diseases with Value : 0

        l.forEach((disease) ->{//Parse list of diseases from source folder and add +1 to dedicated Value in Hashmap for each occurrence
            diseases.put(disease,diseases.get(disease)+1);
        });
        return diseases;
    }

    public static void WriteDiseases(HashMap<String, Integer> diseases) throws IOException {// Write all Key and Value contained in the Hashmap in a result.out file. In parameter : Hashmap with all diseases in key and count in Value
        List<String> l = new ArrayList();
        FileWriter writer = new FileWriter("C:\\Users\\Thibault Chossis\\IdeaProjects\\OCProject2\\src\\result.out");
        diseases.keySet().forEach((key) -> {//Parse HashMap and write on result file
            l.add(key +" : " + diseases.get(key));
        });
        for (String str : l) {
            writer.write(str + "\n");
        }
        writer.close();
    }

    public static void main(String args[]) throws Exception {

        ReadDataResult readDataResult = ReadDataFromFile("C:\\Users\\Thibault Chossis\\IdeaProjects\\OCProject2\\symptoms.txt");//extract data from source file
        if (readDataResult.check) {// Check if extraction correctly worked
            if (readDataResult.l.isEmpty()){// Check if source file contain at least one entry
                System.out.println("Empty File");
            }
            else {
                HashMap<String, Integer> diseases = CreateDiseases(readDataResult.l);//Create HashMap of every unique disease from source File
                diseases = CountDiseases(readDataResult.l, diseases);//Count the occurence of every diseases from source file
                try{
                    WriteDiseases(diseases);//Write result on result file
                    System.out.println("Successfully executed");
                }
                catch (IOException e) {
                    System.out.println("Result file not found");
                }
            }
        }


    }

}
