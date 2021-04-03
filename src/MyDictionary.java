
import java.io.*;
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

    private File dictionaryFile;

    private Map<String, List<String>> dictionaryMap = new HashMap<>();

    public MyDictionary(File dictionary) {
        this.dictionaryFile = dictionary;
    }

    public MyDictionary() {
    }

    public File getDictionaryFile() {
        return dictionaryFile;
    }

    public void setDictionaryFile(File dictionaryFile) {
        this.dictionaryFile = dictionaryFile;
    }

    public  Boolean isLegal(String key){
        return (key.matches("^[a-zA-Z]+$"));
    }

    /**
     * Load the Java Object Serialization from the txt file to in-memory data structure
     */
    public void loadToMap(){
        ObjectInputStream ois = null;
        Map<String,List<String>> map;
        try {
            ois = new ObjectInputStream(new FileInputStream(dictionaryFile));
            Object obj = ois.readObject();
            if (obj instanceof Map){
                map = (Map<String, List<String>>) obj;
                dictionaryMap = map;
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

    }

    /**
     * update the txt file by serializing the hashmap to the txt file again
     * @param map
     */
    public void serializeMap(Map<String, List<String>> map){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(dictionaryFile));
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

    /**
     * Provide a word and query the meanings of a word
     * @param key
     * @return the associated meanings of the word
     */
    public String query(String key) {
        if (!isLegal(key)){
            return "Your input is not a valid word, it should only contain letters";
        }
        List<String> meanings = dictionaryMap.get(key);
        if (meanings != null && meanings.size()>0) {
            int i = 1;
            StringBuilder sb = new StringBuilder();
            sb.append("Below is the meaning(s) of the word " + "\"" + key +  "\":" + "\n");
            for (String meaning : meanings){
                sb.append( i++ + ". " + meaning.trim() + "\n");
            }
            String allMeanings = sb.toString();
            return allMeanings;
        }
        else{
            return "The word doesn't exist in our dictionary";
        }
    }


    /**
     * To add a new pair of word and its meanings to the dictionary(HashMap)
     * Synchronized keyword added here to ensure that the threads won't interfere each other
     * @param key
     * @param meanings
     * @return
     */
    public synchronized String add(String key, List<String> meanings){
        if (!isLegal(key)){
//            throw new NotAWordException("The word you're going to add is not valid, it should only contain letters");
            return "The word you're going to add is not valid, it should only contain letters";
        }
        if (dictionaryMap.get(key) != null){
//            throw new InvalidWordOperationException("The word already exists");
            return "The word already exists";
        }
        else if (meanings == null || meanings.size() == 0){
//            throw new InvalidWordOperationException("Please include at least one meaning");
            return "Please include at least one meaning";
        }
        else{
            dictionaryMap.put(key, meanings);
        }
        //updating the dictionary file
        serializeMap(dictionaryMap);
        return "The word has been successfully added!";

    }

    /**
     * provide a word and remove the word from the dictionary
     * @param key
     * @return success or error message
     */
    public String remove(String key){
        if (!isLegal(key)){
//            throw new NotAWordException("The word you're going to remove is not valid, it should only contain letters");
            return "The word you're going to remove is not valid, it should only contain letters";
        }
        if (dictionaryMap.get(key) != null) {
            dictionaryMap.remove(key);
        }
        else {
//            throw new InvalidWordOperationException("The word you're going to remove doesn't exist");
            return "The word you're going to remove doesn't exist";
        }
        serializeMap(dictionaryMap);
        return "The word has been successfully removed!";

    }

    /**
     * provide a word and its new meanings for updating
     * @param key
     * @param meanings
     * @return success or error message
     */
    public String update(String key, List<String> meanings){
        if (!isLegal(key)){
//            throw new NotAWordException("The word you're going to update is not valid, it should only contain letters");
            return "The word you're going to update is not valid, it should only contain letters";
        }

        if (dictionaryMap.get(key) != null) {
            dictionaryMap.put(key, meanings);
        }
        else {
//            throw new InvalidWordOperationException("The word you're going to update doesn't exist");
            return "The word you're going to update doesn't exist";
        }
        serializeMap(dictionaryMap);
        return "The word has been successfully updated!";

    }
    
}
