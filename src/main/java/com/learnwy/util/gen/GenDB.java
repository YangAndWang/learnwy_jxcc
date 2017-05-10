package com.learnwy.util.gen;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;

/**
 * Created by 25973 on 2017-05-10.
 */
public class GenDB {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        genMMM("food");
        genMMM("dish");
        genMMM("order");
    }

    public static void genMMM(String tableName) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        genMMM(tableName, "D:\\tmp\\git\\wy\\src\\main\\java\\com\\learnwy\\db\\");
    }

    public static void genMMM(String tableName, String filePath) throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, IOException {
        String content = genByTableName(tableName);
        File file = new File(filePath + Table(tableName) + "DB.java");
        if (!file.exists()) {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } else {
            System.out.print(filePath + Table(tableName) + "DB.java" + "文件已经存在,是否覆盖(N)");
            if (System.in.read() != '\n') {
                String tmpPath = "D:\\tmp\\git\\wy\\src\\main\\java\\com\\learnwy\\util\\gen\\temp\\";
                File f;
                int i = 0;
                while ((f = new File(tmpPath, tableName + "" + i++)).exists()) {
                    while ((f = new File(tmpPath, tableName + "DB" + i++)).exists()) {
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
    }

    private static String sbTemp1 = "package com.learnwy.db;" +
            "public class #{tableName2}DB{";
    private static String addTemp = "public static int add#{tableName2}(#{tableName2} #{tableName}){" +
            "   String sql = #{addSQL}" +
            "   int ret = MySQL.updateSQL(sql);" +
            "   return ret;" +
            "}";
    private static String updateTemp = "public static int update#{tableName2}(#{tableName2} #{tableName}){     " +
            "    if (#{tableName} == null) {" +
            "       return -1;" +
            "    } else if (#{tableName}.get#{tableName2}Id() == -1) {" +
            "       return add#{tableName2}(#{tableName});" +
            "    }" +
            "   String sql = #{updateSQL}" +
            "   return MySQL.updateSQL(sql);" +
            "}";
    private static String deleteTemp = "public static int delete#{tableName2}(#{tableName2} #{tableName}){" +
            "   if(#{tableName} == null){" +
            "       return -1;" +
            "   }" +
            "   String sql = \"delete from `#{tableName}` where #{tableName}_id = \" +#{tableName}.get#{tableName2}Id()" +
            ";" +
            "        return MySQL.updateSQL(sql);" +
            "}";
    private static String updateOrAddTemp = "public static int updateOrAdd#{tableName2}(#{tableName2} #{tableName}){" +
            "   if(#{tableName} == null){" +
            "       return -1;" +
            "   }else if(#{tableName}.get#{tableName2}Id() == -1){" +
            "       return add#{tableName2}(#{tableName});" +
            "   }else{" +
            "       return update#{tableName2}(#{tableName});" +
            "   }" +
            "}";

    private static String getAllTemp = "public static List<#{tableName2}> getAll#{tableName2}s(){" +
            "   LinkedList<#{tableName2}> ret = new LinkedList<#{tableName2}>();" +
            "   String sql = #{getALLSQL} " +
            "   ResultSet rs = MySQL.excuteSQL(sql);" +
            "   #{tableName2} #{tableName} = null;" +
            "   try {" +
            "       while (rs.next()) {" +
            "           #{tableName} = new #{tableName2}(#{constructor_tables});" +
            "           ret.add(#{tableName});" +
            "       }" +
            "   } catch (Exception ex) {" +
            "   }" +
            "   return ret;" +
            "}";
    private static String sbEndScope = "}";

    /**
     * default database is mysql
     *
     * @param tableName
     * @return
     */

    public static String genByTableName(String tableName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        StringBuffer sb = new StringBuffer();
        String tableName2 = Table(tableName);
        StringBuffer sb_add = new StringBuffer(addTemp.replaceAll("#\\{tableName\\}", tableName).replaceAll
                ("#\\{tableName2\\}", tableName2));
        StringBuffer addSQL = new StringBuffer();
        StringBuffer addSQL2 = new StringBuffer();
        StringBuffer sb_update = new StringBuffer(updateTemp.replaceAll("#\\{tableName\\}", tableName).replaceAll
                ("#\\{tableName2\\}", tableName2));
        StringBuffer updateSQL = new StringBuffer();
        StringBuffer sb_delete = new StringBuffer(deleteTemp.replaceAll("#\\{tableName\\}", tableName).replaceAll
                ("#\\{tableName2\\}", tableName2));
        StringBuffer sb_updateOrAdd = new StringBuffer(updateOrAddTemp.replaceAll("#\\{tableName\\}", tableName).replaceAll
                ("#\\{tableName2\\}", tableName2));
        StringBuffer sb_getAll = new StringBuffer(getAllTemp.replaceAll("#\\{tableName\\}", tableName).replaceAll
                ("#\\{tableName2\\}", tableName2));
        StringBuffer getAllSQL = new StringBuffer();
        //类的形参列表
        StringBuffer sb_constructor_tables = new StringBuffer();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/learnwy_jxcc?useUnicode=true&characterEncoding=UTF-8&useUnicode=true&characterEncoding=UTF-8&" + "user=root&password=SFigiu88");
            // Do something with the Connection
            PreparedStatement preS = conn.prepareStatement("select * from `" + tableName + "`");
            ResultSet rs = preS.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            LinkedList<GenModel.KeyValue> kvs = new LinkedList<GenModel.KeyValue>();
            sb.append(sbTemp1.replaceAll("#\\{tableName2\\}", tableName2));
            //addSQL
            addSQL.append("\"insert into `").append(tableName).append("`(");
            addSQL2.append(")values(\"");
            //updateSQL
            updateSQL.append("\"update `").append(tableName).append("` set \"");
            //getAll
            getAllSQL.append("\" select ");
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String n = rsmd.getColumnName(i);
                String t = rsmd.getColumnTypeName(i);
                String ty = getType(t);
                System.out.println("DBType:" + t + ",DBName:" + n);
                sb_constructor_tables.append("rs.get").append(Table(ty)).append("(").append(i).append("),");
                getAllSQL.append(n).append(",");
                if ("Date".equals(ty)) {
                    addSQL.append(n).append(",");
                    addSQL2.append("+ \"'\"+").append("new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(").append
                            (tableName).append(".get").append(Table(n)).append("()) +\"'\"").append(" +\",\"");
                    updateSQL.append("+ \"").append(n).append(" = '\"+").append("new SimpleDateFormat(\"yyyy-MM-dd " +
                            "HH:mm:ss\").format(")
                            .append(tableName).append(".get").append(Table(n)).append("()) +\"'\"").append("  +\",\"");
                } else if ("BigDecimal".equals(ty)) {
                    addSQL.append(n).append(",");
                    addSQL2.append(" + ").append(tableName).append(".get").append(Table(n)).append("().toString() " +
                            " +\",\"");
                    updateSQL.append(" + \"").append(n).append(" = \" +").append(tableName).append(".get").append
                            (Table(n)).append("().toString()   +\",\"");
                } else if ("long".equals(ty)) {
                    if ((tableName + "_id").equals(n)) {
                    } else {
                        addSQL.append(n).append(",");
                        addSQL2.append(" + ").append(tableName).append(".get").append(Table(n)).append("() " +
                                " +\",\"");
                        updateSQL.append(" + \"").append(n).append(" = \" +").append(tableName).append(".get").append
                                (Table(n)).append("() +\",\"");
                    }
                } else if ("String".equals(ty)) {
                    addSQL.append(n).append(",");
                    addSQL2.append("+ \"'\"+").append(tableName).append(".get").append(Table(n)).append("() +\"'\"")
                            .append("   +\",\"");
                    updateSQL.append(" + \"").append(n).append(" = '\"+").append(tableName).append(".get").append
                            (Table(n)).append(" () " + "  +\"'\"").append("   +\",\"");
                }
                //addSQL2.append(" \"+").append(tableName).append(".get").append(Table(n)).append("() +\",");
            }
        } catch (Exception ex) {
        }
        //( constructor_tables )
        sb_constructor_tables.setLength(sb_constructor_tables.length() - 1);
        //getAllSQL
        getAllSQL.setLength(getAllSQL.length() - 1);
        getAllSQL.append("  from `").append(tableName).append("`\";");
        sb.append(sb_getAll.toString().replaceAll("#\\{getALLSQL\\}", getAllSQL.toString()).replaceAll
                ("#\\{constructor_tables\\}", sb_constructor_tables.toString()));
        //delete
        sb.append(sb_delete);
        //updateOrAdd
        sb.append(sb_updateOrAdd);
        //add
        addSQL.setLength(addSQL.length() - 1);
        addSQL2.setLength(addSQL2.length() - 5);
        addSQL2.append("+\")\";");
        addSQL.append(addSQL2);
        sb.append(sb_add.toString().replaceAll("#\\{addSQL\\}", addSQL.toString()));
        //update
        updateSQL.setLength(updateSQL.length() - 5);
        updateSQL.append("+ \" where ").append(tableName).append("_id = \"+").append(tableName).append(".get").append
                (tableName2).append("Id();");
        sb.append(sb_update.toString().replaceAll("#\\{updateSQL\\}", updateSQL.toString()));
        return sb.append(sbEndScope).toString();
    }

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
}
