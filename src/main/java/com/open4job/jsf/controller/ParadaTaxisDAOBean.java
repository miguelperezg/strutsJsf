package com.open4job.jsf.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.open4job.jsf.model.vo.ParadaTaxiVO;

//import com.open4job.struts.model.vo.ParadaTaxiVO;
@ManagedBean
@SessionScoped
public class ParadaTaxisDAOBean implements Serializable {

	// @Resource(name="jdbc/myoracle")
	private DataSource ds;

	public ParadaTaxisDAOBean() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/myoracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	// BaseDatosDirecta bbdd = new BaseDatosDirecta();

	// Listado de las paradas de taxi

	public ArrayList<ParadaTaxiVO> getListadoParadaTaxi() throws Exception {

		Connection conexion = ds.getConnection();

		ArrayList<ParadaTaxiVO> lista = new ArrayList<ParadaTaxiVO>();

		PreparedStatement st = conexion
				.prepareStatement("SELECT * FROM PARADA_TAXI");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			lista.add(new ParadaTaxiVO(rs.getInt(1), rs.getString(2), rs
					.getDate(3), rs.getFloat(4), rs.getFloat(5), rs
					.getString(6)));
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
			}
		}

		return lista;
	}

	// Devuelve la parada del taxi por el id

	public ParadaTaxiVO getparadataxi(int id) throws Exception {
		ParadaTaxiVO idLista = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;

		Connection conec = ds.getConnection();

		stmt = conec.prepareStatement("SELECT * FROM PARADA_TAXI WHERE id = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();

		if (rs.next()) {
			idLista = new ParadaTaxiVO(rs.getInt(1), rs.getString(2),
					rs.getDate(3), rs.getFloat(4), rs.getFloat(5),
					rs.getString(6));
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}
		return idLista;

	}

	public void insertValues(Connection conexion) throws SQLException {
		
		java.util.Date utilDate = new java.util.Date();
		float corx = 14.3f;
		float cory = 14.3f;
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		PreparedStatement stmt = null;
		stmt = conexion
				.prepareStatement("INSERT INTO PARADA_TAXI(ID, TITLE, LASTUPDATE, COR_X, COR_Y, ICON) "
						+ "	VALUES (?, ?, ?, ?, ?, ?)");
		stmt.setInt(1, 6);
		stmt.setString(2, "ROMAREDA");
		stmt.setDate(3, sqlDate);
		stmt.setFloat(4, corx);
		stmt.setFloat(5, cory);
		stmt.setString(6, "circulo");
		stmt.executeUpdate();
	}

}
