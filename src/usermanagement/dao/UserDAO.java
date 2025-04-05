package usermanagement.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import usermanagement.model.user;

public class 
. {
	private String jdbcURL = "jdbc:mysql://localhost:3306/Demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Pooja@1234";
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "(name,email,country) VALUES"
	          + "(?,?,?);";
	private static final String SELECT_USER_BY_ID  = "select id,name,email,country from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users  set name = ?,email= ?,country= ? where id =?";
	protected Connection getConnection(){
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insertuser(user user) throws SQLException{
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
					preparedStatement.setString(1, user.getName());
					preparedStatement.setString(2, user.getEmail());
					preparedStatement.setString(3, user.getCountry());
					preparedStatement.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}
	}
	public boolean updateuser(user user) throws SQLException{
		boolean rowUpdated;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);){
				statement.setString(1,user.getName());
				statement.setString(2,user.getEmail());
				statement.setString(3,user.getCountry());
				statement.setInt(4,user.getId());
				rowUpdated = statement.executeUpdate()>0;
				}
		return rowUpdated;
	}
	public user selectuser(int id){
		user user = null;
	try (Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement (SELECT_USER_BY_ID);) {
		preparedStatement.setInt(1, id);
		System.out.println(preparedStatement);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
		String name = rs.getString("name");
		String email = rs.getString("email"); 
		String country = rs.getString("country");
		user = new user(id, name, email, country);
		}
	}catch(SQLException e){
		e.printStackTrace();
	}
	return user;
	}
	public List<user> selectAllusers(){
	List<user> users = new ArrayList<>(); 
	try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
	System.out.println(preparedStatement);
	ResultSet rs = preparedStatement.executeQuery();
	while (rs.next()){
		int id = rs.getInt("id");
		String name = rs.getString("name"); 
		String email = rs.getString("email");
		String country = rs.getString("country");
		users.add(new user(id, name, email, country));
	}
	}catch (SQLException e){
		e.printStackTrace();
	}
	return users;
	}
	public boolean deleteuser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);){
			statement.setInt(1, id); 
			rowDeleted = statement.executeUpdate() > 0;
		}
    return rowDeleted;
	}
}

