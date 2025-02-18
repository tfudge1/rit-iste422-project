/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT
 * BE LIABLE FOR ANY DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT
 * OF OR RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN
 * IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended for
 * use in the design, construction, operation or maintenance of any nuclear
 * facility.
 */

 import java.io.File;
 import java.util.Hashtable;
 import java.util.Enumeration;
 import javax.swing.filechooser.*;
 
 /**
  * A convenience implementation of FileFilter that filters out
  * all files except for those type extensions that it knows about.
  *
  * Extensions are of the type ".foo", which is typically found on
  * Windows and Unix boxes, but not on Macintosh. Case is ignored.
  *
  * Example - create a new filter that filters out all files
  * but gif and jpg image files:
  *
  *     JFileChooser chooser = new JFileChooser();
  *     ExampleFileFilter filter = new ExampleFileFilter(
  *                   new String[]{"gif", "jpg"}, "JPEG & GIF Images");
  *     chooser.addChoosableFileFilter(filter);
  *     chooser.showOpenDialog(this);
  *
  * @version 1.14 01/23/03
  * @author Jeff Dinkins
  */
 public class ExampleFileFilter extends FileFilter {
 
     private Hashtable<String, ExampleFileFilter> filters = null;
     private String description = null;
     private String fullDescription = null;
     private boolean useExtensionsInDescription = true;
 
     /**
      * Creates a new instance of ExampleFileFilter
      */
     public ExampleFileFilter() {
         this.filters = new Hashtable<>();
     }
 
     /**
      * Creates a file filter that accepts files with the given extension.
      *
      * @param extension    The extension to be accepted by the filter.
      * @param description  The description of the filter.
      */
     public ExampleFileFilter(String extension, String description) {
         this();
         if (extension != null) addExtension(extension);
         if (description != null) setDescription(description);
     }
 
     /**
      * Creates a file filter from the given string array and description.
      *
      * @param filters      Array of filters to be accepted by the filter.
      * @param description  The description of the filter.
      */
     public ExampleFileFilter(String[] filters, String description) {
         this();
         for (String filter : filters) {
             addExtension(filter);
         }
         if (description != null) setDescription(description);
     }
 
     /**
      * Tests whether the given file should be included in the list of files
      * in the directory.
      *
      * @param f  The file to be tested.
      * @return   True if the file should be included, false otherwise.
      */
     public boolean accept(File f) {
         if (f != null) {
             if (f.isDirectory()) {
                 return true;
             }
             String extension = getExtension(f);
             return extension != null && filters.containsKey(extension.toLowerCase());
         }
         return false;
     }
 
     /**
      * Returns the extension portion of the file's name.
      *
      * @param f  The file to extract the extension from.
      * @return   The file's extension.
      */
     public String getExtension(File f) {
         if (f != null) {
             String filename = f.getName();
             int i = filename.lastIndexOf('.');
             if (i > 0 && i < filename.length() - 1) {
                 return filename.substring(i + 1).toLowerCase();
             }
         }
         return null;
     }
 
     /**
      * Adds a filetype "dot" extension to filter against.
      *
      * @
    /**
     * Adds a filetype "dot" extension to filter against.
     *
     * @param extension  The extension to be added to the filter.
     */
    public void addExtension(String extension) {
        if (filters == null) {
            filters = new Hashtable<>(5);
        }
        filters.put(extension.toLowerCase(), this);
        fullDescription = null;
    }

    /**
     * Returns the human readable description of this filter.
     *
     * @return  The description of the filter.
     */
    public String getDescription() {
        if (fullDescription == null) {
            if (description == null || isExtensionListInDescription()) {
                fullDescription = description == null ? "(" : description + " (";
                Enumeration<String> extensions = filters.keys();
                if (extensions != null) {
                    fullDescription += "." + extensions.nextElement();
                    while (extensions.hasMoreElements()) {
                        fullDescription += ", ." + extensions.nextElement();
                    }
                }
                fullDescription += ")";
            } else {
                fullDescription = description;
            }
        }
        return fullDescription;
    }

    /**
     * Sets the human readable description of this filter.
     *
     * @param description  The description to be set for the filter.
     */
    public void setDescription(String description) {
        this.description = description;
        fullDescription = null;
    }

    /**
     * Determines whether the extension list should show up in the human readable description.
     *
     * @param b  Boolean value to set whether to include extension list in the description.
     */
    public void setExtensionListInDescription(boolean b) {
        useExtensionsInDescription = b;
        fullDescription = null;
    }

    /**
     * Returns whether the extension list should show up in the human readable description.
     *
     * @return  True if the extension list should be included in the description, false otherwise.
     */
    public boolean isExtensionListInDescription() {
        return useExtensionsInDescription;
    }
}
 