// package src.main.java;

import java.io.*;

public class EdgeConvertFileParser {
   private EdgeTable[] tables;
   private EdgeField[] fields;
   public boolean testFailed = false;
   public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this
   public static final String DELIM = "|";
   
   
   public EdgeConvertFileParser(File constructorFile) {
   }
   
   public EdgeTable[] getEdgeTables() {
      return tables;
   }
   
   public EdgeField[] getEdgeFields() {
      return fields;
   }

   public void setEdgeFields(EdgeField[] _field){
      fields = _field;
   }

   public void setEdgeTables(EdgeTable[] _tables){
      tables = _tables;
   }
} // EdgeConvertFileHandler
