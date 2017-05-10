package com.learnwy.db.mysql;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import com.learnwy.db.KeyValue;
import com.learnwy.model.User;

public class MySQL {
    public static void main(String[] args) throws Exception {
        //ResultSet rs = testSQL("");
//		gen2("power");
//		gen2("role");
//		gen2("role_power");
//        gen("sys_menu");
//		gen2("sys_menu_power");
        // gen2("user");
//		gen2("user_role");
//        String sql = "select user_id from user ORDER  by user_id limit 0,10";
//        String sql2 = "select user_id from user ORDER  by user_id limit 10,10";
//        for (int i = 0; i < 200; i++) {
//
//            final ResultSet rs1 = conn.prepareStatement(sql).executeQuery();
//            final ResultSet rs2 = conn.prepareStatement(sql2).executeQuery();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (rs1.next()) {
//                            System.out.println("rs1::" + rs1.getLong(1));
//                            Thread.sleep(20);
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (rs2.next()) {
//                            System.out.println("rs2::" + rs2.getLong(1));
//                            Thread.sleep(20);
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
    }

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection
                    ("jdbc:mysql://localhost/learnwy_jxcc?useUnicode=true&characterEncoding=UTF-8&useUnicode=true&characterEncoding=UTF-8&" + "user=root&password=SFigiu88");
        } catch (Exception e) {
        }
    }

    public static void gen2(String tableName) throws Exception {
        StringBuffer sb = new StringBuffer();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/learnwy_jxcc?useUnicode=true&characterEncoding=UTF-8&useUnicode=true&characterEncoding=UTF-8&" + "user=root&password=SFigiu88");
            // Do something with the Connection
            PreparedStatement preS = conn.prepareStatement("select * from " + tableName);
            ResultSet rs = preS.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            LinkedList<KeyValue> kvs = new LinkedList<KeyValue>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String n = rsmd.getColumnName(i);
                String t = rsmd.getColumnTypeName(i);
                String ty = getType(t);
                kvs.add(new KeyValue(ty, n) {
                });
            }

            sb.append("package com.learnwy.db;\n");
            sb.append("import com.learnwy.db.mysql.MySQL;\n");
            sb.append("import com.learnwy.model.").append(Table(tableName)).append(";\n");
            sb.append("import java.sql.ResultSet;\n");
            sb.append("import java.sql.SQLException;\n");
            sb.append("import java.util.LinkedList;\n");
            sb.append("import java.util.List;\n");
            sb.append("public class ").append(Table(tableName)).append("DB{\n");

            String tableName2 = Table(tableName);
            sb.append("public static List<").append(Table(tableName)).append("> get").append(Table(tableName))
                    .append("s (){\n");
            sb.append("LinkedList<").append(tableName2).append("> ret = new LinkedList<").append(tableName2)
                    .append(">();\n");
            sb.append("String sql = \"select ");
            for (KeyValue kv : kvs) {
                sb.append(kv.getValue()).append(" ,");
            }
            sb.setLength(sb.length() - 1);
            sb.append("from ").append(tableName).append("\";\n");
            sb.append("ResultSet rs = MySQL.excuteSQL(sql);\n");
            sb.append(tableName2).append(" ").append(tableName).append(" = null; \n");

            sb.append("try{\nwhile(rs.next()){\n");
            sb.append(tableName).append(" = new ").append(tableName2).append("(");
            int i = 1;
            for (KeyValue kv : kvs) {
                sb.append("rs.get").append(kv.getKey().toUpperCase().charAt(0)).append(kv.getKey().substring(1))
                        .append("(").append(i++).append("),");
            }
            sb.setLength(sb.length() - 1);
            sb.append(");");
            sb.append("ret.add(").append(tableName).append(");");
            sb.append("	}");
            sb.append("} catch (Exception ex) {");
            sb.append("}");
            sb.append("return ret;");
            sb.append("}");
            sb.append("public static int del").append(tableName2).append("(long id) {");
            sb.append("	String sql = \"delete from ").append(tableName).append(" where ").append(tableName)
                    .append("_id = \" + id;");
            sb.append("	return MySQL.updateSQL(sql);");
            sb.append("}");
            sb.append("public static int update").append(tableName2).append("(").append(tableName2).append(" ")
                    .append(tableName).append(" ){\n");
            sb.append("	if (").append(tableName).append(" == null) {\n");
            sb.append("		return -1;\n");
            sb.append("	} else if (").append(tableName).append(".get").append(tableName2).append("Id() == -1) {\n");
            sb.append("		return add").append(tableName2).append("(").append(tableName).append(");\n");
            sb.append("	}\n");
            sb.append("	String sql = \"update ").append(tableName).append(" set ");
            for (KeyValue kv : kvs) {
                if (kv.getValue().equals(tableName + "_id")) {
                    continue;
                }
                sb.append(kv.getValue()).append(" = '\"+").append(tableName).append(".get").append(Table(kv.getValue()))
                        .append("() +\"',");
            }
            sb.setLength(sb.length() - 1);
            sb.append(" where ").append(tableName).append("_id = \"+").append(tableName).append(".get")
                    .append(Table((tableName + "_id"))).append("();");
            sb.append("	return MySQL.updateSQL(sql);\n");
            sb.append("}\n");

            sb.append("public static int add").append(tableName2).append("(").append(tableName2).append(" ")
                    .append(tableName).append("){\n");
            sb.append("String sql = \"insert into ").append(tableName).append("(");
            for (KeyValue kv : kvs) {
                if (kv.getValue().equals(tableName + "_id")) {
                    continue;
                }
                sb.append(kv.getValue()).append(" ,");
            }
            sb.setLength(sb.length() - 1);
            sb.append(")values( '\"");
            for (KeyValue kv : kvs) {
                if (kv.getValue().equals(tableName + "_id")) {
                    continue;
                }
                sb.append("+").append(tableName).append(".get").append(Table(kv.getValue())).append("() + \"','\"");
            }
            sb.setLength(sb.length() - 8);
            sb.append("+\"'\"");
            sb.append(";return ");
            sb.append("MySQL.updateSQL(sql);").append("}");

            sb.append("public static ").append(tableName2).append(" get").append(tableName2).append("ById(long id){");
            sb.append("String sql = \"select ");
            for (KeyValue kv : kvs) {
                sb.append(kv.getValue()).append(" ,");
            }
            sb.setLength(sb.length() - 1);
            sb.append(" from ").append(tableName).append(" where ").append(tableName).append("_id = \" + id;");
            sb.append("ResultSet rs = MySQL.excuteSQL(sql);");
            sb.append(tableName2).append(" ").append(tableName).append(" = null;");
            sb.append("try {");
            sb.append("if (rs.next()) {");
            sb.append(tableName).append(" = new ").append(tableName2).append("(");
            i = 1;
            for (KeyValue kv : kvs) {
                sb.append("rs.get").append(kv.getKey().toUpperCase().charAt(0)).append(kv.getKey().substring(1))
                        .append("(").append(i++).append("),");
            }
            sb.setLength(sb.length() - 1);
            sb.append(");	}");
            sb.append("} catch (Exception ex) {");
            sb.append("}");
            sb.append("return ").append(tableName).append(";");
            sb.append("}");

            sb.append("}");
            System.setOut(new PrintStream(new File("D:\\tmp\\git\\wy_eclipse\\src\\main\\java\\com\\learnwy\\db\\",
                    Table(tableName) + "DB.java")));
            System.out.println(sb.toString());
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

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

    public static void gen(String tableName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = null;
        StringBuffer sb = new StringBuffer();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/learnwy_jxcc?useUnicode=true&characterEncoding=UTF-8&useUnicode=true&characterEncoding=UTF-8&" + "user=root&password=SFigiu88");
            // Do something with the Connection
            PreparedStatement preS = conn.prepareStatement("select * from " + tableName);
            ResultSet rs = preS.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            sb.append("package com.learnwy.model;\n");
            sb.append("public class ").append(Table(tableName)).append("{\n");
            LinkedList<KeyValue> kvs = new LinkedList<KeyValue>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String n = rsmd.getColumnName(i);
                String t = rsmd.getColumnTypeName(i);
                String ty = getType(t);
                kvs.add(new KeyValue(ty, n) {
                });
            }

            sb.append("public ").append(Table(tableName)).append("(){\n}\n");
            sb.append("public ").append(Table(tableName)).append("(");
            for (KeyValue kv : kvs) {
                sb.append(kv.getKey()).append(" ").append(kv.getValue()).append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("){\n");
            for (KeyValue kv : kvs) {
                sb.append("this.").append(kv.getValue()).append("=").append(kv.getValue()).append(";\n");
            }
            sb.append("}\n");

            for (KeyValue kv : kvs) {
                sb.append("private ").append(kv.getKey()).append(" ").append(kv.getValue()).append(";\n");
            }

            for (KeyValue kv : kvs) {
                sb.append("public ").append(kv.getKey()).append(" get").append(Table(kv.getValue())).append("(")
                        .append("){\n");
                sb.append("return this.").append(kv.getValue()).append(";").append("\n");
                sb.append("}\n");
                sb.append("public ").append("void ").append("set").append(Table(kv.getValue())).append("(")
                        .append(kv.getKey()).append(" ").append(kv.getValue()).append("){\n");
                sb.append("this.").append(kv.getValue()).append("=").append(kv.getValue()).append(";").append("\n");
                sb.append("}\n");
            }

            sb.append("}");
            System.setOut(new PrintStream(new File("D:\\tmp\\git\\wy_eclipse\\src\\main\\java\\com\\learnwy\\model\\",
                    Table(tableName) + ".java")));
            System.out.println(sb.toString());
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static ResultSet excuteSQL(String sql) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
        try {
            PreparedStatement preS = conn.prepareStatement(sql);
            rs = preS.executeQuery();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    public static int updateSQL(String sql) {
        int rs = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
        try {
            PreparedStatement preS = conn.prepareStatement(sql);
            rs = preS.executeUpdate();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    public static ResultSet testSQL(String sql) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
        try {
            String sql1 = "select user_id,user_name,user_pwd,display_name from user limit  " + 0 + ",10 ;";
            String sql2 = "SELECT FOUND_ROWS()";
            PreparedStatement preS = conn.prepareStatement(sql1 + sql2);
            rs = preS.executeQuery();
            preS.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }
}
