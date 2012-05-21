package no.bekk.java.camelexamples;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.stereotype.Component;

@Component
public class MyFileFilter<T> implements GenericFileFilter<T>{

    public boolean accept(GenericFile<T> file) {
        return file.getAbsoluteFilePath().endsWith(".txt");
    }

}
