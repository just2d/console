package com.nuoshi.console.persistence.write.estate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.common.util.JdbcUtil;
import com.nuoshi.console.domain.topic.Estate;

@Repository
public class EstateDao {
	Log log = LogFactory.getLog(EstateDao.class);

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public void batchUpdate(final List<Estate> list) {
		jdbcTemplate.update("update estate set wyType = ?,basic_info = ? where id= ?", new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.getConnection().setAutoCommit(false);
				for (Estate estate : list) {
					System.out.println("wytype:" + estate.getWyType()+"estateId:"+estate.getEstateId()+"---basic_info"+estate.getBasicInfo());
					ps.setString(1, estate.getWyType());
					ps.setString(2, estate.getBasicInfo());
					ps.setInt(3, estate.getId());
					ps.addBatch();
				}
				ps.executeBatch();
				ps.getConnection().commit();
			}
		});
		/**
		 * jdbcTemplate.batchUpdate(
		 * "update estate set wyType = ?,basic_info = ? where id= ?", new
		 * BatchPreparedStatementSetter() {
		 * 
		 * @Override public void setValues(PreparedStatement ps, int i) throws
		 *           SQLException { if (list != null && list.size() > 0) {
		 * 
		 *           } }
		 * @Override public int getBatchSize() { if (list != null) { return
		 *           list.size(); } return 0; } });
		 **/
		/**
		Connection conn = JdbcUtil.getCommConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("update estate set wyType = ?,basic_info = ? where id= ?");
			for (Estate estate : list) {
				System.out.println("wytype:" + estate.getWyType());
				ps.setString(1, estate.getWyType());
				ps.setString(2, estate.getBasicInfo());
				ps.setInt(3, estate.getId());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}**/

	}

}
