// package src.main.java;

import java.io.*;
import javax.swing.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class EdgeConvertFileParser {
   //private String filename = "test.edg";
   private File parseFile;
   private FileReader fr;
   private BufferedReader br;
   private String currentLine;
   private EdgeTable[] tables;
   private EdgeField[] fields;
   public boolean testFailed = false;
   public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this
   public static final String DELIM = "|";
   private static Logger logger = LogManager.getLogger(EdgeConvertFileParser.class);
   // public parseSaveFile readSaveFile;
   
   
   public EdgeConvertFileParser(File constructorFile) {
      parseFile = constructorFile;
      // this.openFile(parseFile);
   }
   
   public EdgeTable[] getEdgeTables() {
      return tables;
   }
   
   public EdgeField[] getEdgeFields() {
      return fields;
   }

   public void setEdgeFields(EdgeField[] _fields){
      fields = _fields;
   }

   public void setEdgeTables(EdgeTable[] _tables){
      tables = _tables;
   }
   
//    public void openFile(File inputFile) {
//       openFile(inputFile, true);
//   }

//   public void openFile(File inputFile, boolean fatalError) {
//       try {
//          fr = new FileReader(inputFile);
//          br = new BufferedReader(fr);
//          //test for what kind of file we have
//          currentLine = br.readLine().trim();
//          if (currentLine.startsWith(EDGE_ID)) { //the file chosen is an Edge Diagrammer file
//             parseEdgeFile ef = new parseEdgeFile(inputFile);
//             tables = ef.getEdgeTables();
//             fields = ef.getEdgeFields();
//          } else {
//             if (currentLine.startsWith(SAVE_ID)) { //the file chosen is a Save file created by this application
//                parseSaveFile sf = new parseSaveFile(inputFile);
//                tables = sf.getEdgeTables();
//                fields = sf.getEdgeFields();
//             } else { //the file chosen is something else
//                testFailed = true;
//                if (fatalError) {
//                   JOptionPane.showMessageDialog(null, "Unrecognized file format");
//               }
//             }
//          }
//       } // try
//       catch (FileNotFoundException fnfe) {
//          testFailed = true;
//          logger.warn("Cannot find \"" + inputFile.getName() + "\".");
//          if (fatalError) {
//             JOptionPane.showMessageDialog(null, "File Not Found");
//             //System.exit(0);
//          }
//      } // catch FileNotFoundException
//      catch (IOException ioe) {
//          logger.error(ioe);
//          if (fatalError) {
//             JOptionPane.showMessageDialog(null, "Failure encountered while reading/writing");
//             //System.exit(0);
//          }
//      } // catch IOException
//    } // openFile()
} // EdgeConvertFileHandler
