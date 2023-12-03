import java.io.*;
import java.util.*;

public class parseSaveFile extends EdgeConvertFileParser { 
    private BufferedReader br;
    private int numFigure, numFields, numTables;
    private String tableName, fieldName, currentLine;
    private ArrayList<EdgeField> alFields;
    private ArrayList<EdgeTable> alTables;
    private EdgeTable[] tables;
    private EdgeField[] fields;
    //constructor idk
    public parseSaveFile(File inputFile) {
        super(inputFile);
        alTables = new ArrayList<>(); // Initialize the ArrayList for tables
        alFields = new ArrayList<>(); // Initialize the ArrayList for fields
        tables = new EdgeTable[0]; // Initialize the tables array
        fields = new EdgeField[0]; // Initialize the fields array
        try {
            br = new BufferedReader(new FileReader(inputFile));
            // br = new BufferedReader(fr);
            //test for what kind of file we have
            try {
                currentLine = br.readLine();
                System.out.println("First line of the file: " + currentLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // handle the exception according to your needs
        }
        parseFile();
    }
    
    // getter for the important info
    public EdgeTable[] getEdgeTables() {
        System.out.println("returning EdgeTables :)");
        if (alTables != null) {
            return alTables.toArray(new EdgeTable[0]);
        }
        return null;
    }
    
    public EdgeField[] getEdgeFields() {
        System.out.println("returning EdgeFields");
        if (alFields != null) {
            return alFields.toArray(new EdgeField[0]);
        }
        return null;
    }
    
    //change from arrayList => array
    // private void makeArrays() { //convert ArrayList objects into arrays of the appropriate Class type
        
        
    // }
    //where the magic happens :)
    public void parseFile(){ //this method is unclear and confusing in places
        System.out.println("Begining Parse File of save file");
        try{
            StringTokenizer stTables, stNatFields, stRelFields, stField;
            EdgeTable tempTable;
            EdgeField tempField;
            currentLine = br.readLine();
            currentLine = br.readLine(); //this should be "Table: "
            System.out.println("entering table while");
            while (currentLine.startsWith("Table: ")) {
                numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
                currentLine = br.readLine(); //this should be "{"
                System.out.println("{ = " + currentLine);
                currentLine = br.readLine(); //this should be "TableName"
                System.out.println("TableName = " + currentLine);
                tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
                tempTable = new EdgeTable(numFigure + DELIM + tableName);
                //
                currentLine = br.readLine(); //this should be the NativeFields list
                if (!currentLine.startsWith("NativeFields:")) {
                    // Handle the case where the line is not what you expect
                    // You might want to log a warning or skip to the next line
                    continue;
                }

                stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
                numFields = stNatFields.countTokens();
                for (int i = 0; i < numFields; i++) {
                    tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
                }
                //                
                currentLine = br.readLine(); //this should be the RelatedTables list
                stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
                numTables = stTables.countTokens();
                for (int i = 0; i < numTables; i++) {
                    tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
                }
                tempTable.makeArrays();
                
                currentLine = br.readLine(); //this should be the RelatedFields list
                stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
                numFields = stRelFields.countTokens();
        
                for (int i = 0; i < numFields; i++) {
                    tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
                }
                System.out.println("tempTable = " + tempTable);
                alTables.add(tempTable);
                currentLine = br.readLine(); //this should be "}"
                currentLine = br.readLine(); //this should be "\n"
                currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
                System.out.println("exiting table while");
            }
            System.out.println("Entering Field while");
            while ((currentLine = br.readLine()) != null) {
                stField = new StringTokenizer(currentLine, DELIM);
                numFigure = Integer.parseInt(stField.nextToken());
                fieldName = stField.nextToken();
                tempField = new EdgeField(numFigure + DELIM + fieldName);
                tempField.setTableID(Integer.parseInt(stField.nextToken()));
                tempField.setTableBound(Integer.parseInt(stField.nextToken()));
                tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
                tempField.setDataType(Integer.parseInt(stField.nextToken()));
                tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
                tempField.setIsPrimaryKey(Boolean.valueOf(stField.nextToken()).booleanValue());
                tempField.setDisallowNull(Boolean.valueOf(stField.nextToken()).booleanValue());
                if (stField.hasMoreTokens()) { //Default Value may not be defined
                    tempField.setDefaultValue(stField.nextToken());
                }
                alFields.add(tempField);
                System.out.println("Exiting Field while");
            }
            //close up shop
            System.out.println("finished parsing save file");
            br.close();
            // this.makeArrays();
        }catch(Exception IOException){
            System.out.println("cought!!!");
            System.out.println(IOException);
            //TODO
        }//catch
    } // parseSaveFile()
}
