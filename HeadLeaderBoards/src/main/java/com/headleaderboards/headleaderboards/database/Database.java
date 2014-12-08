package com.headleaderboards.headleaderboards.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.headleaderboards.headleaderboards.HeadLeaderBoards;

public class Database {

	   private final String connectionUri;
	   private final String username;
	   private final String password;
	   private Connection conn;

	   public Database() {
	        final String hostname = HeadLeaderBoards.get().getConfig().getString("database.hostname");
	        final int port = HeadLeaderBoards.get().getConfig().getInt("database.port");
	        final String database = HeadLeaderBoards.get().getConfig().getString("database.database");

	        connectionUri = String.format("jdbc:mysql://%s:%d/%s", hostname, port, database);
	        username = HeadLeaderBoards.get().getConfig().getString("database.username");
	        password = HeadLeaderBoards.get().getConfig().getString("database.password");

	        try {
	            connect();
	        } catch (SQLException sqlException) {
	            close();
	        }
	    }

	    private void connect() throws SQLException {
	        if (conn != null) {
	            try {
	                conn.createStatement().execute("SELECT 1;");
	            } catch (SQLException sqlException) {
	                if (sqlException.getSQLState().equals("08S01")) {
	                    try {
	                        conn.close();

	                    } catch (SQLException ignored) {
	                    }
	                }
	            }
	        }

	        if (conn == null || conn.isClosed()) {
	            conn = DriverManager.getConnection(connectionUri, username, password);
	        }
	    }

	    public Connection getConnection() {
	        return conn;
	    }

	    public void close() {
	        try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();
	            }

	        } catch (SQLException ignored) {

	        }

	        conn = null;
	    }

	    public boolean checkConnection() {
	        try {
	            connect();

	        } catch (SQLException sqlException) {
	            close();
	            sqlException.printStackTrace();

	            return false;
	        }

	        return true;
	    }

}
