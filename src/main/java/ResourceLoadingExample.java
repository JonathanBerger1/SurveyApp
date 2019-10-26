//import com.sun.jna.platform.FileMonitor;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs.FileChangeEvent;
import org.apache.commons.vfs.FileListener;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.*;
import org.apache.commons.vfs.impl.DefaultFileMonitor;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;


public class ResourceLoadingExample extends Observable {
    private String path;
    private List<String> originalFileContents = new ArrayList<String>();
    public ResourceLoadingExample(){
        File a = new File("config.txt");
        {
            try {
                path = a.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    String filePath = "src/main/resources/config.txt";


    private FileListener fileListener = new FileListener() {


        @Override
        public void fileCreated(FileChangeEvent fileChangeEvent) throws Exception {

        }

        @Override
        public void fileDeleted(FileChangeEvent fileChangeEvent) throws Exception {

        }

        /**
         * this method invok when file change.
         * @param fileChangeEvent fileChangeEvent
         * @throws Exception
         */
        @Override
        public void fileChanged(FileChangeEvent fileChangeEvent) throws Exception {
            System.out.println("File Changed");
            //get new contents
            List<String> newFileContents = new ArrayList<String>();
            try {
                getFileContents(path, newFileContents);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //get the diff between the two files
            Patch<String> patch = DiffUtils.diff(originalFileContents, newFileContents);
            //get single changes in a list

            List<Delta<String>> deltas = patch.getDeltas();
            setChanged();
            notifyObservers();
            //print the changes
            for (Delta delta : deltas) {
                System.out.println(delta);
            }
        }

    };

    /**
     * this method get File Contents
     * @param path path
     * @param contents contents
     * @throws FileNotFoundException
     * @throws IOException
     */
    void getFileContents(String path, List<String> contents) throws FileNotFoundException, IOException {
        contents.clear();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = null;
        while ((line = reader.readLine()) != null) {
            contents.add(line);
        }
    }

    /**
     * this method listen to file
     */
    public void listenFile() {
//        while(true) {
        DefaultFileMonitor monitor = new DefaultFileMonitor(fileListener);
        try {
            FileObject fileObject = VFS.getManager().resolveFile(path);

            try {
                getFileContents(path, originalFileContents);
            } catch (IOException e) {
                e.printStackTrace();
            }
            monitor.addFile(fileObject);
            monitor.start();
        } catch (FileSystemException e) {
            e.printStackTrace();

//        }
        }
    }
}


//        @Override
//        public void fileDeleted(FileChangeEvent paramFileChangeEvent)
//                throws Exception {
//            // use this to handle file deletion event
//
//        }
//
//        @Override
//        public void fileCreated(FileChangeEvent paramFileChangeEvent)
//                throws Exception {
//            // use this to handle file creation event
//
//        }

//        @Override
//        public void fileChanged(FileChangeEvent paramFileChangeEvent)
//                throws Exception {
//            System.out.println("File Changed");
//            //get new contents
//            List<String> newFileContents = new ArrayList<String> ();
//            getFileContents(filePath, newFileContents);
//            //get the diff between the two files
//            Patch patch = DiffUtils.diff(originalFileContents, newFileContents);
//            //get single changes in a list
//            List<Delta> deltas = patch.getDeltas();
//            //print the changes
//            for (Delta delta : deltas) {
//                System.out.println(delta);
//            }
//        }
//    };



