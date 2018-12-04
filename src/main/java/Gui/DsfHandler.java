package Gui;

import Help.Element;
import dsf.TypeCompilationUnit;
import dsf.TypeDebugSymbolSet;
import dsf.TypeEquipmentDescription;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.lang.reflect.Type;
import java.util.*;

public class DsfHandler {

    private static TypeDebugSymbolSet typeDebugSymbolSet;

    public static void setTypeEquipmentDescription(TypeEquipmentDescription typeEquipmentDescription){
        typeDebugSymbolSet = typeEquipmentDescription.getDebugSymbols().getDebugSymbolSet().get(0);
    }

    public static LinkedList<Element> getVariables(){
        LinkedList<Element> list = new LinkedList<>();
        for(TypeCompilationUnit compilationUnit : typeDebugSymbolSet.getCompilationUnit()){
            for(Object object : compilationUnit.getVariableAndCharacterDataTypeAndBooleanDataType()){
                if(getDataType(object).equals("VARIABLE")){
                    TypeCompilationUnit.Variable variable =(TypeCompilationUnit.Variable) object;
                    list.addFirst(new Element(variable.getName(), variable.getDataTypeId(), variable, compilationUnit));
                }
            }
        }

        return list;
    }

    public static LinkedList<Element> getElements(LinkedList<Element> path) {
        String variableName = path.getFirst().getName();
        LinkedList<Element> list = new LinkedList<>();
        TypeCompilationUnit scope = null;
        String dataTypeId = null;
        //Determine the scope
        dataTypeId = path.getLast().getDataTypeId();
        scope = path.getLast().getCompilationUnit();
        boolean lastIsIndex = false;
        if (scope != null) {
            if (path.getLast().getName().equals("\\d+")) {
                lastIsIndex = true;
                dataTypeId = path.get(path.size()-2).getDataTypeId();
            }
            //DETERMINE THE DATATYPE ID OF THE LAST STRING
            else {
                dataTypeId = path.getLast().getDataTypeId();
            }
        }
        for (Object object : scope.getVariableAndCharacterDataTypeAndBooleanDataType()) {
            if (getDataType(object).equals("RECORD")) {
                TypeCompilationUnit.RecordDataType recordDataType = (TypeCompilationUnit.RecordDataType) object;
                if(recordDataType.getId().equals(dataTypeId)){
                    for(TypeCompilationUnit.RecordDataType.RecordElement recordElement :recordDataType.getRecordElement()){
                        list.add(new Element(recordElement.getName(), recordElement.getDataTypeId(), recordElement,scope));

                    }
                }
            }
            else if(getDataType(object).equals("LIST")){
                TypeCompilationUnit.ListDataType listDataType = (TypeCompilationUnit.ListDataType) object;
                    if(listDataType.getId().equals(dataTypeId)){
                        if(lastIsIndex){
                            dataTypeId = listDataType.getListElement().getDataTypeId();
                            for (Object subobject : scope.getVariableAndCharacterDataTypeAndBooleanDataType()) {
                                if(getDataType(subobject).equals("RECORD")){
                                    TypeCompilationUnit.RecordDataType recordDataType = (TypeCompilationUnit.RecordDataType) subobject;
                                    if(recordDataType.getId().equals(dataTypeId)){
                                        for(TypeCompilationUnit.RecordDataType.RecordElement recordElement :recordDataType.getRecordElement()){
                                            list.add(new Element(recordElement.getName(), recordElement.getDataTypeId(), recordElement, scope));
                                        }
                                    }
                                }
                            }
                        }else
                        {
                            for(int i = 0; i<= listDataType.getListIndex().get(0).getRangeUpper().intValue(); i++) {
                                list.add(new Element(String.valueOf(i), listDataType.getListElement().getDataTypeId(), listDataType.getListElement(), scope));
                            }
                        }
                    }

            }
        }
        return list;
    }

    private static String getDataType(Object variableAndDataType){
        if(variableAndDataType instanceof TypeCompilationUnit.Variable) {
            return "VARIABLE";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.CharacterDataType){
            return "CHARACTER";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.BooleanDataType){
            return "BOOLEAN";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.IntegerDataType){
            return "INTEGER";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.FloatDataType){
            return "FLOAT";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.PointerDataType){
            return "POINTER";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.EnumDataType){
            return "ENUM";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.RecordDataType){
            return "RECORD";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.UnionDataType){
            return "UNION";
        }
        else if(variableAndDataType instanceof TypeCompilationUnit.ListDataType){
            return "LIST";
        }
        else{
            return "UNDEFINED";
        }
    }
}
