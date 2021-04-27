
public class Hashtable <T>{
    NGen<T> [] table;

    //constructor
    Hashtable(int length){
        this.table = new NGen[length];
    }


    public void add(T item){
        NGen node = new NGen(item,null);
//        int key = hash1(item);
        int key = hash2(item);
//        int key = hash3(item);
//        int key = hash4(item);
        NGen ptr = table[key];


        if (table[key] != null) {
            if (item.equals(table[key].getData())) {
                return;
            }
            if (table[key].getNext() != null){
                while (ptr != null){
                    if (ptr.getData().equals(item)) {
                        return;
                    }
                    ptr = ptr.getNext();
                }
            }
            node.setNext(table[key]);
        }
        table[key] = node;
    }//add

    private int hash1(T key){
        int hashNum;
        // takes first/first and last characters times each other modulo of table length

        // length of 101
        // that_bad.txt - longest: 31, avg col: 3
        // gettysburg.txt - longest: 29, avg col: 2
        // proverbs.txt - longest: 43, avg col: 3
        // canterbury.txt - longest: 39, avg col: 2

        //length of 251 - specific hash
        // keywords.txt - longest: 3, avg col: 1
        // 209 empty 42 non-empty
        // this was almost as good as the second hashing function but had slightly more collision

        if (key.toString().length() < 2){
            hashNum = (key.toString().charAt(0) * key.toString().charAt(0)) % table.length;
        } else {
            hashNum = (key.toString().charAt(0) * key.toString().charAt(key.toString().length() -1) ) % table.length;
        }

        return hashNum;
    }//hash1

    //best hash
    private int hash2(T key){
        int hashNum;
        // this was the best hash function for both specific and general
        // multiplies multiple characters by prime numbers and modulo by table.length
        // more prime numbers tend to create more unique hashes
        // large improvement from previous hash especially for length of chain for general case

        // length of 101 - general hash
        // that_bad.txt - longest: 12 , avg col: 2
        // gettysburg.txt - longest: 6 , avg col: 1
        // proverbs.txt - longest: 10 , avg col: 2
        // canterbury.txt - longest: 10, avg col: 2

        //length of 251 - specific hash
        // keywords.txt - longest: 2, avg col: 1
        // 207 empty 44 non-empty

        if (key.toString().length() < 3){
            if (key.toString().length() < 2){
                hashNum = key.toString().charAt(0) * 13 % table.length;
            }else {
                hashNum = (key.toString().charAt(0) * 47 + key.toString().charAt(1) * 23) % table.length;
            }
        } else {
            hashNum = (key.toString().charAt(0) * 13 + key.toString().charAt(1) * 97 +
                    key.toString().charAt(2) * 19) % table.length;
        }

        return hashNum;
    }//hash2

    private int hash3(T key){
        int hashNum;
        // similar to previous hash but this multiplies the key but multiple prime numbers
        // this has decreased the length of one of the longest chains
        // but it also increased the average length of collisions for 2 of the file
        // this shows that more multiplication/complexity does not always
        // improve the quality of the hash

        // length of 101
        // that_bad.txt - longest: 11, avg col: 2
        // gettysburg.txt - longest: 6 , avg col: 2
        // proverbs.txt - longest: 13 , avg col: 3
        // canterbury.txt - longest: 9, avg col: 2

        // length of 251 - specific hash
        // keywords.txt - longest: 4, avg col: 1
        // 213 empty 38 non-empty
        // this was the most complex and second worst function

        if (key.toString().length() < 3){
            if (key.toString().length() < 2){
                hashNum = (((key.toString().charAt(0) * 13) * 23) * 59) % table.length;
            }else {
                hashNum = ((((key.toString().charAt(0) * 47) * 7) * 29) +
                        (((key.toString().charAt(1) * 23) * 89) * 61)) % table.length;
            }
        } else {
            hashNum = ((((key.toString().charAt(0) * 13) * 17) * 67)  + (((key.toString().charAt(1) * 97) * 109) * 11) +
                    (((key.toString().charAt(2) * 19) * 29) * 41)) % table.length;
        }


        return hashNum;
    }//hash3

    private int hash4(T key){
        int hashNum;
        // here I was attempting to make a more simple hash function
        // that didn't require and if statement and is also the only
        // one that had any division and it turned out
        // to be pretty garbage

        // length of 101
        // that_bad.txt - longest: 202, avg col: 10
        // gettysburg.txt - longest: 137 , avg col: 14
        // proverbs.txt - longest: 237 , avg col: 22
        // canterbury.txt - longest: 198, avg col: 21

        // length of 251 - specific hash
        // keywords.txt - longest: 42, avg col: 8
        // 245 empty 6 non-empty
        // this was the worst for all cases

        hashNum = ((key.toString().length() * 13) / key.toString().charAt(0) *
                (key.toString().charAt(key.toString().length() - 1))) % table.length;

        return hashNum;
    }//hash4

    public void display(){
        int count, unique = 0, longest = 0, nonEmpty = 0, empty = 0;

        // goes through entire hashtable
        for (int i = 0; i < table.length; i++){
            count = 0;

            if (table[i] != null){
                //checks for empty and non-empty
                if (table[i].getData() == null){
                    empty++;
                } else{
                    // counts how many items are chained
                    count++;
                    nonEmpty++;
                    if (table[i].getNext() != null){
                        NGen node = table[i];
                        while (node.getNext() != null){
                            count++;
                            node = node.getNext();
                        }
                    }
                }
                // finds longest chain
                if(count > longest){
                    longest = count;
                }
                unique = unique + count;
            }else{
                empty++;
            }

            System.out.print(i + ": " + count);
            System.out.println();

        }

        // prints all numbers
        if (nonEmpty != 0){
            System.out.println("Average collision length: " + (unique/nonEmpty));
        } else {
            System.out.println("Zero non-empty indices, cannot divide by zero");
        }
        System.out.println("Longest chain: " + longest);
        System.out.println("Unique tokens: " + unique);
        System.out.println("Empty indices: " + empty);
        System.out.println("Non-Empty indices: " + nonEmpty);
    }// display

    public static void main(String[] args){
        Hashtable genHash = new Hashtable(101);
        Hashtable specHash = new Hashtable(251);
        Hashtable hash2 = new Hashtable(101);
        Hashtable hash3 = new Hashtable(101);
        Hashtable hash4 = new Hashtable(101);

        //textScan taken from given TextScan class

//        TextScan.textScan("src\\that_bad.txt", hash2);
//        hash2.display();
//        TextScan.textScan("src\\proverbs.txt", hash3);
//        hash3.display();
//        TextScan.textScan("src\\canterbury.txt", hash4);
//        hash4.display();

        // general case
        TextScan.textScan("src\\gettysburg.txt", genHash);
        genHash.display();

        //specific case
        TextScan.textScan("src\\keywords.txt", specHash);
        specHash.display();

    }// main


}//Hashtable
