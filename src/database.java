import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class database {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public database() {
    	  try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost:3307/gscrapper?"
			                  + "user=root&password=&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void insert(String frm, String bdy) throws Exception {
        preparedStatement = connect
                .prepareStatement("insert into gscrapper.results values (default, ?, ?, ?)");
        // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
        // Parameters start with 1
        
        preparedStatement.setString(1,frm );
        preparedStatement.setString(2, bdy);
        preparedStatement.setInt(3, 1);
        // preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
     
        preparedStatement.executeUpdate();

    }
    
    public ResultSet readDataBase() throws Exception {
      
    	try {
        	
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
          
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from gscrapper.comments where isdone=0 and isprocessing=0 order by id DESC LIMIT 5");
            return resultSet;
            
            //writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
           /*
        
            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();
		
            resultSet = statement
            .executeQuery("select * from gscrapper.comments");
            writeMetaData(resultSet);
            	*/ 

        } catch (Exception e) {
            throw e;
        } finally {
          //  close();
        }

    }
    
    public void update(int id) throws SQLException {
    	 preparedStatement = connect
    	            .prepareStatement("update gscrapper.comments set isprocessing= ?  where id=? ; ");
    	            preparedStatement.setInt(1, 1);
    	            preparedStatement.setInt(2, id);
    	            
    	            preparedStatement.executeUpdate();
    }

    
    
    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
    

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("frm");
            String website = resultSet.getString("kword");
         //   String summary = resultSet.getString("summary");
          //  Date date = resultSet.getDate("datum");
          //  String comment = resultSet.getString("comments");
           
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
     //       System.out.println("summary: " + summary);
         //   System.out.println("Date: " + date);
         //   System.out.println("Comment: " + comment);
        }
    }
    

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}