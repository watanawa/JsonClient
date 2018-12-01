package Gui;

import dsf.TypeCompilationUnit;
import dsf.TypeDebugSymbolSet;
import dsf.TypeEquipmentDescription;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.lang.reflect.Type;
import java.util.*;

public class DsfHandler {

    private static TypeDebugSymbolSet typeDebugSymbolSet;

    private class Element{
        String name;
        String dataTypeId;
         Element(String name, String id){
            this.name = name;
            this.dataTypeId = id;
        }

        public String getDataTypeId() {
            return dataTypeId;
        }

        public String getName() {
            return name;
        }
    }

    public static void setTypeEquipmentDescription(TypeEquipmentDescription typeEquipmentDescription){
        typeDebugSymbolSet = typeEquipmentDescription.getDebugSymbols().getDebugSymbolSet().get(0);
    }

    public static LinkedList<String> getVariables(){
        LinkedList<String> list = new LinkedList<>();
        for(TypeCompilationUnit compilationUnit : typeDebugSymbolSet.getCompilationUnit()){
            for(Object object : compilationUnit.getVariableAndCharacterDataTypeAndBooleanDataType()){
                if(getDataType(object).equals("VARIABLE")){
                    TypeCompilationUnit.Variable variable =(TypeCompilationUnit.Variable) object;
                    list.add(variable.getName());
                }
            }
        }
        Collections.sort(list);
        return list;
    }

    public static LinkedList<String> getElements(LinkedList<String> path) {
        String variableName = path.getFirst();
        LinkedList<String> list = new LinkedList<>();
        TypeCompilationUnit scope = null;
        String dataTypeId = null;
        //Determine the scope
        for (TypeCompilationUnit compilationUnit : typeDebugSymbolSet.getCompilationUnit()) {
            for (Object object : compilationUnit.getVariableAndCharacterDataTypeAndBooleanDataType()) {
                if (getDataType(object).equals("VARIABLE")) {
                    TypeCompilationUnit.Variable variable = (TypeCompilationUnit.Variable) object;
                    if (variable.getName().equals(variableName)) {
                        scope = compilationUnit;
                        if(path.size() == 1){
                            dataTypeId = variable.getDataTypeId();
                        }
                        break;
                    }
                }
            }
        }
        boolean lastIsIndex = false;
        if (scope != null) {
            if (path.getLast().equals("\\d+")) {
                lastIsIndex = true;
                for (Object object : scope.getVariableAndCharacterDataTypeAndBooleanDataType()) {
                    if (getDataType(object).equals("RECORD")) {
                        TypeCompilationUnit.RecordDataType recordDataType = (TypeCompilationUnit.RecordDataType) object;
                        for(TypeCompilationUnit.RecordDataType.RecordElement recordElement: recordDataType.getRecordElement()){
                            if (recordElement.getName().equals(path.getLast())) {
                                dataTypeId = recordDataType.getId();
                                break;
                            }
                        }
                    }
                }
            }
            //DETERMINE THE DATATYPE ID OF THE LAST STRING
            else {
                for (Object object : scope.getVariableAndCharacterDataTypeAndBooleanDataType()) {
                    switch (getDataType(object)) {
                        case "VARIABLE":
                            TypeCompilationUnit.Variable variableDataType = (TypeCompilationUnit.Variable) object;
                            if (variableDataType.getName().equals(path.getLast())) {
                                dataTypeId = variableDataType.getDataTypeId();
                                break;
                            }
                            break;
                        case "RECORD":
                            TypeCompilationUnit.RecordDataType recordDataType = (TypeCompilationUnit.RecordDataType) object;
                            for(TypeCompilationUnit.RecordDataType.RecordElement recordElement: recordDataType.getRecordElement()){
                                if (recordElement.getName().equals(path.getLast())) {
                                    dataTypeId = recordElement.getDataTypeId();
                                    break;
                                }
                            }
                            break;
                    }
                }
            }
        }
        for (Object object : scope.getVariableAndCharacterDataTypeAndBooleanDataType()) {
            if (getDataType(object).equals("RECORD")) {
                TypeCompilationUnit.RecordDataType recordDataType = (TypeCompilationUnit.RecordDataType) object;
                if(recordDataType.getId().equals(dataTypeId)){
                    for(TypeCompilationUnit.RecordDataType.RecordElement recordElement :recordDataType.getRecordElement()){
                        list.add(recordElement.getName());
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
                                            list.add(recordElement.getName());
                                        }
                                    }
                                }
                            }
                        }else
                        {
                            for(int i = 0; i<= listDataType.getListIndex().get(0).getRangeUpper().intValue(); i++) {
                                list.add(String.valueOf(i));
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
