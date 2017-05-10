package com.learnwy.util.gen;

import com.learnwy.util.StringUtil;

import java.io.*;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-10.
 */
public class GenModel {
    public static void main(String[] args) throws Exception {
        //System.out.println(genByTableName("dish"));
        genMMM("dish");
        genMMM("food");
        genMMM("order");
    }

    public static void genMMM(String tableName) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        genMMM(tableName, "D:\\tmp\\git\\wy\\src\\main\\java\\com\\learnwy\\model\\");
    }

    public static void genMMM(String tableName, String filePath) throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, IOException {
        String content = genByTableName(tableName);
        File file = new File(filePath + Table(tableName) + ".java");
        if (!file.exists()) {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } else {
            System.out.print(filePath + Table(tableName) + ".java" + "文件已经存在,是否覆盖(N)");
            if (System.in.read() != '\n') {
                String tmpPath = "D:\\tmp\\git\\wy\\src\\main\\java\\com\\learnwy\\util\\gen\\temp\\";
                File f;
                int i = 0;
                while ((f = new File(tmpPath, tableName + i++)).exists()) {
                }
                f.createNewFile();
                FileInputStream fis = new FileInputStream(f);
                FileOutputStream fos = new FileOutputStream(f);
                byte[] bData = new byte[1024 * 32];
                int len = 0;
                while ((len = fis.read(bData, 0, 1024 * 32)) != -1) {
                    fos.write(bData, 0, len);
                }
                fis.close();
                fos.flush();
                fos.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(content);
                bw.close();
                System.out.print(filePath + Table(tableName) + ".java" + "文件已经覆盖");

            }
        }
    }

    private static String sbTemp1 = "package com.learnwy.model;" +
            "public class #{tableName2}{";
    private static String sbTemp2 = "   private #{dataType} #{data};" +
            "   public #{dataType} get#{data2}(){" +
            "       return this.#{data};" +
            "   }" +
            "   public void set#{data2}(#{dataType} #{data}){" +
            "       this.#{data} = #{data};" +
            "   }";
    private static String sbTemp3 = "}";

    /**
     * cast table_name to TableName
     *
     * @param tableName
     * @return
     */
    public static String Table(String tableName) {
        String[] names = tableName.split("_");
        StringBuffer sb = new StringBuffer();
        for (String n : names) {
            sb.append(n.toUpperCase().charAt(0)).append(n.substring(1, n.length()));
        }
        return sb.toString();
    }

    public static String genByMyself(String className, List<KeyValue> props) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb_constructor = new StringBuffer();
        StringBuffer sb_constructor_content = new StringBuffer();

        String tableName2 = Table(className);
        sb_constructor.append("public #{tableName2}(".replaceAll("#\\{tableName2\\}\\(", tableName2));
        String ty;
        String n;
        for (KeyValue kv : props) {
            ty = kv.getKey();
            n = kv.getValue();
            sb.append(sbTemp1.replaceAll("#\\{tableName2\\}", Table(className)));
            sb.append(sbTemp2.replaceAll("#\\{dataType\\}", kv.getKey()).replaceAll("#\\{data\\}", kv.getValue()).replaceAll
                    ("#\\{data2\\}", Table(kv.getValue())));
            sb_constructor.append(ty).append(" ").append(n).append(",");
            sb_constructor_content.append("this.").append(n).append(" = ").append(n);
        }
        sb_constructor.setLength(sb_constructor.length() - 1);
        sb_constructor.append("){").append(sb_constructor_content).append("}");
        return sb.append(sb_constructor).append(sbTemp3).toString();
    }

    /**
     * default database is mysql
     *
     * @param tableName
     * @return
     */
    public static String genByTableName(String tableName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb_constructor = new StringBuffer();
        StringBuffer sb_constructor_content = new StringBuffer();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/learnwy_jxcc?useUnicode=true&characterEncoding=UTF-8&useUnicode=true&characterEncoding=UTF-8&" + "user=root&password=SFigiu88");
            // Do something with the Connection
            PreparedStatement preS = conn.prepareStatement("select * from `" + tableName + "`");
            ResultSet rs = preS.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            LinkedList<KeyValue> kvs = new LinkedList<KeyValue>();
            String tableName2 = Table(tableName);
            sb_constructor.append("public #{tableName2}(".replaceAll("#\\{tableName2\\}", tableName2));
            sb.append(sbTemp1.replaceAll("#\\{tableName2\\}", tableName2));
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String n = rsmd.getColumnName(i);
                String t = rsmd.getColumnTypeName(i);
                String ty = getType(t);
                System.out.println("DBType:" + t + ",DBName:" + n);
                sb.append(sbTemp2.replaceAll("#\\{dataType\\}", ty).replaceAll("#\\{data\\}", n).replaceAll
                        ("#\\{data2\\}", Table(n)));
                sb_constructor.append(ty).append(" ").append(n).append(",");
                sb_constructor_content.append("this.").append(n).append(" = ").append(n).append(";");
                //                kvs.add(new KeyValue(ty, n) {
//                });
            }
        } catch (Exception ex) {
        }
        sb_constructor.setLength(sb_constructor.length() - 1);
        sb_constructor.append("){");
        sb_constructor.append(sb_constructor_content);
        sb_constructor.append("}");
        return sb.append(sb_constructor).append(sbTemp3).toString();
    }

    public static class KeyValue {
        String key;
        String value;

        public KeyValue(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public KeyValue() {
            super();
        }
    }

    public static String getType(String mysqlType) {
        String ret = "unKnow";
        switch (mysqlType) {
            case "VARCHAR":
                ret = "String";
                break;
            case "INT":
                ret = "long";
                break;
            case "DECIMAL":
                ret = "BigDecimal";
                break;
            case "TIMESTAMP":
                ret = "Date";
                break;
            default:
                ret = "unknow";
                break;
        }
        return ret;
    }
}
