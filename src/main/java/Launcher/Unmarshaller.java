package Launcher;

import dsf.ObjectFactory;
import dsf.TypeEquipmentDescription;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;

public class Unmarshaller {

    public TypeEquipmentDescription unmarshal(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            javax.xml.bind.Unmarshaller jcUnmarshaller = jc.createUnmarshaller();
            Object object = jcUnmarshaller.unmarshal(f);

            if(object instanceof JAXBElement)  {
                JAXBElement<?> element = (JAXBElement<?>)jcUnmarshaller.unmarshal(f);
                if(element.getDeclaredType().equals(TypeEquipmentDescription.class)) {
                    //THIS IS TYPESAFE
                    @SuppressWarnings("unchecked")
                    TypeEquipmentDescription equipment = ((JAXBElement<TypeEquipmentDescription>)element).getValue() ;
                    return equipment;
                }
            }


            return null;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
