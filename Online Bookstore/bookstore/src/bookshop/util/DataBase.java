package bookshop.util;
/**
 * <p>数据库连接专用包 </p>
 * <p>Copyright: wxy Copyright (c) 2007</p>
 * <p>Company:Ling Du book shop online </p>
 * @by :wxy
 * @version 1.0
 */
import java.sql.*;
public class DataBase {
  public Connection conn;
  public Statement stmt;
  public ResultSet rs=null;
  public String sqlStr="";

  /*public DataBase() {
    this.connect();
  }*/
 public Connection connect(){
      try{
        Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
        String url ="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=bookshop";
        conn=DriverManager.getConnection(url,"sa","");
        stmt = conn.createStatement ();
       }catch(Exception ee){
        System.out.println("connect db error:"+ee.getMessage());
       }
      return conn;
    }
 /*public static void main(String[] args) {
     try{
            DataBase db = new DataBase();
            db.connect();
          }catch(Exception e){
            e.printStackTrace();
          }
        }*/
}