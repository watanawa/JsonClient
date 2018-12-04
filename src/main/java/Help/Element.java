package Help;

import dsf.TypeCompilationUnit;

import java.util.Iterator;


import dsf.TypeCompilationUnit;
import dsf.TypeCompilationUnit.CharacterDataType;
import dsf.TypeCompilationUnit.EnumDataType;
import dsf.TypeCompilationUnit.FloatDataType;
import dsf.TypeCompilationUnit.IntegerDataType;
import dsf.TypeCompilationUnit.ListDataType;
import dsf.TypeCompilationUnit.PointerDataType;
import dsf.TypeCompilationUnit.RecordDataType;
import dsf.TypeCompilationUnit.Variable;
import dsf.TypeCompilationUnit.RecordDataType.RecordElement;
import java.util.Iterator;

    public class Element {
        private String name;
        private String dataTypeId;
        private TypeCompilationUnit compilationUnit;
        private Object elementObject;

        public Element(String name, String id, Object elementObject, TypeCompilationUnit compilationUnit) {
            this.setName(name);
            this.setDataTypeId(id);
            this.setElementObject(elementObject);
            this.setCompilationUnit(compilationUnit);
        }

        public String getDatatype(Object object) {
            if (object instanceof ListDataType) {
                return "LIST";
            } else if (object instanceof TypeCompilationUnit.Variable) {
                return "VARIABLE";
            } else if (object instanceof RecordElement) {
                return "RECORDELEMENT";
            } else if (object instanceof RecordDataType) {
                return "RECORDDATATYPE";
            } else if (object instanceof PointerDataType) {
                return "POINTER";
            } else if (object instanceof EnumDataType) {
                return "ENUM";
            } else if (object instanceof FloatDataType) {
                return "FLOAT";
            } else if (object instanceof IntegerDataType) {
                return "INTEGER";
            } else {
                return object instanceof CharacterDataType ? "CHARACTER" : null;
            }
        }

        //TODO BEAUTIFY
        public String getSubdataType() {
            Iterator var1 = this.compilationUnit.getVariableAndCharacterDataTypeAndBooleanDataType().iterator();

            while(var1.hasNext()) {
                Object object = var1.next();
                String s = this.getDatatype(object);
                byte var5 = -1;
                switch(s.hashCode()) {
                    case -1618932450:
                        if (s.equals("INTEGER")) {
                            var5 = 4;
                        }
                        break;
                    case -747108043:
                        if (s.equals("RECORDDATATYPE")) {
                            var5 = 1;
                        }
                        break;
                    case 2133249:
                        if (s.equals("ENUM")) {
                            var5 = 5;
                        }
                        break;
                    case 2336926:
                        if (s.equals("LIST")) {
                            var5 = 0;
                        }
                        break;
                    case 55823113:
                        if (s.equals("CHARACTER")) {
                            var5 = 6;
                        }
                        break;
                    case 66988604:
                        if (s.equals("FLOAT")) {
                            var5 = 3;
                        }
                        break;
                    case 317377053:
                        if (s.equals("POINTER")) {
                            var5 = 2;
                        }
                }

                switch(var5) {
                    case 0:
                        ListDataType listDataType = (ListDataType)object;
                        if (listDataType.getId().equals(this.dataTypeId)) {
                            return "LIST";
                        }
                        break;
                    case 1:
                        RecordDataType recordDataType = (RecordDataType)object;
                        if (recordDataType.getId().equals(this.dataTypeId)) {
                            return "RECORDDATATYPE";
                        }
                        break;
                    case 2:
                        PointerDataType pointerDataType = (PointerDataType)object;
                        if (pointerDataType.getId().equals(this.dataTypeId)) {
                            return "POINTER";
                        }
                        break;
                    case 3:
                        FloatDataType floatDataType = (FloatDataType)object;
                        if (floatDataType.getId().equals(this.dataTypeId)) {
                            return "FLOAT";
                        }
                        break;
                    case 4:
                        IntegerDataType integerDataType = (IntegerDataType)object;
                        if (integerDataType.getId().equals(this.dataTypeId)) {
                            return "INTEGER";
                        }
                        break;
                    case 5:
                        EnumDataType enumDataType = (EnumDataType)object;
                        if (enumDataType.getId().equals(this.dataTypeId)) {
                            return "ENUM";
                        }
                        break;
                    case 6:
                        CharacterDataType characterDataType = (CharacterDataType)object;
                        if (characterDataType.getId().equals(this.dataTypeId)) {
                            return "CHARACTER";
                        }
                }
            }

            return null;
        }

        public Object getElementObject() {
            return this.elementObject;
        }

        public void setElementObject(Object elementObject) {
            this.elementObject = elementObject;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDataTypeId() {
            return this.dataTypeId;
        }

        public void setDataTypeId(String dataTypeId) {
            this.dataTypeId = dataTypeId;
        }

        public TypeCompilationUnit getCompilationUnit() {
            return this.compilationUnit;
        }

        public void setCompilationUnit(TypeCompilationUnit compilationUnit) {
            this.compilationUnit = compilationUnit;
        }
    }


