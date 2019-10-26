import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// javac -classpath commons-configuration2-2.4-test-sources.jar commons-configuration2-2.4-javadoc.jar commons-configuration2-2.4-sources.jar commons-beanutils-1.9.3.jar commons-configuration2-2.4.jar commons-configuration2-2.4-tests.jar commons-text-1.6.jar Load.java
public class Load {

    /**
     * load content from file
     * @param file file
     * @return list of content
     * @throws IOException
     */
    public static List<String> readfile(File file) throws IOException {

//        File file = new File("/src/main/java/resorces/config.txt");


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st;
        String[] temp = new String[2];
        List<String> retVal = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            temp = st.split("=");
            retVal.add(temp[1]);
        }
        return retVal;

    }

}
