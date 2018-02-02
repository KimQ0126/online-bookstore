package bookshop.run;
/**
 * <p>Title: </p>
 */
import java.util.*;
import bookshop.util.*;
import bookshop.book.*;
import java.sql.*;
import java.lang.String;
import javax.servlet.http.HttpServletRequest;

public class op_bookclass extends DataBase{
  public op_bookclass() {
  }
  private bookclass bookclass = new bookclass(); //新的图书分类
  private javax.servlet.http.HttpServletRequest request; //建立页面请求
  private Vector classlist;		//图书类别向量数组
  public Vector getClasslist() {
        return classlist;
          }
  public String getBookClassSql() {
                  sqlStr = "select id,classname from BookClass order by id";
                  return sqlStr;
          }
          //查询图书分类
  public boolean seachBookClass() throws Exception {
                  int id = 0;
                  String classname = "";
                  int rscount = 0;
                  try {
                  DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement();
                  rs = stmt.executeQuery(getBookClassSql());
                  rscount = stmt.getMaxRows();
                  classlist = new Vector(rscount+1);
                  classlist.clear();
                  while (rs.next()){
                                  id = rs.getInt("id");
                                  classname = rs.getString("classname");
                                  bookclass bc = new bookclass(id,classname);
                                  classlist.addElement(bc);
                          }
                          rs.close();
                          return true;
                  }
                  catch (SQLException sqle){
                          System.out.println(sqle);
              return false;
                  }
          }
          //添加新图书分类时获得页面转递的分类名称
public boolean getRequest(javax.servlet.http.HttpServletRequest newrequest) {
     boolean flag = false;
     try{
         request = newrequest;
         String bookclassname = request.getParameter("bookclassname");
          if (bookclassname==null || bookclassname.equals("")){
              bookclassname = "";
              flag = false;
              }
              bookclass.setClassName(bookclassname);
              return true;
          }catch (Exception e){
            return false;
            }
    }
    //添加新的图书分类
        public boolean insert() throws Exception {
           sqlStr = "insert into bookclass (classname) values ('";
           sqlStr = sqlStr + dataFormat.toSql(bookclass.getClassName()) + "')";
                      try
                      {      System.out.print(sqlStr);
                        DataBase db = new DataBase();
                              Connection conn=db.connect();
                              stmt =conn.createStatement ();
                              stmt.execute(sqlStr);
                              return true;
                      }
                      catch (SQLException sqle)
                      {        System.out.print(sqle.getMessage());
                              return false;
                      }
              }
          //添加新的图书分类
          public boolean delete( int aid ) throws Exception {
               sqlStr = "delete from bookclass where id = "  + aid ;
               try
               {         DataBase db = new DataBase();
                          Connection conn=db.connect();
                          stmt =conn.createStatement ();
                       stmt.execute(sqlStr);
                       return true;
               }
               catch (SQLException e)
               {
                       System.out.println(e);
                       return false;
               }
       }
  };


