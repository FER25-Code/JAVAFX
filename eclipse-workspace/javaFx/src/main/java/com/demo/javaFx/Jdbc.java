package com.demo.javaFx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.javaFx.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;


public class Jdbc {

	// Replace below database url, username and password with your actual database
	// credentials
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/demo_javafx";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "";
	private static final String INSERT_QUERY = "INSERT INTO item (site,bloc,piece,code,name,marque,type,serie) VALUES (?,?, ?, ?,?, ?, ?,?)";
	private static final String UPDATE_QUERY = ("UPDATE  item SET site=?,bloc=?,piece=?,code=?,name=?,marque=?,type=?,serie=? WHERE code =?");

	public void insertItem(String site, String bloc, String piece, String code, String name, String marque, String type,
			String serie) throws SQLException {
		// Step 1: Establishing a Connection and
		// try-with-resource statement will auto close the connection.
		try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
			preparedStatement.setString(1, site);
			preparedStatement.setString(2, bloc);
			preparedStatement.setString(3, piece);
			preparedStatement.setString(4, code);
			preparedStatement.setString(5, name);
			preparedStatement.setString(6, marque);
			preparedStatement.setString(7, type);
			preparedStatement.setString(8, serie);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// print SQL exception information
			printSQLException(e);
		}
	}

	public void UpdateItem(String site, String bloc, String piece, String code, String name, String marque, String type,
			String serie) throws SQLException {
		// Step 1: Establishing a Connection and
		// try-with-resource statement will auto close the connection.
		try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
			preparedStatement.setString(1, site);
			preparedStatement.setString(2, bloc);
			preparedStatement.setString(3, piece);
			preparedStatement.setString(4, code);
			preparedStatement.setString(5, name);
			preparedStatement.setString(6, marque);
			preparedStatement.setString(7, type);
			preparedStatement.setString(8, serie);
			preparedStatement.setString(9, code);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// print SQL exception information
			printSQLException(e);
		}
	}

	public void deleteItem(String code) throws SQLException {

		Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		String query = "delete from item where code = ?";
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setString(1, code);
		;
		preparedStmt.execute();

	}

	public ObservableList<Item> getAllItem() throws SQLException {
		try {
			Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
			ObservableList<Item> itemList = FXCollections.observableArrayList();

			String query = "SELECT * FROM item";
			Statement st;
			ResultSet re;

			st = connection.createStatement();
			re = st.executeQuery(query);

			while (re.next()) {
				Item item = new Item(re.getInt("id"), re.getString("site"), re.getString("bloc"), re.getString("piece"),
						re.getString("code"), re.getString("name"), re.getString("marque"), re.getString("type"),
						re.getString("serie"));

				itemList.add(item);

			}
			return itemList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);

		}
		return null;

	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public void ExportExcelFile() throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		PreparedStatement psmnt = null;
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("Select * from item");

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Excel Sheet");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("id");
		rowhead.createCell((short) 1).setCellValue("site");
		rowhead.createCell((short) 2).setCellValue("bloc");
		rowhead.createCell((short) 3).setCellValue("code");
		rowhead.createCell((short) 4).setCellValue("name");
		rowhead.createCell((short) 5).setCellValue("marque");
		rowhead.createCell((short) 6).setCellValue("type");
		rowhead.createCell((short) 7).setCellValue("serie");
		rowhead.createCell((short) 8).setCellValue("piece");

		int index = 1;
		while (rs.next()) {
			HSSFRow row = sheet.createRow((short) index);
			row.createCell((short) 0).setCellValue(rs.getInt(1));
			row.createCell((short) 1).setCellValue(rs.getString(2));
			row.createCell((short) 2).setCellValue(rs.getString(3));
			row.createCell((short) 3).setCellValue(rs.getString(4));
			row.createCell((short) 4).setCellValue(rs.getString(5));
			row.createCell((short) 5).setCellValue(rs.getString(6));
			row.createCell((short) 6).setCellValue(rs.getString(7));
			row.createCell((short) 7).setCellValue(rs.getString(8));
			row.createCell((short) 8).setCellValue(rs.getString(9));
			index++;
		}
		FileOutputStream fileOut = new FileOutputStream(
	 "C:\\Users\\FERGANI\\eclipse-workspace\\javaFx\\src\\main\\resources\\com\\demo\\javaFx\\excelFile.xlsx");
		wb.write(fileOut);
		fileOut.close();
		System.out.println("Data is saved in excel file.");
		rs.close();
		connection.close();

	}

	public void ImportExelFile() throws SQLException {
		int batchSize = 20;

		try {

			long start = System.currentTimeMillis();
			System.out.println("start Import ...");
			FileInputStream inputStream = new FileInputStream(
					"C:\\Users\\FERGANI\\eclipse-workspace\\javaFx\\src\\main\\resources\\com\\demo\\javaFx\\excelFileImport.xls");

			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
			connection.setAutoCommit(false);

			String sql = "INSERT INTO item (site,bloc,piece,code,name,marque,type,serie) VALUES (?,?,?, ?, ?,?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			int count = 0;

			rowIterator.next(); // skip the header row

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						String site = nextCell.getStringCellValue();
						statement.setString(1, site);
						break;
					case 1:
						String bloc = nextCell.getStringCellValue();
						statement.setString(2, bloc);
						break;
					case 2:
						String piece = nextCell.getStringCellValue();
						statement.setString(3, piece);
						break;
					case 3:
						String code = nextCell.getStringCellValue();
						statement.setString(4, code);
						break;
					case 4:
						String name = nextCell.getStringCellValue();
						statement.setString(5, name);
						break;
					case 5:
						String marque = nextCell.getStringCellValue();
						statement.setString(6, marque);
						break;
					case 6:
						String type = nextCell.getStringCellValue();
						statement.setString(7, type);
						break;
					case 7:
						String serie = nextCell.getStringCellValue();
						statement.setString(8, serie);
						break;
					}

				}

				statement.addBatch();

				if (count % batchSize == 0) {
					statement.executeBatch();
				}

			}

			workbook.close();

			// execute the remaining queries
			statement.executeBatch();

			connection.commit();
			connection.close();

			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));

		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}

	}

	public void ImportCSV() {
		long start = System.currentTimeMillis();
		String filePath = "C:\\Users\\FERGANI\\eclipse-workspace\\javaFx\\src\\main\\resourc"
				+ "es\\com\\demo\\javaFx\\excelexcelFileIcsv.csv";
		int batchSize = 20;
		try {
			System.out.println("start 2");
			Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
			connection.setAutoCommit(false);

			String sql = "INSERT INTO item (site,bloc,piece,code,name,marque,type,serie) VALUES (?,?,?,?, ?, ?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			BufferedReader lineReader = new BufferedReader(new FileReader(filePath));

			String lineText = null;
			int count = 0;

			lineReader.readLine();
			while ((lineText = lineReader.readLine()) != null) {
				String[] data = lineText.split(";");
				String site = data[0];
				String bloc = data[1];
				String code = data[2];
				String name = data[3];
				String marque = data[4];
				String type = data[5];
				String serie = data[6];
				String piece = data[7];

				statement.setString(1, site);
				statement.setString(2, bloc);
				statement.setString(3, code);
				statement.setString(4, name);
				statement.setString(5, marque);
				statement.setString(6, type);
				statement.setString(7, serie);
				statement.setString(8, piece);

				statement.addBatch();
				if (count % batchSize == 0) {
					statement.executeBatch();
				}
			}
			lineReader.close();
			statement.executeBatch();
			connection.commit();
			connection.close();
			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
			System.out.println("Data has been inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void ImportXlsx() throws IOException {
		String filePath = "C:\\Users\\FERGANI\\eclipse-workspace\\javaFx\\src\\main\\resources\\com\\demo\\javaFx\\cc.xlsx";
			FileInputStream fileInputStream = new FileInputStream(filePath);
			try (XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
				XSSFSheet sheet = workbook.getSheetAt(0);
				int rows =sheet.getLastRowNum();
				int cols =sheet.getRow(1).getLastCellNum();
				for(int r=0;r<=rows;r++) {
					XSSFRow row =sheet.getRow(r);
					
					for(int c=0 ;c<+cols;c++) {
						XSSFCell cell =row.getCell(c);
						switch (cell.getCellType()) {
						case STRING: System.out.println(cell.getStringCellValue());
						
							break ;
						case NUMERIC: System.out.println(cell.getStringCellValue());
						
						break;
						default:
							break;
						}
						
					}
				}
			}
	

	}

}
