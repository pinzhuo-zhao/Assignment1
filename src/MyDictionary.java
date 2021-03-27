import exceptions.InvalidWordOperationException;
import exceptions.NotAWordException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 17:15
 **/
public class MyDictionary {

    private File dictionary = new File("dictionary.txt");

    public MyDictionary(File dictionary) {
        this.dictionary = dictionary;
    }
    public MyDictionary() {
    }

    public File getDictionary() {
        return dictionary;
    }

    public void setDictionary(File dictionary) {
        this.dictionary = dictionary;
    }

    public  Boolean isLegal(String key){
        return (key.matches("^[a-zA-Z]+$"));
    }

    public  Map<String,List<String>> getMap(File file){
        ObjectInputStream ois = null;
        Map<String,List<String>> dictionaryMap = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            Object obj = ois.readObject();
            if (obj instanceof Map){
                dictionaryMap = (Map<String, List<String>>) obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dictionaryMap;
    }

    public void serializeMap(Map<String, List<String>> map){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(dictionary));
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (oos != null){
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String query(String key) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictionary)));
        if (!isLegal(key)){
            throw new NotAWordException("Your input is not a valid word, it should only contain letters");
        }
        Map<String, List<String>> dictionaryMap = getMap(dictionary);
        List<String> meanings = dictionaryMap.get(key);
        if (meanings != null && meanings.size()>0) {
            int i = 1;
            StringBuilder sb = new StringBuilder();
            sb.append("Below is the meaning(s) of the word " + "\"" + key +  "\":" + "\n");
            for (String meaning : meanings){
                sb.append( i++ + ". " + meaning + "\n");
            }
            String allMeanings = sb.toString();
            return allMeanings;
        }
        else{
            throw new InvalidWordOperationException("The word doesn't exist in our dictionary");
        }
    }
    //synchronized method here(static method),
    //it will stop other threads from calling other synchronized method in this class
    //to ensure that the threads won't interfere each other
    public synchronized void add(String key, List<String> meanings){
        if (!isLegal(key)){
            throw new NotAWordException("The word you're going to add is not valid, it should only contain letters");
        }
        Map<String, List<String>> dictionaryMap = getMap(dictionary);
        if (dictionaryMap.get(key) != null){
            throw new InvalidWordOperationException("The word already existed");
        }
        else if (meanings == null || meanings.size() == 0){
            throw new InvalidWordOperationException("Please include at least one meaning");
        }
        else{
            dictionaryMap.put(key, meanings);
        }
        //updating the dictionary file
        serializeMap(dictionaryMap);
        System.out.println("The word has been successfully added!");

    }

    public void remove(String key){
        if (!isLegal(key)){
            throw new NotAWordException("The word you're going to remove is not valid, it should only contain letters");
        }
        Map<String, List<String>> dictionaryMap = getMap(dictionary);
        if (dictionaryMap.get(key) != null) {
            dictionaryMap.remove(key);
        }
        else {
            throw new InvalidWordOperationException("The word you're going to remove doesn't exist");
        }
        serializeMap(dictionaryMap);
        System.out.println("The word has been successfully removed!");

    }

    public void update(String key, List<String> meanings){
        if (!isLegal(key)){
            throw new NotAWordException("The word you're going to update is not valid, it should only contain letters");
        }
        Map<String, List<String>> dictionaryMap = getMap(dictionary);

        if (dictionaryMap.get(key) != null) {
            dictionaryMap.replace(key, meanings);
        }
        else {
            throw new InvalidWordOperationException("The word you're going to update doesn't exist");
        }
        serializeMap(dictionaryMap);
        System.out.println("The word has been successfully updated!");

    }


    public static void main(String[] args) throws IOException {
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("wow");
//        strings.add("lol");
//        strings.add("uou");
//        add("Pz",strings);
//        remove("Pz");
        MyDictionary myDictionary = new MyDictionary();
        System.out.println(myDictionary.query("Pz"));



    }
}
