import java.io.*;
import java.util.*;

public class parseSaveFile extends EdgeConvertFileParser { 
    private ArrayList<EdgeField> alFields;
    private ArrayList<EdgeTable> alTables;
    //constructor idk
    public parseSaveFile(File inputFile) {
        super(inputFile);
        alTables = new ArrayList<>(); // Initialize the ArrayList for tables
        alFields = new ArrayList<>(); // Initialize the ArrayList for fields
        parseFile(inputFile);
    }
    
    // getter for the important info
    public EdgeTable[] getEdgeTables() {
        if (alTables != null) {
            return alTables.toArray(new EdgeTable[0]);
        }
        return null;
    }
    
    public EdgeField[] getEdgeFields() {
        if (alFields != null) {
            return alFields.toArray(new EdgeField[0]);
        }
        return null;
    }
    
    //change from arrayList => array
    // private void makeArrays() { //convert ArrayList objects into arrays of the appropriate Class type
        
        
    // }
    //where the magic happens :)
    public void parseFile(File inputFile){ //this method is unclear and confusing in places
        try{
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(inputFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace(); // handle the exception according to your needs
                //Should display error message
                return;
            }
            StringTokenizer stTables, stNatFields, stRelFields, stField;
            EdgeTable tempTable;
            EdgeField tempField;
            String currentLine = br.readLine();
            currentLine = br.readLine(); //this should be "Table: "
            while (currentLine.startsWith("Table: ")) {
                int numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
                currentLine = br.readLine(); //this should be "{"
                currentLine = br.readLine(); //this should be "TableName"
                String tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
                tempTable = new EdgeTable(numFigure + DELIM + tableName);
                //
                currentLine = br.readLine(); //this should be the NativeFields list
                if (!currentLine.startsWith("NativeFields:")) {
                    // Handle the case where the line is not what you expect
                    // You might want to log a warning or skip to the next line
                    continue;
                }

                stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
                int numFields = stNatFields.countTokens();
                for (int i = 0; i < numFields; i++) {
                    tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
                }
                //                
                currentLine = br.readLine(); //this should be the RelatedTables list
                stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
                int numTables = stTables.countTokens();
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
                alTables.add(tempTable);
                currentLine = br.readLine(); //this should be "}"
                currentLine = br.readLine(); //this should be "\n"
                currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
            }
            while ((currentLine = br.readLine()) != null) {
                stField = new StringTokenizer(currentLine, DELIM);
                int numFigure = Integer.parseInt(stField.nextToken());
                String fieldName = stField.nextToken();
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
            }
            //close up shop
            br.close();
            // this.makeArrays();
        }catch(Exception IOException){
            System.out.println(IOException);
            //TODO
        }//catch
    } // parseSaveFile()
}
