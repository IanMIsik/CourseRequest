package com.teachingplatform.dao.impl;

import com.teachingplatform.dao.interfaces.DAOException;
import com.teachingplatform.dao.interfaces.RequestDAO;
import com.teachingplatform.model.CourseRequest;

import javax.sql.DataSource;
import java.sql.*;

public class RequestDAOImpl implements RequestDAO {
    private DataSource datasource;

    public RequestDAOImpl(DataSource datasource){
        this.datasource = datasource;
    }
    @Override
    public void insertRequest(CourseRequest request) throws DAOException {
    // using preparedstatement to protect against sql injection
        String sql = "INSERT INTO Requests (course_id, applicant_id, status,created_at) VALUES (?, ?, ?, ?) ";
        try(Connection conn = datasource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            pstmt.setInt(1, request.getCourseId());
            pstmt.setInt(2, request.getApplicantId());
            pstmt.setString(3, request.getStatus());
            pstmt.setTimestamp(4, Timestamp.valueOf(request.getCreatedAt()));

            pstmt.executeUpdate();

            // Retrieve the generated ID
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    request.setId(generatedKeys.getInt(1));
                }
            }
            catch(SQLException e){
                throw new DAOException("Error Inserting course Request", e);
            }

        }catch (SQLException e){
            throw new DAOException("Error inserting course request", e);
        }
    }

    @Override
    public void updateRequestStatus(int requestId, String status) throws DAOException {

        String sql = "UPDATE Request SET status = ? WHERE id = ?";

        try(Connection conn = datasource.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, status);
            pstmt.setInt(2, requestId);

            pstmt.execute();

        }catch(SQLException e){
            throw new DAOException("Error updating request status", e);
        }
    }
}
